package io.github.acien101.diedricoanimation.DiedricoTo3D;

import android.app.Activity;
import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.List;
import io.github.acien101.diedricoanimation.vector.LineVector;



public class OpenGlActivity extends Activity {

    private GLSurfaceView mGLView;

    MyGLRendererCameraPic renderer;
    List<PointVector> pointVectors;
    List<LineVector> lineVectors;
    List<PlaneVector> planeVectors;

    Intent intent;

    float initX;      //Is the value of the X coordenate when we press the screen
    float initY;      //The Y

    float moveX;    //Is the value of the X movement
    float moveY;    //Is the value of the Y movement

    boolean pressed;            //if the OpenGL is pressed
    long currentTime;           //The time of the thread, for rotate the camera if the user don't press the screen

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = getIntent();

        pointVectors = intent.getParcelableArrayListExtra("points");
        lineVectors = intent.getParcelableArrayListExtra("lines");
        planeVectors = intent.getParcelableArrayListExtra("planes");

        threadTime();               //start the thread, for rotate the camera if the user don't press the screen
        pressed = false;

        renderer = new MyGLRendererCameraPic();
        mGLView = new MyGLSurfaceViewCamera(this, pointVectors, lineVectors, planeVectors, renderer);
        mGLView.setOnTouchListener(listenerForCamera());
        setContentView(mGLView);
    }

    public View.OnTouchListener listenerForCamera(){
        return new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        renderer.setNotPressed(false);

                        initX = event.getX();
                        initY = event.getY();

                        Log.i("toco", "X " + event.getX() + " Y " + event.getY());

                        pressed = true;
                        return true;


                    case MotionEvent.ACTION_MOVE:
                        moveX = (event.getX() - initX);
                        moveY = -(event.getY() - initY);

                        renderer.setCamera(moveX, moveY, 0);

                        initX = event.getX();
                        initY = event.getY();

                        return true;

                    case MotionEvent.ACTION_UP:
                        pressed = false;
                        threadTime();
                        return true;
                }
                return false;
            }
        };
    }

    public void threadTime(){
        new Thread(new Runnable() {
            public void run() {
                if(pressed == false) {
                    currentTime = SystemClock.currentThreadTimeMillis();
                    while (pressed == false) {
                        if((SystemClock.currentThreadTimeMillis() - currentTime) > 3000){
                            renderer.setNotPressed(true);
                            break;
                        }
                    }
                }
            }
        }).start();
    }
}