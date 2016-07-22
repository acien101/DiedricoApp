package io.github.acien101.diedricoanimation.DiedricoTo3D;

import android.app.Activity;
import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import java.util.List;

import io.github.acien101.diedricoanimation.R;

public class OpenGlActivity extends Activity {

    private GLSurfaceView mGLView;
    MyGLSurfaceViewCamera obj;
    List<PointVector> pointVectors;
    List<LineVector> lineVectors;
    List<PlaneVector> planeVectors;

    Intent intent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gllayout);
        intent = getIntent();

        pointVectors = intent.getParcelableArrayListExtra("points");
        lineVectors = intent.getParcelableArrayListExtra("lines");
        planeVectors = intent.getParcelableArrayListExtra("planes");

        mGLView = new MyGLSurfaceViewCamera(this, pointVectors, lineVectors, planeVectors);
        LinearLayout rootLayout = (LinearLayout)findViewById(R.id.rootLayout);
        rootLayout.addView(mGLView);

        // Create a GLSurfaceView instance and set it
        // as the ContentView for this Activity.


        SeekBar myBar = (SeekBar)findViewById(R.id.seekBar);
        myBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                obj = new MyGLSurfaceViewCamera(getApplicationContext(), pointVectors, lineVectors, planeVectors);
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