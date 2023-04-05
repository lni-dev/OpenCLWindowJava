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

import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public abstract class ComplexStructure extends Structure {

    protected @NotNull Structure[] items;

    public ComplexStructure() {

    }

    public void init(boolean allocateBuffer, @NotNull Structure @NotNull ... items) {
        this.items = items;
        if(allocateBuffer)
            allocate();
    }

    @Override
    public void useBuffer(@NotNull ByteBuffer buffer) {
        super.useBuffer(buffer);
        StructureInfo info = getInfo();

        int[] sizes = info.getSizes();

        byteBuf.position(0);
        byteBuf.limit(0);

        for(int i = 0; i < items.length ; i++) {
            byteBuf.position(byteBuf.limit() + sizes[i * 2]);
            byteBuf.limit(byteBuf.position() + sizes[i * 2 + 1]);
            items[i].useBuffer(byteBuf.slice().order(ByteOrder.nativeOrder()));
        }

        byteBuf.position(0);
        byteBuf.limit(byteBuf.capacity());
    }

    public String toString(@NotNull Structure @NotNull ... items) {
        return getInfo().toString(this.getClass().getSimpleName(), items);
    }
}
