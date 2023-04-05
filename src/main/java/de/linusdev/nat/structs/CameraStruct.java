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

package de.linusdev.nat.structs;

import de.linusdev.nat.types.Float1;
import de.linusdev.nat.types.Float3;
import org.jetbrains.annotations.NotNull;

public class CameraStruct extends ComplexStructure {

    public static final StructureInfo INFO = new StructureInfo(
            Float3.INFO,
            Float3.INFO,
            Float1.INFO);

    public final @NotNull Float3 position = new Float3(false);
    public final @NotNull Float3 lookAtVector = new Float3(false);
    public final @NotNull Float1 distanceToScreen = new Float1(false);

    public CameraStruct(boolean allocateBuffer) {
        super();
        init(allocateBuffer, position, lookAtVector, distanceToScreen);
    }

    @Override
    protected @NotNull StructureInfo getInfo() {
        return INFO;
    }

    @Override
    public String toString() {
        return toString(position, lookAtVector, distanceToScreen);
    }
}
