package de.linusdev.nat.types;

import de.linusdev.nat.structs.StructureInfo;
import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;

@SuppressWarnings("unused")
public class Float1 extends FloatN {

    public static StructureInfo INFO = new StructureInfo(4, false, 0, 4, 0);

    public Float1(boolean allocateBuffer) {
        super(1, allocateBuffer);
    }

    public Float1(@NotNull ByteBuffer buffer) {
        super(buffer, 1);
    }

    public float get() {
        return buf.get(0);
    }

    @SuppressWarnings("UnusedReturnValue")
    public @NotNull Float1 set(float f) {
        buf.put(0, f);
        return this;
    }

    @Override
    protected @NotNull StructureInfo getInfo() {
        return INFO;
    }
}
