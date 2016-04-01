package io.github.acien101.diedricoto3d.openGL;

import android.content.Context;
import android.opengl.GLSurfaceView;

import java.util.List;

import io.github.acien101.diedricoto3d.LineVector;
import io.github.acien101.diedricoto3d.PlaneVector;
import io.github.acien101.diedricoto3d.PointVector;

/**
 * Created by amil101 on 10/01/16.
 */
class MyGLSurfaceView extends GLSurfaceView {

    private final MyGLRenderer mRenderer;


    public MyGLSurfaceView(Context context, List<PointVector> coords, List<LineVector> lineVectors, List<PlaneVector> planeVectors){
        super(context);

        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2);

        mRenderer = new MyGLRenderer();
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

    public void setPointCoords(List<PointVector> coords){
        mRenderer.setPointCoords(coords);

    }


}