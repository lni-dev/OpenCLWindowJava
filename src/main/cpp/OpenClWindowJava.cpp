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