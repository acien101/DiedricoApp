package io.github.acien101.diedricoanimation;

import android.content.Context;
import android.opengl.GLSurfaceView;

/**
 * Created by amil101 on 23/04/16.
 */
class MyGLSurfaceView extends GLSurfaceView {
    public MyGLSurfaceView(Context context, Renderer renderer){
        super(context);

        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2);

        setRenderer(renderer);

        // Render the view only when there is a change in the drawing data
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);

    }
}