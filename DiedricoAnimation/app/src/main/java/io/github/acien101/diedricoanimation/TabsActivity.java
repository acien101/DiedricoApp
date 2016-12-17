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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.github.acien101.diedricoanimation.DiedricoTo3D.CameraActivity;
import ru.noties.scrollable.CanScrollVerticallyDelegate;
import ru.noties.scrollable.OnScrollChangedListener;
import ru.noties.scrollable.ScrollableLayout;

public class TabsActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener{

    private static final String ARG_LAST_SCROLL_Y = "arg.LastScrollY";

    private ScrollableLayout mScrollableLayout;

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

    /*
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(ARG_LAST_SCROLL_Y, mScrollableLayout.getScrollY());
        super.onSaveInstanceState(outState);
    }

    */
    private List<BaseFragment> getFragments() {

        final FragmentManager manager = getSupportFragmentManager();
        final List<BaseFragment> list = new ArrayList<>();

        ProjectionFragment projectionFragment
                = (ProjectionFragment) manager.findFragmentByTag(ProjectionFragment.TAG);
        if (projectionFragment == null) {
            projectionFragment = ProjectionFragment.newInstance();
        }

        DiedricoFragment diedricoFragment
                = (DiedricoFragment) manager.findFragmentByTag(DiedricoFragment.TAG);
        if(diedricoFragment == null){
            diedricoFragment = DiedricoFragment.newInstance(Color.rgb(245, 245, 245));
        }

        Collections.addAll(list, projectionFragment, diedricoFragment);
        return list;
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


    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
/*
        if (this.id == R.id.welcome) {
            changeRenderer(new MyGLRenderer());
            buttonsLayout.setVisibility(View.GONE);
            projectionLayout.setVisibility(View.GONE);
            textInfoProjection.setVisibility(View.VISIBLE);
        } else if (this.id == R.id.components) {
            changeRenderer(new MyGLRendererEdges(false, createDiedrico));
            buttonsLayout.setVisibility(View.INVISIBLE);
            projectionLayout.setVisibility(View.VISIBLE);
            textInfoProjection.setVisibility(View.GONE);

            cotaText.setVisibility(View.INVISIBLE);
            alejamientoText.setVisibility(View.INVISIBLE);
        } else if (this.id == R.id.edges) {
            changeRenderer(new MyGLRendererEdges(true, createDiedrico));
            buttonsLayout.setVisibility(View.INVISIBLE);
            projectionLayout.setVisibility(View.VISIBLE);
            textInfoProjection.setVisibility(View.GONE);

            cotaText.setVisibility(View.INVISIBLE);
            alejamientoText.setVisibility(View.INVISIBLE);
        } else if (this.id == R.id.pointProjection) {
            changeRenderer(new MyGLRendererPointProyection(createDiedrico));
            buttonsLayout.setVisibility(View.INVISIBLE);
            projectionLayout.setVisibility(View.VISIBLE);
            textInfoProjection.setVisibility(View.GONE);

            cotaText.setVisibility(View.VISIBLE);
            alejamientoText.setVisibility(View.VISIBLE);
        } else if (this.id == R.id.lineProjection) {
            changeRenderer(new MyGLRendererLineProyection(createDiedrico));
            buttonsLayout.setVisibility(View.INVISIBLE);
            projectionLayout.setVisibility(View.VISIBLE);
            textInfoProjection.setVisibility(View.GONE);

            cotaText.setVisibility(View.INVISIBLE);
            alejamientoText.setVisibility(View.INVISIBLE);
        } else if (this.id == R.id.typeOflines) {
            changeRenderer(new MyGLRendererTypeOfLines(createDiedrico, 0, infoText));
            buttonsLayout.setVisibility(View.VISIBLE);
            projectionLayout.setVisibility(View.VISIBLE);
            textInfoProjection.setVisibility(View.GONE);

            cotaText.setVisibility(View.INVISIBLE);
            alejamientoText.setVisibility(View.INVISIBLE);
            isTypeOfLines = true;
        } else if (this.id == R.id.typeOfPlanes) {
            changeRenderer(new MyGLRendererTypeOfPlanes(createDiedrico, 0, infoText));
            buttonsLayout.setVisibility(View.VISIBLE);
            projectionLayout.setVisibility(View.VISIBLE);
            textInfoProjection.setVisibility(View.GONE);

            cotaText.setVisibility(View.INVISIBLE);
            alejamientoText.setVisibility(View.INVISIBLE);
            isTypeOfLines = false;
        } else if (this.id == R.id.camara){
            Intent intent = new Intent(this, CameraActivity.class);
            this.startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        */
        return true;

    }

}
