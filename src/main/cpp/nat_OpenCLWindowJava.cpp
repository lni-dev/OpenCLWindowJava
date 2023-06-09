// Copyright (c) 2023 Linus Andera
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

#include <iostream>
#include "nat_OpenCLWindowJava.h"
#include "OpenClWindowJava.h"

using namespace linusdev::java;

/*
 * Class:     de_linusdev_nat_openclwindow_OpenCLWindowJava
 * Method:    _create
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_de_linusdev_openclwindow_nat_OpenCLWindowJava__1create
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
JNIEXPORT void JNICALL Java_de_linusdev_openclwindow_nat_OpenCLWindowJava__1show
        (JNIEnv* env, jobject obj, jlong pointer) {
    auto* win = (OpenClWindowJava*) pointer;
    win->show();
}

/*
 * Class:     de_linusdev_openclwindow_nat_OpenCLWindowJava
 * Method:    _checkIfWindowShouldClose
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_de_linusdev_openclwindow_nat_OpenCLWindowJava__1checkIfWindowShouldClose
        (JNIEnv* env, jclass clazz, jlong pointer) {
    auto* win = (OpenClWindowJava*) pointer;
    return win->checkIfWindowShouldClose() ? JNI_TRUE : JNI_FALSE;
}

/*
 * Class:     de_linusdev_openclwindow_nat_OpenCLWindowJava
 * Method:    _render
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_de_linusdev_openclwindow_nat_OpenCLWindowJava__1render
        (JNIEnv* env, jclass clazz, jlong pointer) {
    auto* win = (OpenClWindowJava*) pointer;
    return win->render();
}

/*
 * Class:     de_linusdev_openclwindow_nat_OpenCLWindowJava
 * Method:    _swapBuffer
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_de_linusdev_openclwindow_nat_OpenCLWindowJava__1swapBuffer
        (JNIEnv* env, jclass clazz, jlong pointer) {
    auto* win = (OpenClWindowJava*) pointer;
    win->swapBuffer();
}

/*
 * Class:     de_linusdev_openclwindow_nat_OpenCLWindowJava
 * Method:    _destroy
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_de_linusdev_openclwindow_nat_OpenCLWindowJava__1destroy
        (JNIEnv* env, jclass clazz, jlong pointer) {
    auto* win = (OpenClWindowJava*) pointer;
    win->destroy();
}

/*
 * Class:     de_linusdev_oclwindow_nat_OpenCLWindowJava
 * Method:    _delete
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_de_linusdev_openclwindow_nat_OpenCLWindowJava__1delete
        (JNIEnv* env, jobject obj, jlong pointer) {
    delete (OpenClWindowJava*) pointer;
}

/*
 * Class:     de_linusdev_oclwindow_nat_OpenCLWindowJava
 * Method:    _setTitle
 * Signature: (JLjava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_de_linusdev_openclwindow_nat_OpenCLWindowJava__1setTitle
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
JNIEXPORT void JNICALL Java_de_linusdev_openclwindow_nat_OpenCLWindowJava__1setSize
        (JNIEnv* env, jobject obj, jlong pointer, jint width, jint height) {
    auto* win = (OpenClWindowJava*) pointer;
    win->setSize((int)width, (int)height);
}

/*
 * Class:     de_linusdev_oclwindow_nat_OpenCLWindowJava
 * Method:    _setBorderlessFullscreen
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_de_linusdev_openclwindow_nat_OpenCLWindowJava__1setBorderlessFullscreen
        (JNIEnv* env, jobject obj, jlong pointer) {
    auto* win = (OpenClWindowJava*) pointer;
    win->setBorderlessFullscreen();
}

/*
 * Class:     de_linusdev_oclwindow_nat_OpenCLWindowJava
 * Method:    _setProgramCode
 * Signature: (JLjava/lang/String;JLjava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_de_linusdev_openclwindow_nat_OpenCLWindowJava__1setProgramCode
        (JNIEnv* env, jobject obj, jlong pointer, jstring code, jstring options) {
    auto* win = (OpenClWindowJava*) pointer;
    const char* cStr = env->GetStringUTFChars(code, nullptr);
    const char* oStr = options == NULL ? NULL : env->GetStringUTFChars(options, nullptr);
    win->setProgramCode(cStr, oStr);
    env->ReleaseStringUTFChars(code, cStr);
    if(options != NULL)
        env->ReleaseStringUTFChars(code, oStr);
}

/*
 * Class:     de_linusdev_nat_openclwindow_OpenCLWindowJava
 * Method:    _setKernelArg
 * Signature: (JILjava/nio/ByteBuffer;I)I
 */
