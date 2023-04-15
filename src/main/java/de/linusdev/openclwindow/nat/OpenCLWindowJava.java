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
import de.linusdev.openclwindow.enums.GLFWValues;
import de.linusdev.openclwindow.enums.Modifiers;
import de.linusdev.openclwindow.enums.OpenCLErrorCodes;
import de.linusdev.openclwindow.input.InputManager;
import de.linusdev.openclwindow.listener.CharListener;
import de.linusdev.openclwindow.listener.KeyListener;
import de.linusdev.openclwindow.listener.MouseListener;
import de.linusdev.openclwindow.nat.loader.LibraryLoader;
import de.linusdev.openclwindow.structs.Structure;
import org.intellij.lang.annotations.MagicConstant;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class OpenCLWindowJava implements AutoCloseable {

    static {
        //System.load("C:\\Users\\Linus\\Desktop\\Programming\\Projects\\openclwindow\\cmake-build-release\\libOpenCLWindowJava.jnilib");
        /*LibraryLoader loader = new LibraryLoader("native-libraries/libOpenCLWindowJava.jnilib", OpenCLWindowJava.class,
                "native-libraries/libglfw3.a",
                "native-libraries/libglad.a",
                "native-libraries/libOpenCLWindow.a");*/

        LibraryLoader loader = new LibraryLoader(
                OpenCLWindowJava.class, "native-libraries/", "OpenCLWindowJava.jnilib",
                "glfw3", "glad", "OpenCLWindow"
                );

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private final long objectPointer;
    private boolean programCodeSet = false;

    //listener
    private final LongVolatileBitfield<Modifiers> lastMods = new LongVolatileBitfield<>();
    private List<KeyListener> keyListeners = new LLinkedList<>();
    private List<MouseListener> mouseListeners = new LLinkedList<>();
    private List<CharListener> charListeners = new LLinkedList<>();

    //other
    private final @NotNull LLinkedList<AutoUpdateGPUBuffer> buffers;
    private final @NotNull FrameInfo frameInfo;
    private long frameStartTime = 0L;

    private final @NotNull InputManager inputManager;

    //runnable-queue
    private final @NotNull ConcurrentLinkedQueue<Runnable> runnableQueue;

    public OpenCLWindowJava() {
        objectPointer = _create();
        this.buffers = new LLinkedList<>();
        this.frameInfo = new FrameInfo(100, null);
        this.inputManager = new InputManager(this);
        this.runnableQueue = new ConcurrentLinkedQueue<>();
    }

    private void createSharedRenderBuffer() {
        OpenCLException.check(_createSharedRenderBuffer(objectPointer));
        if(programCodeSet)
            OpenCLException.check(_setBaseKernelArgs(objectPointer));
    }

    public void show() {
        _show(objectPointer);
    }

    public void setTitle(@NotNull String title) {
        _setTitle(objectPointer, title);
    }

    public void setSize(int width, int height) {
        _setSize(objectPointer, width, height);
        createSharedRenderBuffer();
    }

    public void setBorderlessFullscreen() {
        _setBorderlessFullscreen(objectPointer);
    }

    public void setProgramCode(@NotNull String code, @Nullable String options) {
        programCodeSet = true;
        _setProgramCode(objectPointer, code, options);
        _setBaseKernelArgs(objectPointer);
    }

    public void setProgramCodeOfResource(@NotNull String resourcePath, @Nullable String options) throws IOException {
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

            setProgramCode(sb.toString(), options);
        }
    }

    public void setProgramCodeOfFile(@NotNull Path path, @Nullable String options) throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            StringBuilder sb = new StringBuilder();
            char[] buf = new char[1024*8];

            int readCount;
            while ((readCount = reader.read(buf)) != -1) {
                sb.append(buf, 0, readCount);
            }

            setProgramCode(sb.toString(), options);
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

    public void addMouseListener(@NotNull MouseListener mouseListener) {
        this.mouseListeners.add(mouseListener);
    }

    public void removeMouseListener(@NotNull MouseListener mouseListener) {
        this.mouseListeners.remove(mouseListener);
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

    public boolean check() {
        long currentTime = System.currentTimeMillis();
        if(frameStartTime != 0)
            frameInfo.submitFrame(currentTime - frameStartTime);
        frameStartTime = currentTime;

        @Nullable Runnable runnable;
        while((runnable = runnableQueue.poll()) != null) {
            runnable.run();
        }

        return _checkIfWindowShouldClose(objectPointer);
    }

    public void enqueueRunnable(@NotNull Runnable runnable) {
        runnableQueue.add(runnable);
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

    public void setInputMode(
            @MagicConstant(valuesFromClass = GLFWValues.InputMode.Mode.class) int mode,
            @MagicConstant(valuesFromClass = GLFWValues.InputMode.Value.class) int value
    ) {
        _glfwSetInputMode(objectPointer, mode, value);
    }

    public void setCursorPos(double xpos, double ypos) {
        _glfwSetCursorPos(objectPointer, xpos, ypos);
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

    public @NotNull FrameInfo getFrameInfo() {
        return frameInfo;
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
    private native void _setProgramCode(long pointer, String code, String options);
    private static native int _createSharedRenderBuffer(long pointer);
    private static native int _setBaseKernelArgs(long pointer);
    private native int _setKernelArg(long pointer, int index, ByteBuffer buffer, int bufSize);
    private static native int _setKernelArg(long pointer, int index, long clBufferPointer);
    private static native void _glfwSetInputMode(long pointer, @MagicConstant(valuesFromClass = GLFWValues.InputMode.Mode.class) int mode, @MagicConstant(valuesFromClass = GLFWValues.InputMode.Value.class) int value);
    private static native void _glfwSetCursorPos(long pointer, double xpos, double ypos);

    //public native
    public static native boolean isRawMouseMotionSupported();
    public static native String getKeyName(int key, int scancode);

    /*
     * Methods, that should be called natively only:
     */

    private void onKey(int key, int scancode, int action, int mods) {
        lastMods.replaceWith(mods);
        for(KeyListener listener : keyListeners)
            listener.onKey(key, scancode, action, lastMods);
    }

    private void onMouseCursor(double xpos, double ypos) {
        for(MouseListener listener : mouseListeners)
            listener.onMouseCursor(xpos, ypos);
    }

    private void onMouseButton(int button, int action, int mods) {
        lastMods.replaceWith(mods);
        for(MouseListener listener : mouseListeners)
            listener.onMouseButton(button, action, lastMods);
    }

    private void onChar(int codepoint) {
                char[] chars = Character.toChars(codepoint);
        for(CharListener listener : charListeners)
            listener.onChar(chars);
    }
}
