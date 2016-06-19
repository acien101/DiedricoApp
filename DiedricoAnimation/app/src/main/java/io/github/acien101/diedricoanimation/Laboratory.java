package io.github.acien101.diedricoanimation;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.LinearLayout;

/**
 * Created by amil101 on 6/06/16.
 */
public class Laboratory extends Activity{
    LinearLayout projection;

    boolean expanded = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        projection = (LinearLayout) findViewById(R.id.layoutForProjections);
        projection.setOnClickListener(projectionClick());
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
            Log.i("i", "false");
            a = new ProjectionAnimation(projection, 9.0f, 0.1f);
            expanded = true;
        }
        else{
            Log.i("i", "true");
            a = new ProjectionAnimation(projection, 0.1f, 9.0f);
            expanded = false;
        }
        a.setDuration(200);
        projection.startAnimation(a);
    }
}
