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

package de.linusdev.openclwindow.buffer;

import de.linusdev.openclwindow.Utils;
import org.jetbrains.annotations.NotNull;

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

    public static long getHeapAddress(@NotNull ByteBuffer buffer) {
        return Utils.UNSAFE.getLong(buffer, BUFFER_VARIABLE_ADDRESS_OFFSET);
    }

}
