package org.daleon.Test1;

import org.daleon.framework.Screen;
import org.daleon.framework.impl.GLGame;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MainClassTest1 extends GLGame {

    boolean firstTimeCreate = true;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config){
        super.onSurfaceCreated(gl,config);

        if(firstTimeCreate){
            Assets.load(this);
            firstTimeCreate = false;
        }else{
            Assets.reload();
        }

    }

    @Override
    public Screen getStartScreen(){
        return new MainScreen(this);
    }
    @Override
    public void onPause(){
        super.onPause();
    }
}
