package io.github.acien101.diedricoanimation;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.github.acien101.diedricoanimation.DiedricoTo3D.CameraActivity;
import io.github.acien101.diedricoanimation.vector.LineVector;
import io.github.acien101.diedricoanimation.vector.PointVector;
import ru.noties.scrollable.CanScrollVerticallyDelegate;
import ru.noties.scrollable.OnFlingOverListener;
import ru.noties.scrollable.OnScrollChangedListener;
import ru.noties.scrollable.ScrollableLayout;

public class TabsActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener{

    private static final String ARG_LAST_SCROLL_Y = "arg.LastScrollY";

    private ScrollableLayout mScrollableLayout;

    ProjectionFragment projectionFragment;
    DiedricoFragment diedricoFragment;
    ExplanationFragment explanationFragment;

    CreateDiedrico createDiedrico;

    Diedrico diedrico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_tabs);
        setSupportActionBar(myToolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_tabs_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, myToolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_tabs_view);
        navigationView.setNavigationItemSelectedListener(this);

        final TabsLayout tabs = findView(R.id.tabs);

        mScrollableLayout = findView(R.id.scrollable_layout);
        mScrollableLayout.setDraggableView(tabs);
        final ViewPager viewPager = findView(R.id.view_pager);
        final ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), getResources(), getFragments());
        viewPager.setAdapter(adapter);

        tabs.setViewPager(viewPager);

// Note this bit, it's very important
        mScrollableLayout.setCanScrollVerticallyDelegate(new CanScrollVerticallyDelegate() {
            @Override
            public boolean canScrollVertically(int direction) {
                return adapter.canScrollVertically(viewPager.getCurrentItem(), direction);
            }
        });
        mScrollableLayout.setOnScrollChangedListener(new OnScrollChangedListener() {
            @Override
            public void onScrollChanged(int y, int oldY, int maxY) {

                // Sticky behavior
                final float tabsTranslationY;
                if (y < maxY) {
                    tabsTranslationY = .0F;
                } else {
                    tabsTranslationY = y - maxY;
                }

                tabs.setTranslationY(tabsTranslationY);
            }
        });
    }

    private List<BaseFragment> getFragments() {

        final FragmentManager manager = getSupportFragmentManager();
        final List<BaseFragment> list = new ArrayList<>();

        projectionFragment = (ProjectionFragment) manager.findFragmentByTag(ProjectionFragment.TAG);
        if (projectionFragment == null) {
            projectionFragment = ProjectionFragment.newInstance();
        }

        diedricoFragment = (DiedricoFragment) manager.findFragmentByTag(DiedricoFragment.TAG);
        if(diedricoFragment == null){
            diedricoFragment = DiedricoFragment.newInstance();
        }


        explanationFragment= (ExplanationFragment) manager.findFragmentByTag(ExplanationFragment.TAG);
        if(explanationFragment == null){
            explanationFragment = explanationFragment.newInstance();
        }

        Collections.addAll(list, projectionFragment, diedricoFragment, explanationFragment);
        return list;
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_tabs_layout);
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


    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.welcome) {
            diedrico = new Diedrico(null, null, null);

            projectionFragment.changeRenderer(new MyGLRenderer(diedrico));
            projectionFragment.newInstance();

            diedricoFragment.setDiedrico(diedrico);

            explanationFragment.setExplanation(R.string.firtstext);
            explanationFragment.newInstance();
        } else if (id == R.id.components) {
            diedrico = new Diedrico(null, null, null);

            projectionFragment.changeRenderer(new MyGLRendererEdges(false, null));
            projectionFragment.newInstance();


            diedricoFragment.setDiedrico(new Diedrico(null, null, null));

            explanationFragment.setExplanation(R.string.edges);
            explanationFragment.newInstance();
        } else if (id == R.id.edges) {
            diedrico = new Diedrico(null, null, null);

            projectionFragment.changeRenderer(new MyGLRenderer(diedrico));
            projectionFragment.newInstance();

            diedricoFragment.setDiedrico(diedrico);

            explanationFragment.setExplanation(R.string.firtstext);
            explanationFragment.newInstance();
        } else if (id == R.id.pointProjection) {

            List<PointVector> pointVectors = new ArrayList<>();
            pointVectors.add(new PointVector(0.75f, 0.25f, 0.0f));
            pointVectors.add(new PointVector(0.4f, 0.6f, 0.0f));

            diedrico = new Diedrico(pointVectors, null, null);

            projectionFragment.changeRenderer(new MyGLRenderer(diedrico));
            projectionFragment.newInstance();

            diedricoFragment.setDiedrico(diedrico);

            explanationFragment.setExplanation(R.string.firtstext);
            explanationFragment.newInstance();
        } else if (id == R.id.lineProjection) {

            List<LineVector> lineVectors = new ArrayList<>();
            lineVectors.add(new LineVector(0.0f, 0.8f, 0.4f, 0.9f, 0.0f, -0.4f));
            diedrico = new Diedrico(null, null, null);

            projectionFragment.changeRenderer(new MyGLRenderer(diedrico));
            projectionFragment.newInstance();

            diedricoFragment.setDiedrico(diedrico);

            explanationFragment.setExplanation(R.string.firtstext);
            explanationFragment.newInstance();
        } /*else if (id == R.id.typeOflines) {
            diedrico = new Diedrico(null, null, null);

            projectionFragment.changeRenderer(new MyGLRenderer(diedrico));
            projectionFragment.newInstance();

            diedricoFragment.setDiedrico(diedrico);

            explanationFragment.setExplanation(R.string.firtstext);
            explanationFragment.newInstance();
        } else if (id == R.id.typeOfPlanes) {
            diedrico = new Diedrico(null, null, null);

            projectionFragment.changeRenderer(new MyGLRenderer(diedrico));
            projectionFragment.newInstance();

            diedricoFragment.setDiedrico(diedrico);

            explanationFragment.setExplanation(R.string.firtstext);
            explanationFragment.newInstance();
        }*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_tabs_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;

    }


}
