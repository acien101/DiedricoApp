package io.github.acien101.diedricoanimation;

import android.content.Context;
import android.opengl.GLSurfaceView;

/**
 * Created by amil101 on 23/04/16.
 */
class MyGLSurfaceView extends GLSurfaceView {

    private final MyGLRenderer mRenderer;
    private final MyGLRendererPointProyection myGLRendererPointProyection;

    public static boolean pointProjection = false;

    public MyGLSurfaceView(Context context){
        super(context);

        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2);
        myGLRendererPointProyection = new MyGLRendererPointProyection();
        mRenderer = new MyGLRenderer();

        if(pointProjection == true){
            // Set the Renderer for drawing on the GLSurfaceView
            setRenderer(myGLRendererPointProyection);
        }
        else{
            // Set the Renderer for drawing on the GLSurfaceView
            setRenderer(mRenderer);

        }

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