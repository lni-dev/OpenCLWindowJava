//
// Created by Linus on 29/03/2023.
//
#include <iostream>
#include "de_linusdev_nat_openclwindow_OpenCLWindowJava.h"
#include "OpenClWindowJava.h"

using namespace linusdev::java;

/*
 * Class:     de_linusdev_nat_openclwindow_OpenCLWindowJava
 * Method:    _create
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_de_linusdev_nat_openclwindow_OpenCLWindowJava__1create
        (JNIEnv* env, jobject obj) {

    jclass cl = env->GetObjectClass(obj);
    auto onKeyMethod = env->GetMethodID(cl, "onKey", "(IIII)V");
    env->CallVoidMethod(obj, onKeyMethod, 1, 2, 3, 4);

    env->NewGlobalRef(obj);

    return (jlong) new OpenClWindowJava(env, obj);
}

/*
 * Class:     de_linusdev_oclwindow_nat_OpenCLWindowJava
 * Method:    _show
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_de_linusdev_nat_openclwindow_OpenCLWindowJava__1show
        (JNIEnv* env, jobject obj, jlong pointer) {
    auto* win = (OpenClWindowJava*) pointer;
    win->show();
}

/*
 * Class:     de_linusdev_oclwindow_nat_OpenCLWindowJava
 * Method:    _delete
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_de_linusdev_nat_openclwindow_OpenCLWindowJava__1delete
        (JNIEnv* env, jobject obj, jlong pointer) {
    delete (OpenClWindowJava*) pointer;
}

/*
 * Class:     de_linusdev_oclwindow_nat_OpenCLWindowJava
 * Method:    _setTitle
 * Signature: (JLjava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_de_linusdev_nat_openclwindow_OpenCLWindowJava__1setTitle
        (JNIEnv* env, jobject obj, jlong pointer, jstring title) {
    auto* win = (OpenClWindowJava*) pointer;

    const char* cStr = env->GetStringUTFChars(title, nullptr);
    win->setTitle(cStr);
    env->ReleaseStringUTFChars(title, cStr);
}

/*
 * Class:     de_linusdev_oclwindow_nat_OpenCLWindowJava
 * Method:    _setSize
 * Signature: (JII)V
 */
JNIEXPORT void JNICALL Java_de_linusdev_nat_openclwindow_OpenCLWindowJava__1setSize
        (JNIEnv* env, jobject obj, jlong pointer, jint width, jint height) {
    auto* win = (OpenClWindowJava*) pointer;
    win->setSize((int)width, (int)height);
}

/*
 * Class:     de_linusdev_oclwindow_nat_OpenCLWindowJava
 * Method:    _setBorderlessFullscreen
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_de_linusdev_nat_openclwindow_OpenCLWindowJava__1setBorderlessFullscreen
        (JNIEnv* env, jobject obj, jlong pointer) {
    auto* win = (OpenClWindowJava*) pointer;
    win->setBorderlessFullscreen();
}

/*
 * Class:     de_linusdev_oclwindow_nat_OpenCLWindowJava
 * Method:    _setProgramCode
 * Signature: (JLjava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_de_linusdev_nat_openclwindow_OpenCLWindowJava__1setProgramCode
        (JNIEnv* env, jobject obj, jlong pointer, jstring code) {
    auto* win = (OpenClWindowJava*) pointer;
    const char* cStr = env->GetStringUTFChars(code, nullptr);
    win->setProgramCode(cStr);
    env->ReleaseStringUTFChars(code, cStr);
}

/*
 * Class:     de_linusdev_nat_openclwindow_OpenCLWindowJava
 * Method:    _setKernelArg
 * Signature: (JILjava/nio/ByteBuffer;I)I
 */
JNIEXPORT jint JNICALL Java_de_linusdev_nat_openclwindow_OpenCLWindowJava__1setKernelArg
        (JNIEnv* env, jobject obj, jlong pointer, jint index, jobject buffer, jint bufSize) {
    auto* win = (OpenClWindowJava*) pointer;
    void* bufPointer = env->GetDirectBufferAddress(buffer);
    return win->setKernelArg(index, bufSize, bufPointer);
}
