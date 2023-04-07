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

package de.linusdev.openclwindow.nat;

import de.linusdev.openclwindow.OpenCLException;
import de.linusdev.openclwindow.enums.CLMemoryFlags;
import de.linusdev.openclwindow.enums.OpenCLErrorCodes;
import de.linusdev.openclwindow.buffer.BufferUtils;
import de.linusdev.lutils.bitfield.IntBitfield;
import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class GPUBuffer implements AutoCloseable {

    protected final long pointer;
    protected @NotNull OpenCLWindowJava window;

    public GPUBuffer(@NotNull OpenCLWindowJava win, @NotNull IntBitfield<CLMemoryFlags> clMemFlags, @NotNull ByteBuffer data) {
        this.window = win;

        ByteBuffer createRet = _create(win.getPointer(), clMemFlags.getValue(), data.capacity(), BufferUtils.getHeapAddress(data));
        createRet.order(ByteOrder.nativeOrder());

        this.pointer = createRet.getLong(8);

        int err = createRet.getInt(16);
        _deleteReturnStruct(createRet.getLong(0));

        if(err != OpenCLErrorCodes.CL_SUCCESS.getCode()) {
            _delete(pointer);
            throw new OpenCLException(OpenCLErrorCodes.checkError(err));
        }

    }

    public void enqueueWriteBuffer(boolean blocking, int offset, int size, @NotNull ByteBuffer data, boolean addOffsetToBufferAddress) {
        int err = _enqueueWriteBuffer(pointer, window.getPointer(), blocking, offset, size,
                addOffsetToBufferAddress ? (BufferUtils.getHeapAddress(data) + offset) :  BufferUtils.getHeapAddress(data));
        if(err != OpenCLErrorCodes.CL_SUCCESS.getCode())
            throw new OpenCLException(OpenCLErrorCodes.checkError(err));
    }

    @Override
    public void close() {
        _delete(pointer);
    }

    //package-private methods
    long getPointer() {
        return pointer;
    }

    //native methods
    protected native ByteBuffer _create(long winPointer, long clMemFlags, int size, long dataPointer);
    protected static native int _enqueueWriteBuffer(long pointer, long winPointer, boolean blocking, int offset, int size, long dataPointer);
    protected static native void _delete(long pointer);
    protected static native void _deleteReturnStruct(long pointer);




}
