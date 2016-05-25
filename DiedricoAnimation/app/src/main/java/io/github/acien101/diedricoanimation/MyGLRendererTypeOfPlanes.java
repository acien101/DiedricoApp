package io.github.acien101.diedricoanimation;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by amil101 on 23/05/16.
 */
public class MyGLRendererTypeOfPlanes implements GLSurfaceView.Renderer{
    private Axis mAxis;
    private Axis mAxis2;

    private Plane crosswidePlane;
    private Plane horizontalPlane;
    private Plane frontalPlane;
    private Plane horizontalProjectionPlane;
    private Plane verticalProjectionPlane;
    private Plane groundLineParallelPlane;
    private Plane groundLineCuttedPlane;
    private Plane profilePlane;

    // mMVPMatrix is an abbreviation for "Model View Projection Matrix"
    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];
    private final float[] mRotationMatrix = new float[16];
    float blackColor[] = {0.0f, 0.0f, 0.0f, 1.0f};


    static float squareCoords[] = {
            -1.0f,  0.0f, 0.0f,   // top left
            1.0f, 0.0f, 0.0f,   // bottom left
            1.0f, 0.0f, -1.0f,   // bottom right
            -1.0f,  0.0f, -1.0f }; // top right

    static float squareCoords2[] = {
            0.0f,  1.0f, 0.0f,   // top left
            0.0f, -1.0f, 0.0f,   // bottom left
            0.0f, -1.0f, -1.0f,   // bottom right
            0.0f,  1.0f, -1.0f }; // top right


    @Override
    public void onSurfaceCreated(GL10 gl, javax.microedition.khronos.egl.EGLConfig config) {
        // Set the background frame color
        GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

        // initialize a triangle
        mAxis = new Axis(squareCoords);
        mAxis2 = new Axis(squareCoords2);

        /*
        crosswidePlane = new Plane(-0.1f, 0.9f, 0.9f, -0.9f);
        horizontalPlane = new Plane(-0.1f, 0.5f, 1.0f, -0.1f);
        frontalPlane = new Plane(-0.1f, 1.0f, 0.5f, 1.0f);

        horizontalProjectionPlane;
        verticalProjectionPlane;
        groundLineParallelPlane;
        groundLineCuttedPlane;
        profilePlane;

        */
    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);

        float ratio = (float) width / height;

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
    }

    public void onDrawFrame(GL10 unused) {
        float[] scratch = new float[16];

        // Redraw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        // Position the eye behind the origin.
        final float eyeX = 4.0f;
        final float eyeY = 1.0f;
        final float eyeZ = 3f;

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

        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

        Matrix.setIdentityM(mRotationMatrix, 0);

        Matrix.translateM(mRotationMatrix, 0, 0, 0, 0);

        //Assign mRotationMatrix a rotation with the seekbar
        Matrix.rotateM(mRotationMatrix, 0, (SystemClock.uptimeMillis() % 6000L) * 0.060f, 0.0f, 0.5f, 0.0f);

        Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, mRotationMatrix, 0);

        // Draw shape
        mAxis.draw(scratch);
        mAxis2.draw(scratch);


        /*
        crosswidePlane.draw(scratch);
        horizontalPlane.draw(scratch);
        frontalPlane.draw(scratch);

        */


    }

    public static int loadShader(int type, String shaderCode){

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }
}
