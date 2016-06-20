package io.github.acien101.diedricoanimation;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import io.github.acien101.diedricoanimation.vector.PointVector;

/**
 * Created by amil101 on 6/06/16.
 */
public class Laboratory extends Activity{
    private GLSurfaceView mGLView;
    private MyGLSurfaceView myGLSurfaceView;

    LinearLayout projection;
    LinearLayout layoutForSurfaceView;

    ImageView diedrico;

    float initX;      //Is the value of the X coordenate when we press the screen
    float initY;      //The Y

    float moveX;    //Is the value of the X movement
    float moveY;    //Is the value of the Y movement

    boolean pressed;
    long currentTime;

    boolean expanded = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGLView = new MyGLSurfaceView(this);
        myGLSurfaceView = new MyGLSurfaceView(this);

        mGLView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        myGLSurfaceView.setNotPressed(false);
                        initX = event.getX();
                        initY = event.getY();
                        Log.i("toco", "X " + event.getX() + " Y " + event.getY());
                        pressed = true;
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        moveX = (event.getX() - initX);
                        moveY = -(event.getY() - initY);
                        myGLSurfaceView.setCamera(moveX, moveY, 0);
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
        });

        layoutForSurfaceView = (LinearLayout) findViewById(R.id.layoutForSurfaceView);
        layoutForSurfaceView.addView(mGLView);
        mGLView.requestRender();

        projection = (LinearLayout) findViewById(R.id.layoutForProjections);
        projection.setOnClickListener(projectionClick());

        diedrico = (ImageView) findViewById(R.id.projection);
        new CreateDiedrico(new PointVector(100.0f, 50.0f, 50.0f), diedrico);
    }

    View.OnClickListener projectionClick(){
             return new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                    changeLayoutParams();
                 }
             };
    }

    void changeLayoutParams() {
        Animation a;
        if(expanded == false){
            a = new ProjectionAnimation(projection, 9.0f, 0.1f);
            expanded = true;
        }
        else{
            a = new ProjectionAnimation(projection, 0.1f, 9.0f);
            expanded = false;
        }
        a.setDuration(200);
        projection.startAnimation(a);
    }

    public void threadTime(){
        new Thread(new Runnable() {
            public void run() {
                if(pressed == false) {
                    currentTime = SystemClock.currentThreadTimeMillis();
                    while (pressed == false) {
                        if((SystemClock.currentThreadTimeMillis() - currentTime) > 3000){
                            myGLSurfaceView.setNotPressed(true);
                            break;
                        }
                    }
                }
            }
        }).start();
    }

}
