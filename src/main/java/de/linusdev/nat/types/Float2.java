package de.linusdev.nat.types;

import de.linusdev.nat.structs.StructureInfo;
import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;

public class Float2 extends FloatN {

    public static StructureInfo INFO = new StructureInfo(8, false, 0, 8, 0);

    public Float2(boolean allocateBuffer) {
        super(2, allocateBuffer);
    }

    public Float2(@NotNull ByteBuffer buffer) {
        super(buffer, 2);
    }

    public float x() {
        return buf.get(0);
    }

    public float y() {
        return buf.get(1);
    }

    public @NotNull Float2 x(float f) {
        buf.put(0, f);
        return this;
    }

    public @NotNull Float2 y(float f) {
        buf.put(1, f);
        return this;
    }

    @Override
    protected @NotNull StructureInfo getInfo() {
        return INFO;
    }
}
