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

package de.linusdev.openclwindow.input;

import de.linusdev.openclwindow.enums.GLFWValues;
import de.linusdev.openclwindow.nat.OpenCLWindowJava;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InputManagerTest {

    @Test
    void isPressed() {
        OpenCLWindowJava w =new OpenCLWindowJava();
        InputManager inputManager = new InputManager(w);

        int code = 10;

        assertFalse(inputManager.isPressed(code));
        simPress(inputManager, code);
        assertTrue(inputManager.isPressed(code));
        simRelease(inputManager, code);
        assertFalse(inputManager.isPressed(code));

        code = 298;

        assertFalse(inputManager.isPressed(code));
        simPress(inputManager, code);
        assertTrue(inputManager.isPressed(code));
        simRelease(inputManager, code);
        assertFalse(inputManager.isPressed(code));

        code = 20 * Integer.SIZE - 1;

        assertFalse(inputManager.isPressed(code));
        simPress(inputManager, code);
        assertTrue(inputManager.isPressed(code));
        simRelease(inputManager, code);
        assertFalse(inputManager.isPressed(code));


        assertFalse(inputManager.isPressed(11));
        simPress(inputManager, 11);
        simPress(inputManager, 10);
        assertTrue(inputManager.isPressed(11));
        assertTrue(inputManager.isPressed(10));
        simRelease(inputManager, 11);
        assertFalse(inputManager.isPressed(11));
        assertTrue(inputManager.isPressed(10));

        w.close();
    }

    public static void simPress(InputManager m, int scancode) {
        m.onKey(0, scancode, GLFWValues.Actions.GLFW_PRESS, null);
    }

    public static void simRelease(InputManager m, int scancode) {
        m.onKey(0, scancode, GLFWValues.Actions.GLFW_RELEASE, null);
    }
}