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

import de.linusdev.lutils.bitfield.IntBitfield;
import de.linusdev.openclwindow.enums.CLMemoryFlags;
import de.linusdev.openclwindow.structs.CameraStruct;
import de.linusdev.openclwindow.types.BBFloat3;
import de.linusdev.openclwindow.types.VMath;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class OpenCLWindowJavaTest {

    @Test
    public void test() throws IOException {
        CameraStruct cam = new CameraStruct(true);

        cam.position.xyz(4f, 4f, 0f);
        cam.lookAtVector.xyz(0f, .5f, 0f);
        cam.distanceToScreen.set(1.f);

        OpenCLWindowJava window = new OpenCLWindowJava();
        window.setTitle("Some Title");
        window.setProgramCodeOfResource("render.cl");
        window.setSize(800, 450);
        //window.setBorderlessFullscreen();

        window.setKeyListener((key, scancode, action, mods) -> {
            //scancodes: w: 17, a:30, s:31, d: 32
        });

        IntBitfield<CLMemoryFlags> flags = new IntBitfield<>();
        flags.set(CLMemoryFlags.CL_MEM_READ_ONLY, CLMemoryFlags.CL_MEM_COPY_HOST_PTR,
                CLMemoryFlags.CL_MEM_HOST_WRITE_ONLY);
        GPUBuffer buf = new GPUBuffer(window, flags, cam.getByteBuf());

        window.setKernelArg(2, buf);

        window.show();

        BBFloat3 a = new BBFloat3(true);
        a.x(0.5f);

        double fpsChecksSum = 0d;
        int fpsCheckCount = 0;
        long lastFPSCheck = System.currentTimeMillis();
        int frames = 0;
        double fps;
        while (!window.checkIfWindowShouldClose()) {
            window.render();
            window.swapBuffer();

            if(cam.isModified()) {
                cam.unmodified();
                System.out.println("enqueueWriteBuffer");
                buf.enqueueWriteBuffer(false, 0,
                        cam.position.getByteBuf().capacity(), cam.position.getByteBuf(), false);
            }


            if(frames++ == 100) {
                VMath.add(cam.position, a, cam.position);
                fps = 1000.d / ((System.currentTimeMillis() - lastFPSCheck) / ((double) frames));
                lastFPSCheck = System.currentTimeMillis();
                frames = 0;

                fpsChecksSum += fps;
                fpsCheckCount++;
                System.out.println(fps);
            }

            if(fpsCheckCount == 20) break;
        }

        buf.close();
        window.close();

        System.out.println("Average FPS: " + (fpsChecksSum / fpsCheckCount));
    }
}