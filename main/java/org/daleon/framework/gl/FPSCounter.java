package org.daleon.framework.gl;

import android.util.Log;

public class FPSCounter {
    static long  startTime = System.nanoTime();
    static int  frames = 0;
    static int previousnFrame = 0;

    public static int logFrame(){
        frames++;
        if(System.nanoTime() - startTime >= 1000000000) {
            Log.d("FPSCounter","fps: " + frames);
            previousnFrame = frames;
            frames = 0;
            startTime = System.nanoTime();

        }
        return previousnFrame;
    }
}
