package de.linusdev.nat.types;

import de.linusdev.nat.structs.Structure;
import de.linusdev.nat.structs.StructureInfo;
import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
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