JNIEXPORT jint JNICALL Java_de_linusdev_openclwindow_nat_OpenCLWindowJava__1setKernelArg__JILjava_nio_ByteBuffer_2I
        (JNIEnv* env, jobject obj, jlong pointer, jint index, jobject buffer, jint bufSize) {
    auto* win = (OpenClWindowJava*) pointer;
    void* bufPointer = env->GetDirectBufferAddress(buffer);
    return win->setKernelArg(index, bufSize, bufPointer);
}

/*
 * Class:     de_linusdev_openclwindow_nat_OpenCLWindowJava
 * Method:    _setKernelArg
 * Signature: (JIJ)I
 */
JNIEXPORT jint JNICALL Java_de_linusdev_openclwindow_nat_OpenCLWindowJava__1setKernelArg__JIJ
        (JNIEnv* env, jclass clazz, jlong pointer, jint index, jlong clBufferPointer) {
    auto* win = (OpenClWindowJava*) pointer;
    auto* buffer = (cl::Buffer*) clBufferPointer;
    return win->setKernelArg(index, *buffer);
}

/*
 * Class:     de_linusdev_openclwindow_nat_OpenCLWindowJava
 * Method:    _glfwSetInputMode
 * Signature: (JII)V
 */
JNIEXPORT void JNICALL Java_de_linusdev_openclwindow_nat_OpenCLWindowJava__1glfwSetInputMode
        (JNIEnv* env, jclass clazz, jlong pointer, jint mode, jint value) {
    auto* win = (OpenClWindowJava*) pointer;
    glfwSetInputMode(win->getGLFWWindow(), mode, value);
}

/*
 * Class:     de_linusdev_openclwindow_nat_OpenCLWindowJava
 * Method:    _glfwRawMouseMotionSupported
 * Signature: ()Z
 */
JNIEXPORT jboolean JNICALL Java_de_linusdev_openclwindow_nat_OpenCLWindowJava_isRawMouseMotionSupported
        (JNIEnv* env, jclass clazz) {
    return glfwRawMouseMotionSupported() == GLFW_TRUE ? JNI_TRUE : JNI_FALSE;
}

JNIEXPORT jstring JNICALL Java_de_linusdev_openclwindow_nat_OpenCLWindowJava_getKeyName
        (JNIEnv* env, jclass clazz, jint key, jint scancode) {
    const char* name = glfwGetKeyName(key, scancode);
    return env->NewStringUTF(name);
}

/*
 * Class:     de_linusdev_openclwindow_nat_OpenCLWindowJava
 * Method:    _glfwSetCursorPos
 * Signature: (JDD)V
 */
JNIEXPORT void JNICALL Java_de_linusdev_openclwindow_nat_OpenCLWindowJava__1glfwSetCursorPos
        (JNIEnv* env, jclass clazz, jlong pointer, jdouble xpos, jdouble ypos) {
    auto* win = (OpenClWindowJava*) pointer;
    glfwSetCursorPos(win->getGLFWWindow(), xpos, ypos);
}

/*
 * Class:     de_linusdev_openclwindow_nat_OpenCLWindowJava
 * Method:    _createSharedRenderBuffer
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_de_linusdev_openclwindow_nat_OpenCLWindowJava__1createSharedRenderBuffer
        (JNIEnv* env, jclass clazz, jlong pointer) {
    auto* win = (OpenClWindowJava*) pointer;
    return win->createSharedRenderBuffer();
}

/*
 * Class:     de_linusdev_openclwindow_nat_OpenCLWindowJava
 * Method:    _setBaseKernelArgs
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_de_linusdev_openclwindow_nat_OpenCLWindowJava__1setBaseKernelArgs
        (JNIEnv* env, jclass clazz, jlong pointer) {
    auto* win = (OpenClWindowJava*) pointer;
    return win->setBaseKernelArgs();
}
