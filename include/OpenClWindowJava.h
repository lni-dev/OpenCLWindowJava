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

#ifndef OPENCLWINDOWJAVA_OPENCLWINDOWJAVA_H
#define OPENCLWINDOWJAVA_OPENCLWINDOWJAVA_H

#include <jni.h>

#include "OpenClWindow.h"
#include "KeyListener.h"
#include "MouseListener.h"

namespace linusdev::java {

    class OpenClWindowJava : public OpenClWindow, KeyListener, MouseListener {

    private:

        JNIEnv* env;
        jobject globalJavaRef;
        jmethodID onKeyMethodId;
        jmethodID onCursorMethodId;
        jmethodID onMouseButtonMethodId;

        void onKey(int key, int scancode, int action, int mods) override;

        void onMouseCursor(double xpos, double ypos) override;

        void onMouseButton(int button, int action, int mods) override;

    public:
        OpenClWindowJava(JNIEnv* env, jobject obj);
        ~OpenClWindowJava() override;
    };

} // java

#endif //OPENCLWINDOWJAVA_OPENCLWINDOWJAVA_H
