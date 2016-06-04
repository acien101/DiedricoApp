package io.github.acien101.diedricoanimation;

import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private GLSurfaceView mGLView;
    private MyGLSurfaceView myGLSurfaceView;

    float initX;      //Is the value of the X coordenate when we press the screen
    float initY;      //The Y

    float moveX;    //Is the value of the X movement
    float moveY;    //Is the value of the Y movement

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create a GLSurfaceView instance and set it
        // as the ContentView for this Activity.
        mGLView = new MyGLSurfaceView(this);
        myGLSurfaceView = new MyGLSurfaceView(this);
        mGLView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initX = event.getX();
                        initY = event.getY();

                        Log.i("toco", "X " + event.getX() + " Y " + event.getY());

                    case MotionEvent.ACTION_MOVE:
                        moveX = (event.getX() - initX);
                        moveY = (event.getY() - initY);

                        myGLSurfaceView.setCamera(moveX, moveY, 0);
                        Log.i("muevo", "X " + moveX + " Y " + moveY);

                        initX = event.getX();
                        initY = event.getY();
            }

            return true;
        }
    });
        setContentView(mGLView);

        mGLView.requestRender();
    }
}
