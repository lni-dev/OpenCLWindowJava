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

package de.linusdev.openclwindow.structs;

import org.jetbrains.annotations.NotNull;

public class StructureArray extends ComplexStructure {

    private final @NotNull Sizeable type;
    @SuppressWarnings("NotNullFieldNotInitialized") // init in calculateInfo()
    private @NotNull StructureInfo info;

    private final int size;

    public StructureArray(boolean trackModifications, @NotNull Sizeable type, int size) {
        super(trackModifications);
        this.type = type;
        this.size = size;
        this.items = new Structure[size];
        calculateInfo();
        allocate();
    }

    public void set(int index, @NotNull Structure struct) {
        items[index] = struct;
    }

    private void calculateInfo() {
        this.info = new StructureInfo(type.getRequiredSize(), false, 0, type.getRequiredSize() * size, 0);
    }

    @Override
    protected @NotNull StructureInfo getInfo() {
        return info;
    }
}
