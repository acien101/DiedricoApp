package io.github.acien101.diedricoto3d.openGL;

import android.app.Activity;
import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.SeekBar;


import java.util.ArrayList;
import java.util.List;

import io.github.acien101.diedricoto3d.LineaVector;
import io.github.acien101.diedricoto3d.PlanoVector;
import io.github.acien101.diedricoto3d.PuntoVector;
import io.github.acien101.diedricoto3d.R;

public class OpenGlActivity extends Activity {

    private GLSurfaceView mGLView;
    MyGLSurfaceView obj;
    List<PuntoVector> puntos;
    List<LineaVector> lineaVectors;
    List<PlanoVector> planoVectors;

    Intent intent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gllayout);
        intent = getIntent();

        puntos = intent.getParcelableArrayListExtra("vector");
        lineaVectors = intent.getParcelableArrayListExtra("lines");
        planoVectors = intent.getParcelableArrayListExtra("planos");

        Log.i("received", Integer.toString(puntos.size()));
        Log.i("LINE", Integer.toString(lineaVectors.size()));

        mGLView = new MyGLSurfaceView(this, puntos, lineaVectors, planoVectors);
        LinearLayout rootLayout = (LinearLayout)findViewById(R.id.rootLayout);
        rootLayout.addView(mGLView);

        // Create a GLSurfaceView instance and set it
        // as the ContentView for this Activity.




        SeekBar myBar = (SeekBar)findViewById(R.id.seekBar);
        myBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                Log.i("SeekBar", Integer.toString(progress));

                obj = new MyGLSurfaceView(getApplicationContext(), puntos, lineaVectors, planoVectors);
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