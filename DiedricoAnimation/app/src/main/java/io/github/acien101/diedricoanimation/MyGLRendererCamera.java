package io.github.acien101.diedricoanimation;

import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by amil101 on 26/06/16.
 */
public class MyGLRendererCamera implements GLSurfaceView.Renderer{
    static float viewX = 0.0f;
    static float viewY = 0.0f;

    static boolean notPressed;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

    }

    @Override
    public void onDrawFrame(GL10 gl) {

    }

    void setCamera(float x, float y, float z){
        viewX = viewX + x;
        viewY = viewY + y;
        //eyeZ = eyeZ + z;
    }

    void setNotPressed(boolean notPressed){
        this.notPressed = notPressed;
    }
}
