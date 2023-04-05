package de.linusdev.nat.structs;

import de.linusdev.nat.buffer.BufferUtils;
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
