package de.linusdev.nat.buffer;

import de.linusdev.Utils;
import sun.misc.Unsafe;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class BufferUtils {

    private static final long BUFFER_VARIABLE_ADDRESS_OFFSET;
    static {
        try {
            BUFFER_VARIABLE_ADDRESS_OFFSET = Utils.UNSAFE.objectFieldOffset(Buffer.class.getDeclaredField("address"));
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    public static @NotNull ByteBuffer createAlignedByteBuffer(int capacity, long alignment) {
        ByteBuffer buffer = ByteBuffer.allocateDirect((int) (capacity + alignment - 1));
        long address = Utils.UNSAFE.getLong(buffer, BUFFER_VARIABLE_ADDRESS_OFFSET);

        if((address % (alignment)) == 0)
            return buffer.slice(0, capacity).order(ByteOrder.nativeOrder());

        System.out.printf("Buffer alignment (%d) fixed. Was %d. Moved by %d.\n", alignment, address, (alignment - (address % (alignment))));
        return buffer.slice((int) (alignment - (address % (alignment))), capacity).order(ByteOrder.nativeOrder());
    }

}
