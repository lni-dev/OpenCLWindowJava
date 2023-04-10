/*
 * Copyright (c) 2023 Linus Andera
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.linusdev.openclwindow.nat;

import de.linusdev.lutils.bitfield.LongVolatileBitfield;
import de.linusdev.lutils.llist.LLinkedList;
import de.linusdev.openclwindow.FrameInfo;
import de.linusdev.openclwindow.OpenCLException;
import de.linusdev.openclwindow.buffer.AutoUpdateGPUBuffer;
import de.linusdev.openclwindow.buffer.HasGPUBuffer;
import de.linusdev.openclwindow.enums.Modifiers;
import de.linusdev.openclwindow.enums.OpenCLErrorCodes;
import de.linusdev.openclwindow.input.InputManager;
import de.linusdev.openclwindow.listener.CharListener;
import de.linusdev.openclwindow.listener.KeyListener;
import de.linusdev.openclwindow.listener.MouseListener;
import de.linusdev.openclwindow.nat.loader.LibraryLoader;
import de.linusdev.openclwindow.structs.Structure;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.util.List;

public class OpenCLWindowJava implements AutoCloseable {

    static {
        //System.load("C:\\Users\\Linus\\Desktop\\Programming\\Projects\\openclwindow\\cmake-build-release\\libOpenCLWindowJava.jnilib");
        LibraryLoader loader = new LibraryLoader("native-libraries/libOpenCLWindowJava.jnilib",
                "native-libraries/libglfw3.a",
                "native-libraries/libglad.a",
                "native-libraries/libOpenCLWindow.a");

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private final long objectPointer;

    //listener
    private final LongVolatileBitfield<Modifiers> lastMods = new LongVolatileBitfield<>();
    private List<KeyListener> keyListeners = new LLinkedList<>();
    private MouseListener mouseListener = null;
    private List<CharListener> charListeners = new LLinkedList<>();

    //other
    private final @NotNull LLinkedList<AutoUpdateGPUBuffer> buffers;
    private final @NotNull FrameInfo frameInfo;
    private long frameStartTime = 0L;

    private final @NotNull InputManager inputManager;

    public OpenCLWindowJava() {
        objectPointer = _create();
        this.buffers = new LLinkedList<>();
        this.frameInfo = new FrameInfo(100, null);
        this.inputManager = new InputManager(this);
    }

    public void show() {
        _show(objectPointer);
    }

    public void setTitle(@NotNull String title) {
        _setTitle(objectPointer, title);
    }

    public void setSize(int width, int height) {
        _setSize(objectPointer, width, height);
    }

    public void setBorderlessFullscreen() {
        _setBorderlessFullscreen(objectPointer);
    }

    public void setProgramCode(@NotNull String code) {
        _setProgramCode(objectPointer, code);
    }

    public void setProgramCodeOfResource(@NotNull String resourcePath) throws IOException {
        StackWalker stackWalker = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);
        Class<?> callerClass = stackWalker.getCallerClass();

        try (
                InputStream in = callerClass.getClassLoader().getResourceAsStream(resourcePath);

        ) {
            if(in == null)
                throw new IllegalArgumentException("Resource '" + resourcePath + "' not found.");

            InputStreamReader rIn = new InputStreamReader(in);
            BufferedReader reader = new BufferedReader(rIn);

            StringBuilder sb = new StringBuilder();
            char[] buf = new char[1024*8];

            int readCount;
            while ((readCount = reader.read(buf)) != -1) {
                sb.append(buf, 0, readCount);
            }

            setProgramCode(sb.toString());
        }
    }

    public void setFrameListener(@Nullable FrameInfo.UpdateListener listener) {
        frameInfo.setListener(listener);
    }

    public void addKeyListener(@NotNull KeyListener keyListener) {
        this.keyListeners.add(keyListener);
    }

    public void removeKeyListener(@NotNull KeyListener listener) {
        keyListeners.remove(listener);
    }

    public void setMouseListener(@Nullable MouseListener mouseListener) {
        this.mouseListener = mouseListener;
    }

    public void addCharListener(@NotNull CharListener listener) {
        this.charListeners.add(listener);
    }

    public void removeCharListener(@NotNull CharListener listener) {
        this.charListeners.remove(listener);
    }

    public void setKernelArg(int index, @NotNull Structure struct) {
        int err = _setKernelArg(objectPointer, index, struct.getByteBuf(), struct.getSize());
        if(err != 0)
            throw new OpenCLException(OpenCLErrorCodes.checkError(err));
    }

    public void setKernelArg(int index, @NotNull HasGPUBuffer buffer) {
        int err = _setKernelArg(objectPointer, index, buffer.getGPUBuffer().getPointer());
        if(err != 0)
            throw new OpenCLException(OpenCLErrorCodes.checkError(err));
    }

    public boolean checkIfWindowShouldClose() {
        long currentTime = System.currentTimeMillis();
        if(frameStartTime != 0)
            frameInfo.submitFrame(currentTime - frameStartTime);
        frameStartTime = currentTime;

        return _checkIfWindowShouldClose(objectPointer);
    }

    public void render() {

        long startTime = System.currentTimeMillis();

        int err = _render(objectPointer);
        if(err != 0)
            throw new OpenCLException(OpenCLErrorCodes.checkError(err));

        frameInfo.submitRenderTime(System.currentTimeMillis() - startTime);

    }

    public void swapBuffer() {
        long startTime = System.currentTimeMillis();
        _swapBuffer(objectPointer);
        frameInfo.submitSwapBufferTime(System.currentTimeMillis() - startTime);
    }

    @Override
    public void close() {
        _destroy(objectPointer);
        _delete(objectPointer);
    }

    //auto update buffer
    public void addAutoUpdateBuffer(@NotNull AutoUpdateGPUBuffer buffer) {
        buffers.add(buffer);
    }

    public void removeAutoUpdateBuffer(@NotNull AutoUpdateGPUBuffer buffer) {
        buffers.remove(buffer);
    }

    public void checkAutoUpdateBuffer() {
        long startTime = System.currentTimeMillis();
        for(AutoUpdateGPUBuffer buffer : buffers)
            buffer.check();
        frameInfo.submitAutoBufferTime(System.currentTimeMillis() - startTime);
    }

    //getter

    public @NotNull InputManager getInputManager() {
        return inputManager;
    }

    //package-private
    long getPointer() {
        return objectPointer;
    }

    //native
    private native long _create();
    private native void _show(long pointer);
    private static native boolean _checkIfWindowShouldClose(long pointer);
    private static native int _render(long pointer);
    private static native void _swapBuffer(long pointer);
    private static native void _destroy(long pointer);
    private native void _delete(long pointer);
    private native void _setTitle(long pointer, String string);
    private native void _setSize(long pointer, int width, int height);
    private native void _setBorderlessFullscreen(long pointer);
    private native void _setProgramCode(long pointer, String string);
    private native int _setKernelArg(long pointer, int index, ByteBuffer buffer, int bufSize);
    private static native int _setKernelArg(long pointer, int index, long clBufferPointer);

    /*
     * Methods, that should be called natively only:
     */

    private void onKey(int key, int scancode, int action, int mods) {
        lastMods.replaceWith(mods);
        for(KeyListener listener : keyListeners)
            listener.onKey(key, scancode, action, lastMods);
    }

    private void onMouseCursor(double xpos, double ypos) {
        if(mouseListener != null)
            mouseListener.onMouseCursor(xpos, ypos);
    }

    private void onMouseButton(int button, int action, int mods) {
        lastMods.replaceWith(mods);
        if(mouseListener != null)
            mouseListener.onMouseButton(button, action, lastMods);
    }

    private void onChar(int codepoint) {
        System.out.println(codepoint);

        char[] chars = Character.toChars(codepoint);
        for(CharListener listener : charListeners)
            listener.onChar(chars);
    }
}
