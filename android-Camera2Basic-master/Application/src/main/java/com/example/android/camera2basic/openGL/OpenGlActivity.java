package com.example.android.camera2basic.openGL;

import android.app.Activity;
import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import com.example.android.camera2basic.R;

import java.util.ArrayList;
import java.util.List;

public class OpenGlActivity extends Activity {

    private GLSurfaceView mGLView;
    MyGLSurfaceView obj;
    final List<Double> pointCoords= new ArrayList<Double>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gllayout);
        Intent intent = getIntent();

        pointCoords.add(0, intent.getDoubleExtra("altura1", 1));
        pointCoords.add(1, intent.getDoubleExtra("altura2", 1));
        pointCoords.add(2, intent.getDoubleExtra("longitud", 1));

        mGLView = new MyGLSurfaceView(this, pointCoords);
        LinearLayout rootLayout = (LinearLayout)findViewById(R.id.rootLayout);
        rootLayout.addView(mGLView);

        // Create a GLSurfaceView instance and set it
        // as the ContentView for this Activity.




        SeekBar myBar = (SeekBar)findViewById(R.id.seekBar);
        myBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                Log.i("SeekBar", Integer.toString(progress));

                obj = new MyGLSurfaceView(getApplicationContext(), pointCoords);
                obj.setRenderer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }
}