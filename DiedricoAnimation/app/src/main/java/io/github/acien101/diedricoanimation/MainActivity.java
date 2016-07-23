package io.github.acien101.diedricoanimation;

import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.os.SystemClock;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.support.v7.widget.Toolbar;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import io.github.acien101.diedricoanimation.DiedricoTo3D.Camera2BasicFragment;
import io.github.acien101.diedricoanimation.DiedricoTo3D.CameraActivity;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    RelativeLayout content_main;            //its the content layout, where it is the opengl renderer

    private GLSurfaceView mGLView;          //SurfaceView of OpenGL
    private MyGLSurfaceView myGLSurfaceView;            //Its the object of the openGL where we pass the coords for the camera

    float initX;      //Is the value of the X coordenate when we press the screen
    float initY;      //The Y

    float moveX;    //Is the value of the X movement
    float moveY;    //Is the value of the Y movement

    boolean pressed;            //if the OpenGL is pressed
    long currentTime;           //The time of the thread, for rotate the camera if the user don't press the screen

    MyGLRendererCamera renderer;            //The renderer obj

    boolean expanded = false;                   //if the projection is extended

    LinearLayout projection;
    LinearLayout layoutForSurfaceView;

    ImageView diedrico;                 //The imageView of the projection

    CreateDiedrico createDiedrico;

    LinearLayout buttonsLayout;
    ImageButton leftButton;
    ImageButton rightButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        content_main = (RelativeLayout) findViewById(R.id.content_main);

        diedrico = (ImageView) findViewById(R.id.projection);

        createDiedrico = new CreateDiedrico(diedrico);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        buttonsLayout = (LinearLayout) findViewById(R.id.buttonsLayout);
        leftButton = (ImageButton) findViewById(R.id.leftButton);
        rightButton = (ImageButton) findViewById(R.id.rightButton);
        buttonsLayout.setVisibility(View.INVISIBLE);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Create a GLSurfaceView instance and set it
        // as the ContentView for this Activity.

        renderer = new MyGLRenderer(createDiedrico);              //The main screen is the MyGLRenderer() obj, where is a home renderer
        mGLView = new MyGLSurfaceView(this, renderer);
        myGLSurfaceView = new MyGLSurfaceView(this, renderer);

        threadTime();               //start the thread, for rotate the camera if the user don't press the screen
        pressed = false;

        mGLView.setOnTouchListener(listenerForCamera());

        layoutForSurfaceView = (LinearLayout) findViewById(R.id.layoutForSurfaceView);
        layoutForSurfaceView.addView(mGLView);
        mGLView.requestRender();

        projection = (LinearLayout) findViewById(R.id.layoutForProjections);
        projection.setOnClickListener(projectionClick());


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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.welcome) {
            changeRenderer(new MyGLRenderer(createDiedrico));
            buttonsLayout.setVisibility(View.INVISIBLE);
        } else if (id == R.id.edges) {
            changeRenderer(new MyGLRendererPointProyection(createDiedrico));
            buttonsLayout.setVisibility(View.INVISIBLE);
        } else if (id == R.id.pointProjection) {
            changeRenderer(new MyGLRendererPointProyection(createDiedrico));
            buttonsLayout.setVisibility(View.INVISIBLE);
        } else if (id == R.id.lineProjection) {
            changeRenderer(new MyGLRendererLineProyection(createDiedrico));
            buttonsLayout.setVisibility(View.INVISIBLE);
        } else if (id == R.id.typeOflines) {
            changeRenderer(new MyGLRendererTypeOfLines(createDiedrico));
            buttonsLayout.setVisibility(View.VISIBLE);
        } else if (id == R.id.typeOfPlanes) {
            changeRenderer(new MyGLRendererTypeOfPlanes(createDiedrico));
            buttonsLayout.setVisibility(View.VISIBLE);
        } else if (id == R.id.camara){
            Intent intent = new Intent(this, CameraActivity.class);
            this.startActivity(intent);

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void changeRenderer(MyGLRendererCamera renderer){
        mGLView = new MyGLSurfaceView(this, renderer);
        myGLSurfaceView = new MyGLSurfaceView(this, renderer);

        mGLView.setOnTouchListener(listenerForCamera());

        //Put the diedrico projection to the layout and the renderer
        layoutForSurfaceView = (LinearLayout) findViewById(R.id.layoutForSurfaceView);
        layoutForSurfaceView.removeAllViews();
        layoutForSurfaceView.addView(mGLView);

        mGLView.requestRender();
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

    public void openCamera(){

    }

}
