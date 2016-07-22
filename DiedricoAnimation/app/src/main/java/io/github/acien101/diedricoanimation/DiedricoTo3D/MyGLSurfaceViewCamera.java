package io.github.acien101.diedricoanimation.DiedricoTo3D;

import android.content.Context;
import android.opengl.GLSurfaceView;

import java.util.List;


/**
 * Created by amil101 on 10/01/16.
 */
class MyGLSurfaceViewCamera extends GLSurfaceView {

    private final MyGLRendererCamera mRenderer;


    public MyGLSurfaceViewCamera(Context context, List<PointVector> coords, List<LineVector> lineVectors, List<PlaneVector> planeVectors){
        super(context);

        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2);

        mRenderer = new MyGLRendererCamera();
        mRenderer.setPointCoords(coords);
        mRenderer.setLineVectors(lineVectors);
        mRenderer.setPlaneVectors(planeVectors);

        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(mRenderer);
        // Render the view only when there is a change in the drawing data
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);

    }

    public void setRenderer(int progress){

        mRenderer.setZoom(progress);
        requestRender();
    }
}