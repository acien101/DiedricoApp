package io.github.acien101.diedricoanimation;

import android.opengl.GLES20;
import android.opengl.Matrix;
import android.os.SystemClock;
import android.util.Log;

import javax.microedition.khronos.opengles.GL10;

import io.github.acien101.diedricoanimation.openGL.AlejamientoModel;
import io.github.acien101.diedricoanimation.openGL.Axis;
import io.github.acien101.diedricoanimation.openGL.CotaModel;
import io.github.acien101.diedricoanimation.openGL.GLPoint;
import io.github.acien101.diedricoanimation.openGL.ImportModel;
import io.github.acien101.diedricoanimation.openGL.Line;
import io.github.acien101.diedricoanimation.vector.PointVector;

/**
 * Created by amil101 on 28/04/16.
 */
public class MyGLRendererPointProyection extends MyGLRendererCamera{
    private Axis mAxis;
    private Axis mAxis2;

    private Line mLine;

    private GLPoint mPoint;

    private ImportModel cota;     //We import the model cota and the model alejamiento
    private ImportModel alejamiento;

    private DiscontinuousLine mDiscontinuousLineX;
    private DiscontinuousLine mDicontinuousLineY;

    // mMVPMatrix is an abbreviation for "Model View Projection Matrix"
    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];
    private final float[] mTranslationMatrix = new float[16];
    float blackColor[] = {0.0f, 0.0f, 0.0f, 1.0f};
    private final float[] mRotationMatrix = new float[16];

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

    PointVector pointVectorCoords = new PointVector(0.5f, 0.5f, 0.0f);

    public MyGLRendererPointProyection(CreateDiedrico createDiedrico){
        Log.i("asdf", "asdfasdf");
        createDiedrico.addDiedricoPoint(pointVectorCoords);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, javax.microedition.khronos.egl.EGLConfig config) {
        // Set the background frame color
        GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

        // initialize a triangle
        mAxis = new Axis(squareCoords);
        mAxis2 = new Axis(squareCoords2);

        mLine = new Line(0.0f, 0.0f, 0.5f, 0.0f, 0.0f, -0.5f, blackColor);

        mPoint = new GLPoint(10,10,0.01f, 0.7f);

        mDiscontinuousLineX = new DiscontinuousLine(new PointVector(0.5f, 0.5f, 0.0f), new PointVector(0.5f, 0.0f, 0.0f), blackColor, 10);
        mDicontinuousLineY = new DiscontinuousLine(new PointVector(0.5f, 0.5f, 0.0f), new PointVector(0.0f, 0.5f, 0.0f), blackColor, 10);

        cota = new ImportModel(new CotaModel());
        alejamiento = new ImportModel(new AlejamientoModel());
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
        float[] rotation = new float[16];

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

        // Calculate the projection and view transformation
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

        Matrix.multiplyMM(rotation, 0, mMVPMatrix, 0, mRotationMatrix, 0);

        Matrix.setIdentityM(mTranslationMatrix, 0);

        Matrix.translateM(mTranslationMatrix, 0, (float) pointVectorCoords.getPointX(), (float) pointVectorCoords.getPointY(), (float) pointVectorCoords.getPointZ());

        Matrix.multiplyMM(scratch, 0, rotation, 0, mTranslationMatrix, 0);

        // Draw shape
        mAxis.draw(rotation);
        mAxis2.draw(rotation);

        mLine.draw(rotation);

        mDiscontinuousLineX.draw(rotation);
        mDicontinuousLineY.draw(rotation);

        mPoint.draw(scratch);


        //Moving the cotaModel
        Matrix.setIdentityM(mTranslationMatrix, 0);
        Matrix.translateM(mTranslationMatrix, 0, 0.75f, 0.25f, 0.0f);
        Matrix.multiplyMM(scratch, 0, rotation, 0, mTranslationMatrix, 0);

        cota.draw(scratch);

        //Moving the alejamientoModel
        Matrix.setIdentityM(mTranslationMatrix, 0);
        Matrix.translateM(mTranslationMatrix, 0, 0.4f, 0.6f, 0.0f);
        Matrix.multiplyMM(scratch, 0, rotation, 0, mTranslationMatrix, 0);

        alejamiento.draw(scratch);

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
