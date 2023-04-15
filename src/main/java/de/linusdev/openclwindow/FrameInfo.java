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

package de.linusdev.openclwindow;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FrameInfo {
    private final int averageOverFrames;
    private @Nullable UpdateListener listener;

    private volatile double averageMillisBetweenFrames;
    private volatile double averageMillisRenderTime;
    private volatile double averageMillisAutoBufferTime;
    private volatile double averageMillisSwapBufferTime;

    long renderMillisSum = 0;
    long frameMillisSum = 0;
    long autoBufferMillisSum = 0;
    long swapBufferMillisSum = 0;

    int frameCount = 0;

    private double deltaTime = 1d / 60d;

    public FrameInfo(int averageOverFrames, @Nullable UpdateListener listener) {
        this.averageOverFrames = averageOverFrames;
        this.listener = listener;
    }

    public void setListener(@Nullable UpdateListener listener) {
        this.listener = listener;
    }

    public void submitFrame(long millis) {

        deltaTime = (deltaTime + ((double) millis) / 1000d) / 2d;

        frameMillisSum += millis;

        if(frameCount++ >= averageOverFrames) {
            averageMillisBetweenFrames = frameMillisSum / (double) frameCount;
            averageMillisRenderTime = renderMillisSum / (double) frameCount;
            averageMillisAutoBufferTime = autoBufferMillisSum / (double) frameCount;
            averageMillisSwapBufferTime = swapBufferMillisSum / (double) frameCount;

            frameCount = 0;
            renderMillisSum = 0;
            frameMillisSum = 0;
            autoBufferMillisSum = 0;
            swapBufferMillisSum = 0;

            if(listener != null)
                listener.onUpdate(this);
        }
    }

    public void submitRenderTime(long millis) {
        renderMillisSum += millis;
    }

    public void submitAutoBufferTime(long millis) {
        autoBufferMillisSum += millis;
    }

    public void submitSwapBufferTime(long millis) {
        swapBufferMillisSum += millis;
    }

    public double getFPS() {
        return 1000d / averageMillisBetweenFrames;
    }

    public double getDeltaTime() {
        return deltaTime;
    }

    public double getAverageMillisBetweenFrames() {
        return averageMillisBetweenFrames;
    }

    public double getAverageMillisAutoBufferTime() {
        return averageMillisAutoBufferTime;
    }

    public double getAverageMillisRenderTime() {
        return averageMillisRenderTime;
    }

    public double getAverageMillisSwapBufferTime() {
        return averageMillisSwapBufferTime;
    }

    public static interface UpdateListener {
        void onUpdate(@NotNull FrameInfo info);
    }
}
