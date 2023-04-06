
#include "nat_GPUBuffer.h"
#include "OpenClWindowJava.h"

typedef struct {
    jlong selfPointer;
    jlong pointer;
    jint error;
} BufCreateReturn;

using namespace linusdev::java;

/*
 * Class:     de_linusdev_nat_GPUBuffer
 * Method:    _create
 * Signature: (JJIJ)Ljava/nio/ByteBuffer;
 */
JNIEXPORT jobject JNICALL Java_de_linusdev_openclwindow_nat_GPUBuffer__1create
  (JNIEnv* env, jobject obj, jlong winPointer, jlong clMemFlags, jint size, jlong dataPointer) {
    auto* win = (OpenClWindowJava*) winPointer;

    cl_int err;
    auto* buffer = new cl::Buffer(*(win->getContext()), clMemFlags, size, (void*) dataPointer, &err);
    auto* ret = new BufCreateReturn(0L, reinterpret_cast<jlong>(buffer), err);
    ret->selfPointer = reinterpret_cast<jlong>(ret);

    jobject byteBuf = env->NewDirectByteBuffer(ret, sizeof(BufCreateReturn));
    return byteBuf;
}

/*
 * Class:     de_linusdev_nat_GPUBuffer
 * Method:    _enqueueWriteBuffer
 * Signature: (JIIIJ)I
 */
JNIEXPORT jint JNICALL Java_de_linusdev_openclwindow_nat_GPUBuffer__1enqueueWriteBuffer
  (JNIEnv* env, jclass clazz, jlong pointer, jlong winPointer, jint blocking, jint offset, jint size, jlong dataPointer) {
    auto* buf = (cl::Buffer*) pointer;
    auto* win = (OpenClWindowJava*) winPointer;

    return win->getQueue()->enqueueWriteBuffer(*buf, blocking, offset, size, reinterpret_cast<const void*>(dataPointer),
                                        nullptr, nullptr);
}

/*
 * Class:     de_linusdev_nat_GPUBuffer
 * Method:    _delete
 * Signature: (J)J
 */
JNIEXPORT void JNICALL Java_de_linusdev_openclwindow_nat_GPUBuffer__1delete
        (JNIEnv* env, jclass clazz, jlong pointer) {
    delete (cl::Buffer*) pointer;
}

/*
 * Class:     de_linusdev_nat_GPUBuffer
 * Method:    _deleteReturnStruct
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_de_linusdev_openclwindow_nat_GPUBuffer__1deleteReturnStruct
        (JNIEnv* env, jclass clazz, jlong pointer) {
    delete (BufCreateReturn*) pointer;
}

