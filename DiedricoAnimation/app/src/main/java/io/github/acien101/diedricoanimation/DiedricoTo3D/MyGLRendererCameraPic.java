package io.github.acien101.diedricoanimation.DiedricoTo3D;

import android.opengl.GLES20;
import android.opengl.Matrix;
import android.os.SystemClock;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import io.github.acien101.diedricoanimation.openGL.Axis;
import io.github.acien101.diedricoanimation.openGL.GLPoint;
import io.github.acien101.diedricoanimation.openGL.Plane;
import io.github.acien101.diedricoanimation.openGL.Line;
import io.github.acien101.diedricoanimation.MyGLRendererCamera;

/**
 * Created by amil101 on 10/01/16.
 */
public class MyGLRendererCameraPic extends MyGLRendererCamera{

    private Axis mAxis;
    private Axis mAxis2;

    private List<GLPoint> mPoints = new ArrayList<>();
    private List<Line> mLines = new ArrayList<>();
    private List<Plane> mPlane = new ArrayList<>();

    static float squareCoords[] = {
            -1.0f,  0.0f, 0.5f,   // top left
            1.0f, 0.0f, 0.5f,   // bottom left
            1.0f, 0.0f, -0.5f,   // bottom right
            -1.0f,  0.0f, -0.5f }; // top right

    static float squareCoords2[] = {
            0.0f,  1.0f, 0.5f,   // top left
            0.0f, -1.0f, 0.5f,   // bottom left
            0.0f, -1.0f, -0.5f,   // bottom right
            0.0f,  1.0f, -0.5f }; // top right

    // mMVPMatrix is an abbreviation for "Model View Projection Matrix"
    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];
    private final float[] mRotationMatrix = new float[16];
    private final float[] mTranslationMatrix = new float[16];
    float blackColor[] = {0.0f, 0.0f, 0.0f, 1.0f};

    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        Log.i("created", "created");
        // Set the background frame color
        GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

        // initialize a triangle
        mAxis = new Axis(squareCoords);
        mAxis2 = new Axis(squareCoords2);

        // initialize the points
        for(int i = 0; i < pointVectors.size(); i++){
            mPoints.add(new GLPoint(10,10,0.01f, 0.7f));
        }
        // initialize the lines
        for(int i = 0; i< lineVectors.size(); i++){
            mLines.add(new Line(lineVectors.get(i).getLineYA(), lineVectors.get(i).getLineXA(), 0.5f - lineVectors.get(i).getLineZA(), lineVectors.get(i).getLineYB(), lineVectors.get(i).getLineXB(), 0.5f - lineVectors.get(i).getLineZB(), blackColor));
        }
        // initialize the planes
        for(int i = 0; i < planeVectors.size(); i++){
            mPlane.add(new Plane(0.5f - planeVectors.get(i).getPlaneOriginZ(), planeVectors.get(i).getPlaneY(), planeVectors.get(i).getPlaneX(), 0.5f - planeVectors.get(i).getPlaneZ()));
        }
    }

    public void onDrawFrame(GL10 unused) {
        float[] scratch = new float[16];

        // Redraw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        // Position the eye behind the origin.
        final float eyeX = 4.0f;
        final float eyeY = 1.0f;
        final float eyeZ = 4f;

        // We are looking toward the distance
        final float lookX = -5.0f;
        final float lookY = -1.0f;
        final float lookZ = -5.0f;

        // Set our up vector. This is where our head would be pointing were we holding the camera.
        final float upX = 0.0f;
        final float upY = 1.0f;
        final float upZ = 0.0f;

        // Set the view matrix. This matrix can be said to represent the camera position.
        // NOTE: In OpenGL 1, a ModelView matrix is used, which is a combination of a model and
        // view matrix. In OpenGL 2, we can keep track of these matrices separately if we choose.
        Matrix.setLookAtM(mViewMatrix, 0, eyeX, eyeY, eyeZ, lookX, lookY, lookZ, upX, upY, upZ);

        // Create a rotation and translation for the cube
        Matrix.setIdentityM(mRotationMatrix, 0);

        Matrix.translateM(mRotationMatrix, 0, 0, 0, 0);

        if(notPressed){
            Matrix.rotateM(mRotationMatrix, 0, (SystemClock.uptimeMillis() % 6000L) * 0.060f, 0.0f, 1.0f, 0.0f);
        }
        else{
            //Assign mRotationMatrix a rotation with the time
            Matrix.rotateM(mRotationMatrix, 0, viewX, 0.0f, 0.1f, 0.0f);
            Matrix.rotateM(mRotationMatrix, 0, viewY, 0.0f, 0.0f, 0.1f);
        }

        // combine the model with the view matrix
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

        // combine the model-view with the projection matrix
        Matrix.multiplyMM(mMVPMatrix, 0, mMVPMatrix, 0, mRotationMatrix, 0);

        // we draw the points and we move to the place they must be
        for(int i = 0; i < mPoints.size(); i++){
            Matrix.setIdentityM(mTranslationMatrix, 0);
            Matrix.translateM(mTranslationMatrix, 0, ((float) pointVectors.get(i).getPointY()), ((float) pointVectors.get(i).getPointX()), (0.5f - (float) pointVectors.get(i).getPointZ()));
            Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, mTranslationMatrix, 0);

            mPoints.get(i).draw(scratch);
        }

        // we draw the lines
        for(int i = 0; i < mLines.size(); i++){
            mLines.get(i).draw(mMVPMatrix);
        }

        // we draw the planes
        for(int i = 0; i < mPlane.size(); i++){
            mPlane.get(i).draw(mMVPMatrix);
        }

        // Draw shape
        mAxis.draw(mMVPMatrix);
        mAxis2.draw(mMVPMatrix);

    }

    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);

        float ratio = (float) width / height;

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
    }

    public static int loadShader(int type, String shaderCode) {

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }

}