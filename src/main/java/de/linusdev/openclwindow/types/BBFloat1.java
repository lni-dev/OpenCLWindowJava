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

package de.linusdev.openclwindow.types;

import de.linusdev.openclwindow.structs.StructureInfo;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class BBFloat1 extends BBFloatN {

    public static StructureInfo INFO = new StructureInfo(4, false, 0, 4, 0);

    public BBFloat1(boolean allocateBuffer) {
        super(1, allocateBuffer);
    }

    public float get() {
        return buf.get(0);
    }

    @SuppressWarnings("UnusedReturnValue")
    public @NotNull BBFloat1 set(float f) {
        buf.put(0, f);
        modified();
        return this;
    }

    @Override
    protected @NotNull StructureInfo getInfo() {
        return INFO;
    }
}
