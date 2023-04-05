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