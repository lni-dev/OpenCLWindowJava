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

import de.linusdev.lutils.bitfield.IntBitfield;
import de.linusdev.openclwindow.enums.CLMemoryFlags;
import de.linusdev.openclwindow.nat.GPUBuffer;
import de.linusdev.openclwindow.nat.OpenCLWindowJava;
import de.linusdev.openclwindow.structs.ModificationInfo;
import de.linusdev.openclwindow.structs.Structure;
import org.jetbrains.annotations.NotNull;

public class AutoUpdateGPUBuffer implements AutoCloseable, HasGPUBuffer {

    private final @NotNull GPUBuffer gpuBuffer;
    private final @NotNull Structure structure;


    public AutoUpdateGPUBuffer(@NotNull OpenCLWindowJava window, @NotNull Structure structure, @NotNull BufferAccess access,
                               CLMemoryFlags hostPointerType) {
        this.structure = structure;
        this.gpuBuffer = new GPUBuffer(window, access.getValue() | hostPointerType.getValue(), structure.getByteBuf());
        window.addAutoUpdateBuffer(this);
    }

    public AutoUpdateGPUBuffer(@NotNull OpenCLWindowJava window, @NotNull Structure structure, CLMemoryFlags... flags) {
        this.structure = structure;
        IntBitfield<CLMemoryFlags> flagsField = new IntBitfield<>();
        flagsField.set(flags);
        this.gpuBuffer = new GPUBuffer(window, flagsField.getValue(), structure.getByteBuf());
        window.addAutoUpdateBuffer(this);
    }

    public boolean check() {
        if (structure.isModified()) {

            //set structure to unmodified first. During copying there may be coming in new modifications,
            //that must be copied in the next check()...
            structure.unmodified();

            if (!structure.hasModificationsInfo()) {
                //No info about the modifications given. Copy the complete buffer.
                gpuBuffer.enqueueWriteBuffer(false,
                        0, structure.getSize(),
                        structure.getByteBuf(), false);
                return true;
            }

            //Acquire lock for this structure's modifications info.
            structure.acquireModificationLock();
            try {
                ModificationInfo first = structure.getFirstModificationInfo(true);

                while (first != null) {
                    gpuBuffer.enqueueWriteBuffer(false,
                            first.startOffset, first.endOffset - first.startOffset,
                            structure.getByteBuf(), true);
                    first = first.next;
                }
            } finally {
                structure.releaseModificationLock();
            }

            return true;
        }
        return false;
    }

    @Override
    public void close() {
        this.gpuBuffer.close();
    }

    @Override
    public @NotNull GPUBuffer getGPUBuffer() {
        return gpuBuffer;
    }
}
