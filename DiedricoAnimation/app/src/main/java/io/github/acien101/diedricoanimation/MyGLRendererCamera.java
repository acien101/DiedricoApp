package io.github.acien101.diedricoanimation;

import android.opengl.GLSurfaceView;

import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import io.github.acien101.diedricoanimation.vector.LineVector;
import io.github.acien101.diedricoanimation.DiedricoTo3D.PlaneVector;
import io.github.acien101.diedricoanimation.DiedricoTo3D.PointVector;

/**
 * Created by amil101 on 26/06/16.
 */
public class MyGLRendererCamera implements GLSurfaceView.Renderer{
    public static float viewX = 0.0f;
    public static float viewY = 0.0f;

    public static boolean notPressed;

    public static List<PointVector> pointVectors;
    public static List<LineVector> lineVectors;
    public static List<PlaneVector> planeVectors;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

    }

    @Override
    public void onDrawFrame(GL10 gl) {

    }

    public void setCamera(float x, float y, float z){
        viewX = viewX + x;
        viewY = viewY + y;
        //eyeZ = eyeZ + z;
    }

    public void setNotPressed(boolean notPressed){
        this.notPressed = notPressed;
    }

    //Used for pass the coords from the picture with the camera
    public void setPointCoords(List<PointVector> pointCoords) {
        this.pointVectors = pointCoords;

    }

    //Used for pass the coords from the picture with the camera
    public void setLineVectors(List<LineVector> lineVectors) {
        this.lineVectors = lineVectors;
    }

    //Used for pass the coords from the picture with the camera
    public void setPlaneVectors(List<PlaneVector> planeVectors) {
        this.planeVectors = planeVectors;
    }
}
