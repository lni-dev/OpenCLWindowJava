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
import de.linusdev.openclwindow.listener.CharListener;
import de.linusdev.openclwindow.listener.KeyListener;
import de.linusdev.openclwindow.nat.OpenCLWindowJava;
import org.jetbrains.annotations.NotNull;

public class TextInput implements CharListener, KeyListener {

    private final @NotNull OpenCLWindowJava window;
    private final @NotNull StringBuffer string;

    private final @NotNull Listener listener;

    public TextInput(@NotNull OpenCLWindowJava window, @NotNull Listener listener) {
        this.window = window;
        this.listener = listener;
        this.string = new StringBuffer();
    }

    public void start() {
        window.addCharListener(this);
        window.addKeyListener(this);
    }

    public void stop() {
        window.removeCharListener(this);
        window.removeKeyListener(this);
    }

    public @NotNull StringBuffer getString() {
        return string;
    }

    @Override
    public synchronized void onChar(char[] chars) {
        string.append(chars);
        listener.onAdd(string, chars);
    }

    @Override
    public void onKey(int key, int scancode, int action, @NotNull LongVolatileBitfield<Modifiers> mods) {
        if(key == GLFWValues.Keys_US.GLFW_KEY_BACKSPACE && !string.isEmpty()) {
            char removed = string.charAt(string.length()-1);
            string.setLength(string.length()-1);
            listener.onRemove(string, removed);
        } else if(key == GLFWValues.Keys_US.GLFW_KEY_ENTER) {
            listener.onEnter(string);
        }
    }

    public interface Listener {
        void onAdd(@NotNull StringBuffer current,  char @NotNull[] added);

        void onRemove(@NotNull StringBuffer current, char removed);

        void onEnter(@NotNull StringBuffer current);
    }
}
