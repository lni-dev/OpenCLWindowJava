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

import de.linusdev.lutils.bitfield.LongVolatileBitfield;
import de.linusdev.openclwindow.enums.GLFWValues;
import de.linusdev.openclwindow.enums.Modifiers;
import de.linusdev.openclwindow.listener.KeyListener;
import de.linusdev.openclwindow.nat.OpenCLWindowJava;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public class InputManager implements KeyListener {

    private static final int SCANCODE_ARRAY_SIZE = 20;
    private static final int MAX_SCANCODE = SCANCODE_ARRAY_SIZE * Integer.SIZE - 1;

    private final int[] scancodes;

    public InputManager(@NotNull OpenCLWindowJava window) {
        this.scancodes = new int[SCANCODE_ARRAY_SIZE];
        window.addKeyListener(this);
    }

    public boolean isPressed(int scancode) {
        if(scancode > MAX_SCANCODE)
            throw new IllegalArgumentException("Scancode may not exceed " + MAX_SCANCODE);

        return (scancodes[scancode / 32] & (1 << (scancode & 31))) != 0;
    }

    private void press(int scancode) {
        if(scancode > MAX_SCANCODE) {
            System.err.println("Received scancode " + scancode + ", which is bigger than " + MAX_SCANCODE);
            return;
        }

        scancodes[scancode / 32] |= (1 << (scancode & 31));
    }

    private void release(int scancode) {
        if(scancode > MAX_SCANCODE) {
            System.err.println("Received scancode " + scancode + ", which is bigger than " + MAX_SCANCODE);
            return;
        }

        scancodes[scancode / 32] &= ~(1 << (scancode & 31));
    }

    @ApiStatus.Internal
    @Override
    public void onKey(int key, int scancode, int action, @NotNull LongVolatileBitfield<Modifiers> mods) {
        if(action == GLFWValues.Actions.GLFW_PRESS)
            press(scancode);
        else if(action == GLFWValues.Actions.GLFW_RELEASE)
            release(scancode);
    }
}
