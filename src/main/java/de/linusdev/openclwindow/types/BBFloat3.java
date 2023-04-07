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

/**
 * 3 Component float vector. Has the same {@link #getRequiredSize()} as a {@link BBFloat4}.
 */
@SuppressWarnings("UnusedReturnValue")
public class BBFloat3 extends BBFloatN {

    public static StructureInfo INFO = new StructureInfo(16, false, 0, 12, 4);

    public BBFloat3(boolean allocateBuffer) {
        super(3, allocateBuffer);
    }

    public float x() {
        return buf.get(0);
    }

    public float y() {
        return buf.get(1);
    }

    public float z() {
        return buf.get(2);
    }

    public @NotNull BBFloat3 x(float f) {
        buf.put(0, f);
        modified();
        return this;
    }

    public @NotNull BBFloat3 y(float f) {
        buf.put(1, f);
        modified();
        return this;
    }

    public @NotNull BBFloat3 z(float f) {
        buf.put(2, f);
        modified();
        return this;
    }

    public @NotNull BBFloat3 xyz(float x, float y, float z) {
        buf.put(0, x);
        buf.put(1, y);
        buf.put(2, z);
        modified();
        return this;
    }

    @Override
    protected @NotNull StructureInfo getInfo() {
        return INFO;
    }
}
