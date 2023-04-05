package de.linusdev.nat.structs;

import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public abstract class ComplexStructure extends Structure {

    protected @NotNull Structure[] items;

    public ComplexStructure() {

    }

    public void init(boolean allocateBuffer, @NotNull Structure @NotNull ... items) {
        this.items = items;
        if(allocateBuffer)
            allocate();
    }

    @Override
    public void useBuffer(@NotNull ByteBuffer buffer) {
        super.useBuffer(buffer);
        StructureInfo info = getInfo();

        int[] sizes = info.getSizes();

        byteBuf.position(0);
        byteBuf.limit(0);

        for(int i = 0; i < items.length ; i++) {
            byteBuf.position(byteBuf.limit() + sizes[i * 2]);
            byteBuf.limit(byteBuf.position() + sizes[i * 2 + 1]);
            items[i].useBuffer(byteBuf.slice().order(ByteOrder.nativeOrder()));
        }

        byteBuf.position(0);
        byteBuf.limit(byteBuf.capacity());
    }

    public String toString(@NotNull Structure @NotNull ... items) {
        return getInfo().toString(this.getClass().getSimpleName(), items);
    }
}
