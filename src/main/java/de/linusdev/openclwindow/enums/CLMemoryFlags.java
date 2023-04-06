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

package de.linusdev.openclwindow.enums;

import de.linusdev.lutils.bitfield.IntBitFieldValue;

public enum CLMemoryFlags implements IntBitFieldValue {


    /**
     * This flag specifies that the memory object will be read and written by a kernel. This is the default.
     */
    CL_MEM_READ_WRITE((1 << 0)),

    /**
     * This flag specifies that the memory object will be written but not read by a kernel.
     * Reading from a buffer or image object created with CL_MEM_WRITE_ONLY inside a kernel is undefined.
     * CL_MEM_READ_WRITE and CL_MEM_WRITE_ONLY are mutually exclusive.
     */
    CL_MEM_WRITE_ONLY(1 << 1),

    /**
     * This flag specifies that the memory object is a read-only memory object when used inside a kernel.
     * Writing to a buffer or image object created with CL_MEM_READ_ONLY inside a kernel is undefined.
     * CL_MEM_READ_WRITE or CL_MEM_WRITE_ONLY and CL_MEM_READ_ONLY are mutually exclusive.
     */
    CL_MEM_READ_ONLY(1 << 2),

    /**
     * This flag is valid only if host_ptr is not NULL. If specified, it indicates that the application wants the OpenCL implementation to use memory referenced by host_ptr as the storage bits for the memory object.
     * OpenCL implementations are allowed to cache the buffer contents pointed to by host_ptr in device memory. This cached copy can be used when kernels are executed on a device.
     * The result of OpenCL commands that operate on multiple buffer objects created with the same host_ptr or overlapping host regions is considered to be undefined.
     */
    CL_MEM_USE_HOST_PTR(1 << 3),

    /**
     * This flag specifies that the application wants the OpenCL implementation to allocate memory from host accessible memory.
     * CL_MEM_ALLOC_HOST_PTR and CL_MEM_USE_HOST_PTR are mutually exclusive.
     */
    CL_MEM_ALLOC_HOST_PTR(1 << 4),

    /**
     * This flag is valid only if host_ptr is not NULL. If specified, it indicates that the application wants the OpenCL implementation to allocate memory for the memory object and copy the data from memory referenced by host_ptr.
     * CL_MEM_COPY_HOST_PTR and CL_MEM_USE_HOST_PTR are mutually exclusive.
     * CL_MEM_COPY_HOST_PTR can be used with CL_MEM_ALLOC_HOST_PTR to initialize the contents of the cl_mem object allocated using host-accessible (e.g. PCIe) memory.
     */
    CL_MEM_COPY_HOST_PTR(1 << 5),

    /**
     * This flag specifies that the host will only write to the memory object (using OpenCL APIs that enqueue a write or a map for write). This can be used to optimize write access from the host (e.g. enable write-combined allocations for memory objects for devices that communicate with the host over a system bus such as PCIe).
     */
    CL_MEM_HOST_WRITE_ONLY(1 << 7),

    /**
     * This flag specifies that the host will only read the memory object (using OpenCL APIs that enqueue a read or a map for read).
     * CL_MEM_HOST_WRITE_ONLY and CL_MEM_HOST_READ_ONLY are mutually exclusive.
     */
    CL_MEM_HOST_READ_ONLY(1 << 8),

    /**
     * This flag specifies that the host will not read or write the memory object.
     * CL_MEM_HOST_WRITE_ONLY or CL_MEM_HOST_READ_ONLY and CL_MEM_HOST_NO_ACCESS are mutually exclusive.
     */
    CL_MEM_HOST_NO_ACCESS(1 << 9),

    CL_MEM_SVM_FINE_GRAIN_BUFFER(1 << 10),
    CL_MEM_SVM_ATOMICS(1 << 11),
    CL_MEM_KERNEL_READ_AND_WRITE(1 << 12),


    ;

    private final int code;

    CLMemoryFlags(int code) {
        this.code = code;
    }

    @Override
    public int getValue() {
        return code;
    }
}
