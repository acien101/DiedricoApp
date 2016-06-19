package io.github.acien101.diedricoanimation;

import android.content.Context;
import android.opengl.GLSurfaceView;

/**
 * Created by amil101 on 23/04/16.
 */
class MyGLSurfaceView extends GLSurfaceView {

    private final MyGLRendererPointProyection mRenderer;

    public MyGLSurfaceView(Context context){
        super(context);

        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2);

        mRenderer = new MyGLRendererPointProyection();

        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(mRenderer);

        // Render the view only when there is a change in the drawing data
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);

    }

    void setCamera(float x, float y, float z){
       mRenderer.addCameraPosition(x, y, z);
    }

    void setNotPressed(boolean notPressed){
        mRenderer.setNotPressed(notPressed);
    }
}