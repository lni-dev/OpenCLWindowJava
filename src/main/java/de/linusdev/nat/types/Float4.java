package de.linusdev.nat.types;

import de.linusdev.nat.structs.StructureInfo;
import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;

public class Float4 extends FloatN {

    public static StructureInfo INFO = new StructureInfo(16, false, 0, 16, 0);

    public Float4(boolean allocateBuffer) {
        super(4, allocateBuffer);
    }

    public Float4(@NotNull ByteBuffer buffer) {
        super(buffer, 4);
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

    public float w() {
        return buf.get(3);
    }

    public @NotNull Float4 x(float f) {
        buf.put(0, f);
        return this;
    }

    public @NotNull Float4 y(float f) {
        buf.put(1, f);
        return this;
    }

    public @NotNull Float4 z(float f) {
        buf.put(2, f);
        return this;
    }

    public @NotNull Float4 w(float f) {
        buf.put(3, f);
        return this;
    }

    @Override
    protected @NotNull StructureInfo getInfo() {
        return INFO;
    }
}
