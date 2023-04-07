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

public class BBFloat2 extends BBFloatN {

    public static StructureInfo INFO = new StructureInfo(8, false, 0, 8, 0);

    public BBFloat2(boolean allocateBuffer) {
        super(2, allocateBuffer);
    }

    public float x() {
        return buf.get(0);
    }

    public float y() {
        return buf.get(1);
    }

    public @NotNull BBFloat2 x(float f) {
        buf.put(0, f);
        modified();
        return this;
    }

    public @NotNull BBFloat2 y(float f) {
        buf.put(1, f);
        modified();
        return this;
    }

    @Override
    protected @NotNull StructureInfo getInfo() {
        return INFO;
    }
}
