/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class de_linusdev_nat_openclwindow_OpenCLWindowJava */

#ifndef _Included_de_linusdev_nat_openclwindow_OpenCLWindowJava
#define _Included_de_linusdev_nat_openclwindow_OpenCLWindowJava
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     de_linusdev_nat_openclwindow_OpenCLWindowJava
 * Method:    _create
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_de_linusdev_nat_openclwindow_OpenCLWindowJava__1create
        (JNIEnv *, jobject);

/*
 * Class:     de_linusdev_nat_openclwindow_OpenCLWindowJava
 * Method:    _show
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_de_linusdev_nat_openclwindow_OpenCLWindowJava__1show
        (JNIEnv *, jobject, jlong);

/*
 * Class:     de_linusdev_nat_openclwindow_OpenCLWindowJava
 * Method:    _delete
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_de_linusdev_nat_openclwindow_OpenCLWindowJava__1delete
        (JNIEnv *, jobject, jlong);

/*
 * Class:     de_linusdev_nat_openclwindow_OpenCLWindowJava
 * Method:    _setTitle
 * Signature: (JLjava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_de_linusdev_nat_openclwindow_OpenCLWindowJava__1setTitle
        (JNIEnv *, jobject, jlong, jstring);

/*
 * Class:     de_linusdev_nat_openclwindow_OpenCLWindowJava
 * Method:    _setSize
 * Signature: (JII)V
 */
JNIEXPORT void JNICALL Java_de_linusdev_nat_openclwindow_OpenCLWindowJava__1setSize
        (JNIEnv *, jobject, jlong, jint, jint);

/*
 * Class:     de_linusdev_nat_openclwindow_OpenCLWindowJava
 * Method:    _setBorderlessFullscreen
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_de_linusdev_nat_openclwindow_OpenCLWindowJava__1setBorderlessFullscreen
        (JNIEnv *, jobject, jlong);

/*
 * Class:     de_linusdev_nat_openclwindow_OpenCLWindowJava
 * Method:    _setProgramCode
 * Signature: (JLjava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_de_linusdev_nat_openclwindow_OpenCLWindowJava__1setProgramCode
        (JNIEnv *, jobject, jlong, jstring);

/*
 * Class:     de_linusdev_nat_openclwindow_OpenCLWindowJava
 * Method:    _setKernelArg
 * Signature: (JILjava/nio/ByteBuffer;I)I
 */
JNIEXPORT jint JNICALL Java_de_linusdev_nat_openclwindow_OpenCLWindowJava__1setKernelArg
        (JNIEnv *, jobject, jlong, jint, jobject, jint);

#ifdef __cplusplus
}
#endif
#endif