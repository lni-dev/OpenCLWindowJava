package de.linusdev;

import de.linusdev.nat.buffer.BufferUtils;
import de.linusdev.nat.openclwindow.OpenCLWindowJava;
import de.linusdev.nat.structs.CameraStruct;
import de.linusdev.nat.structs.StructureInfo;
import de.linusdev.nat.types.Float1;
import de.linusdev.nat.types.Float4;

import java.io.IOException;
import java.nio.ByteBuffer;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        CameraStruct cam = new CameraStruct(true);

        cam.position.x(1.f);
        cam.distanceToScreen.set(5.f);

        OpenCLWindowJava window = new OpenCLWindowJava();
        window.setTitle("Some Title");
        window.setProgramCodeOfResource("render.cl");
        window.setSize(800, 450);
        //window.setBorderlessFullscreen();

        window.setKeyListener((key, scancode, action, mods) -> {
            //scancodes: w: 17, a:30, s:31, d: 32
        });

        window.setKernelArg(2, cam);

        window.show();
        window.close();
    }


}