package io.github.acien101.diedricoto3d.openGL;

import android.app.Activity;
import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.SeekBar;


import java.util.List;

import io.github.acien101.diedricoto3d.LineVector;
import io.github.acien101.diedricoto3d.PlaneVector;
import io.github.acien101.diedricoto3d.PointVector;
import io.github.acien101.diedricoto3d.R;

public class OpenGlActivity extends Activity {

    private GLSurfaceView mGLView;
    MyGLSurfaceView obj;
    List<PointVector> puntos;
    List<LineVector> lineVectors;
    List<PlaneVector> planeVectors;

    Intent intent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gllayout);
        intent = getIntent();

        puntos = intent.getParcelableArrayListExtra("vector");
        lineVectors = intent.getParcelableArrayListExtra("lines");
        planeVectors = intent.getParcelableArrayListExtra("planos");

        Log.i("received", Integer.toString(puntos.size()));
        Log.i("LINE", Integer.toString(lineVectors.size()));

        mGLView = new MyGLSurfaceView(this, puntos, lineVectors, planeVectors);
        LinearLayout rootLayout = (LinearLayout)findViewById(R.id.rootLayout);
        rootLayout.addView(mGLView);

        // Create a GLSurfaceView instance and set it
        // as the ContentView for this Activity.




        SeekBar myBar = (SeekBar)findViewById(R.id.seekBar);
        myBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                Log.i("SeekBar", Integer.toString(progress));

                obj = new MyGLSurfaceView(getApplicationContext(), puntos, lineVectors, planeVectors);
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