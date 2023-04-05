package de.linusdev.nat.types;

import de.linusdev.nat.structs.StructureInfo;
import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;

/**
 * 3 Component float vector. Has the same {@link #getRequiredSize()} as a {@link Float4}.
 */
public class Float3 extends FloatN {

    public static StructureInfo INFO = new StructureInfo(16, false, 0, 12, 4);

    public Float3(boolean allocateBuffer) {
        super(3, allocateBuffer);
    }

    public Float3(@NotNull ByteBuffer buffer) {
        super(buffer, 3);
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

    public @NotNull Float3 x(float f) {
        buf.put(0, f);
        return this;
    }

    public @NotNull Float3 y(float f) {
        buf.put(1, f);
        return this;
    }

    public @NotNull Float3 z(float f) {
        buf.put(2, f);
        return this;
    }

    @Override
    protected @NotNull StructureInfo getInfo() {
        return INFO;
    }
}
