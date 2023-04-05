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

package de.linusdev.nat;

import de.linusdev.lutils.bitfield.IntBitFieldValue;

public enum Modifiers implements IntBitFieldValue {

    /**
     * @see GLFWValues.Modifiers#GLFW_MOD_SHIFT
     */
    SHIFT(GLFWValues.Modifiers.GLFW_MOD_SHIFT),

    /**
     * @see GLFWValues.Modifiers#GLFW_MOD_CONTROL
     */
    CONTROL(GLFWValues.Modifiers.GLFW_MOD_CONTROL),

    /**
     * @see GLFWValues.Modifiers#GLFW_MOD_ALT
     */
    ALT(GLFWValues.Modifiers.GLFW_MOD_ALT),

    /**
     * @see GLFWValues.Modifiers#GLFW_MOD_SUPER
     */
    SUPER(GLFWValues.Modifiers.GLFW_MOD_SUPER),

    /**
     * @see GLFWValues.Modifiers#GLFW_MOD_CAPS_LOCK
     */
    CAPS_LOCK(GLFWValues.Modifiers.GLFW_MOD_CAPS_LOCK),

    /**
     * @see GLFWValues.Modifiers#GLFW_MOD_NUM_LOCK
     */
    NUM_LOCK(GLFWValues.Modifiers.GLFW_MOD_NUM_LOCK),
    ;

    private final int value;

    Modifiers(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }
}
