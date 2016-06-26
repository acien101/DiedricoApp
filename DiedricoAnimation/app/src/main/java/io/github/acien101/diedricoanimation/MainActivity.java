package io.github.acien101.diedricoanimation;

import android.opengl.GLSurfaceView;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.Toolbar;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    RelativeLayout content_main;

    private GLSurfaceView mGLView;
    private MyGLSurfaceView myGLSurfaceView;

    float initX;      //Is the value of the X coordenate when we press the screen
    float initY;      //The Y

    float moveX;    //Is the value of the X movement
    float moveY;    //Is the value of the Y movement

    boolean pressed;
    long currentTime;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        content_main = (RelativeLayout) findViewById(R.id.content_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Create a GLSurfaceView instance and set it
        // as the ContentView for this Activity.
        mGLView = new MyGLSurfaceView(this);
        myGLSurfaceView = new MyGLSurfaceView(this);
        threadTime();
        pressed = false;
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

        content_main.addView(mGLView);
        mGLView.requestRender();


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

    public void createDiedrico(){
        RelativeLayout relativeLayout = new RelativeLayout(getApplicationContext());
        relativeLayout.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
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

        if (id == R.id.nav_camara) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
