package de.linusdev.nat.openclwindow;

import de.linusdev.lutils.bitfield.IntBitfield;
import de.linusdev.lutils.bitfield.IntVolatileBitfield;
import de.linusdev.nat.GLFWValues;
import de.linusdev.nat.Modifiers;
import de.linusdev.nat.loader.LibraryLoader;
import de.linusdev.nat.openclwindow.listener.KeyListener;
import de.linusdev.nat.openclwindow.listener.MouseListener;
import de.linusdev.nat.structs.Structure;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;

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
    private final IntVolatileBitfield<Modifiers> lastMods = new IntVolatileBitfield<>();
    private KeyListener keyListener = null;
    private MouseListener mouseListener = null;

    public OpenCLWindowJava() {
        objectPointer = _create();
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

    public void setKeyListener(@Nullable KeyListener keyListener) {
        this.keyListener = keyListener;
    }

    public void setMouseListener(@Nullable MouseListener mouseListener) {
        this.mouseListener = mouseListener;
    }

    public void setKernelArg(int index, @NotNull Structure struct) {
        int err = _setKernelArg(objectPointer, index, struct.getByteBuf(), struct.size());
        if(err != 0)
            throw new RuntimeException("Error while setting kernel arg: " + err);
    }

    @Override
    public void close() {
        _delete(objectPointer);
    }

    private native long _create();

    private native void _show(long pointer);

    private native void _delete(long pointer);

    private native void _setTitle(long pointer, String string);

    private native void _setSize(long pointer, int width, int height);

    private native void _setBorderlessFullscreen(long pointer);

    private native void _setProgramCode(long pointer, String string);

    private native int _setKernelArg(long pointer, int index, ByteBuffer buffer, int bufSize);

    /*
     * Method that should be called natively only:
     */

    private void onKey(int key, int scancode, int action, int mods) {
        lastMods.replaceWith(mods);
        if(keyListener != null)
            keyListener.onKey(key, scancode, action, lastMods);
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
}
