//
// Created by Linus on 01/04/2023.
//

#include "../../../include/OpenClWindowJava.h"

namespace linusdev::java {

    OpenClWindowJava::OpenClWindowJava(JNIEnv *env, jobject obj) {
        setKeyListener(this);
        setMouseListener(this);

        this->env = env;
        this->globalJavaRef = env->NewGlobalRef(obj);

        jclass clazz = env->GetObjectClass(globalJavaRef);
        this->onKeyMethodId = env->GetMethodID(clazz, "onKey", "(IIII)V");
        this->onCursorMethodId = env->GetMethodID(clazz, "onMouseCursor", "(DD)V");
        this->onMouseButtonMethodId = env->GetMethodID(clazz, "onMouseButton", "(III)V");
    }

    void OpenClWindowJava::onKey(int key, int scancode, int action, int mods) {
        env->CallVoidMethod(globalJavaRef, onKeyMethodId, key, scancode, action, mods);
    }

    void OpenClWindowJava::onMouseCursor(double xpos, double ypos) {
        env->CallVoidMethod(globalJavaRef, onCursorMethodId, xpos, ypos);
    }

    void OpenClWindowJava::onMouseButton(int button, int action, int mods) {
        env->CallVoidMethod(globalJavaRef, onMouseButtonMethodId, button, action, mods);
    }

    OpenClWindowJava::~OpenClWindowJava() {
        env->DeleteGlobalRef(globalJavaRef);
    }

} // java