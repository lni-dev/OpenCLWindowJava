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

import de.linusdev.openclwindow.structs.Structure;
import de.linusdev.openclwindow.structs.StructureInfo;
import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

@SuppressWarnings("unused")
public class IntN extends Structure {

    public static StructureInfo INFO = new StructureInfo(4, false, 0, 4, 0);


    protected IntBuffer buf;
    protected final int memberCount;

    public IntN(@NotNull ByteBuffer buf, int count) {
        this.memberCount = count;
        useBuffer(buf);
    }

    public IntN(int count, boolean allocateBuffer) {
        this.memberCount = count;
        if(allocateBuffer)
            allocate();
    }

    @Override
    public void useBuffer(@NotNull ByteBuffer buffer) {
        super.useBuffer(buffer);
        buf = buffer.asIntBuffer();
    }

    @Override
    protected @NotNull StructureInfo getInfo() {
        return INFO;
    }

    /**
     * Count of floats in this buffer.
     * @return float count in this buffer
     */
    public int getMemberCount() {
        return memberCount;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        boolean first = true;
        for(int i = 0; i < memberCount; i++) {
            if(first) first = false;
            else sb.append(", ");
            sb.append(buf.get(i));
        }

        return String.format("float%d(%s)", memberCount, sb);
    }
}

