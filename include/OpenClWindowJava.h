//
// Created by Linus on 01/04/2023.
//

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
