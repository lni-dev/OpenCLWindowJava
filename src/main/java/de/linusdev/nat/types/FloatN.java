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

package de.linusdev.nat.types;

import de.linusdev.nat.structs.Structure;
import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

@SuppressWarnings("unused")
abstract class FloatN extends Structure {

    protected FloatBuffer buf;
    protected final int memberCount;

    public FloatN(@NotNull ByteBuffer buf, int count) {
        this.memberCount = count;
        useBuffer(buf);
    }

    public FloatN(int count, boolean allocateBuffer) {
        this.memberCount = count;
        if(allocateBuffer)
            allocate();
    }

    @Override
    public void useBuffer(@NotNull ByteBuffer buffer) {
        super.useBuffer(buffer);
        buf = buffer.asFloatBuffer();
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
