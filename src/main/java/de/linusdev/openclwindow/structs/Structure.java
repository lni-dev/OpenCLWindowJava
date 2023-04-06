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

import de.linusdev.openclwindow.buffer.BufferUtils;
import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;

public abstract class Structure implements Sizeable {

    protected ByteBuffer byteBuf;

    public void useBuffer(@NotNull ByteBuffer buffer) {

        if(buffer.capacity() != getRequiredSize()) {
            throw new IllegalArgumentException("buffer size must be " + getRequiredSize() + ", but is " + buffer.capacity());
        }

        byteBuf = buffer;
    }

    /**
     * Creates an 8 byte aligned direct byte buffer and calls {@link #useBuffer(ByteBuffer)}.
     */
    public void allocate() {
        useBuffer(BufferUtils.createAlignedByteBuffer(getRequiredSize(), 8));
    }

    /**
     * Size required for this {@link Structure} in bytes.
     * @return required size
     */
    @Override
    public int getRequiredSize() {
        return getInfo().getRequiredSize();
    }

    /**
     * Information about this {@link Structure}.
     * @return {@link StructureInfo}
     */
    protected abstract @NotNull StructureInfo getInfo();

    /**
     * Byte size of this {@link Structure}
     * @return byte size
     */
    public int size() {
        return byteBuf.capacity();
    }

    /**
     * Get the {@link ByteBuffer} backed by this {@link Structure}.
     * @return {@link ByteBuffer} of this {@link Structure}
     */
    public ByteBuffer getByteBuf() {
        return byteBuf;
    }
}
