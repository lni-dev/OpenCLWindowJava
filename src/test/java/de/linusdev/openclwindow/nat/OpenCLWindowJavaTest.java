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

import de.linusdev.openclwindow.buffer.AutoUpdateGPUBuffer;
import de.linusdev.openclwindow.buffer.BufferAccess;
import de.linusdev.openclwindow.enums.CLMemoryFlags;
import de.linusdev.openclwindow.enums.GLFWValues;
import de.linusdev.openclwindow.input.InputManager;
import de.linusdev.openclwindow.input.TextInput;
import de.linusdev.openclwindow.structs.CameraStruct;
import de.linusdev.openclwindow.types.BBFloat3;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class OpenCLWindowJavaTest {

    @Test
    public void test() throws IOException {
        CameraStruct cam = new CameraStruct(true);

        cam.position.xyz(4f, 4f, 0f);
        cam.lookAtVector.xyz(0f, .5f, 0f);
        cam.distanceToScreen.set(1.f);

        OpenCLWindowJava window = new OpenCLWindowJava();
        window.setFrameListener(info -> {
            /*System.out.printf("FPS: %f. Millis: Frame: %f, Render: %f, Swap: %f, Memory: %f\n",
                    info.getFPS(), info.getAverageMillisBetweenFrames(),
                    info.getAverageMillisRenderTime(), info.getAverageMillisSwapBufferTime(),
                    info.getAverageMillisAutoBufferTime());*/
        });
        window.setTitle("Some Title");
        window.setProgramCodeOfResource("render.cl", "-cl-std=CL2.0");
        window.setSize(800, 450);
        //window.setBorderlessFullscreen();

        window.addKeyListener((key, scancode, action, mods) -> {
            //scancodes: w: 17, a:30, s:31, d: 32

            if(action == GLFWValues.Actions.GLFW_PRESS || action == GLFWValues.Actions.GLFW_REPEAT) {
                if(scancode == 17) {
                    cam.position.x(cam.position.x() + 0.05f);
                    cam.position.modified();
                } else if(scancode == 30) {
                    cam.position.z(cam.position.z() + 0.05f);
                    cam.position.modified();
                }  else if(scancode == 31) {
                    cam.position.x(cam.position.x() - 0.05f);
                    cam.position.modified();
                }  else if(scancode == 32) {
                    cam.position.z(cam.position.z() - 0.05f);
                    cam.position.modified();
                }
            }


        });

        AutoUpdateGPUBuffer buf = new AutoUpdateGPUBuffer(window, cam,
                BufferAccess.HOST_WRITE_KERNEL_READ, CLMemoryFlags.CL_MEM_COPY_HOST_PTR);

        window.setKernelArg(2, buf);
        window.show();

        BBFloat3 a = new BBFloat3(true);
        a.x(0.01f);

        TextInput input = new TextInput(window, new TextInput.Listener() {
            @Override
            public void onAdd(@NotNull StringBuffer current, char @NotNull [] added) {
                //System.out.println("current: "+current+", added: " + Arrays.toString(added));
            }

            @Override
            public void onRemove(@NotNull StringBuffer current, char removed) {
                //System.out.println("current: "+current);
            }

            @Override
            public void onEnter(@NotNull StringBuffer current) {
                System.out.println(current);
                current.setLength(0);
            }
        });

        input.start();

        InputManager im = window.getInputManager();
        while (!window.checkIfWindowShouldClose()) {
            window.render();
            window.swapBuffer();
            window.checkAutoUpdateBuffer();

            //scancodes: w: 17, a:30, s:31, d: 32
            if(im.isPressed(17)) {
                cam.position.x(cam.position.x() + 0.05f);
                cam.position.modified();
            }

            if (im.isPressed(30)) {
                cam.position.z(cam.position.z() + 0.05f);
                cam.position.modified();
            }

            if (im.isPressed(31)) {
                cam.position.x(cam.position.x() - 0.05f);
                cam.position.modified();
            }

            if (im.isPressed(32)) {
                cam.position.z(cam.position.z() - 0.05f);
                cam.position.modified();
            }

            //VMath.add(cam.position, a, cam.position);
            //cam.position.modified();
        }

        buf.close();
        window.close();

        System.out.println(input.getString());
    }
}