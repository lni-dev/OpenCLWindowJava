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
import org.jetbrains.annotations.NotNull;

import java.nio.FloatBuffer;

@SuppressWarnings("unused")
abstract class BBFloatN extends Structure implements FloatN {

    protected FloatBuffer buf;
    protected final int memberCount;

    public BBFloatN(@NotNull Structure mostParentStructure, int offset, int count) {
        this.memberCount = count;
        useBuffer(mostParentStructure, offset);
    }

    public BBFloatN(int count, boolean allocateBuffer) {
        this.memberCount = count;
        if(allocateBuffer)
            allocate();
    }

    public void modified() {
        modified(0, getSize());
    }

    @Override
    public void useBuffer(@NotNull Structure mostParentStructure, int offset) {
        super.useBuffer(mostParentStructure, offset);
        buf = byteBuf.asFloatBuffer();
    }

    /**
     * Count of floats in this buffer.
     * @return float count in this buffer
     */
    @Override
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

    @Override
    public float get(int index) {
        return buf.get(index);
    }

    @Override
    public @NotNull BBFloatN put(int index, float value) {
        buf.put(index, value);
        return this;
    }

}
