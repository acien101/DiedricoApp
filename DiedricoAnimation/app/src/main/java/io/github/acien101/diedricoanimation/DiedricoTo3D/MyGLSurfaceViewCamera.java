package io.github.acien101.diedricoanimation.DiedricoTo3D;

import android.content.Context;
import android.opengl.GLSurfaceView;
import io.github.acien101.diedricoanimation.MyGLRendererCamera;

import java.util.List;


/**
 * Created by amil101 on 10/01/16.
 */
class MyGLSurfaceViewCamera extends GLSurfaceView {

    public MyGLSurfaceViewCamera(Context context, List<PointVector> coords, List<LineVector> lineVectors, List<PlaneVector> planeVectors, MyGLRendererCamera renderer){
        super(context);

        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2);

        renderer.setPointCoords(coords);
        renderer.setLineVectors(lineVectors);
        renderer.setPlaneVectors(planeVectors);

        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(renderer);
        // Render the view only when there is a change in the drawing data
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);

    }
}