package io.github.acien101.diedricoanimation;

import android.content.res.Resources;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by amil101 on 15/08/16.
 */
public class DiedricoFragment extends BaseFragment{

    static final String TAG = "tag.DiedricoFragment";

    ImageView diedrico;            //ImageView
    CreateDiedrico createDiedrico;      //For changing the pictures of the imageView

    public static DiedricoFragment newInstance() {
        final Bundle bundle = new Bundle();

        final DiedricoFragment fragment = new DiedricoFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    private ListView mListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle sis) {

        final View view = inflater.inflate(R.layout.fragment_diedrico, parent, false);

        diedrico = (ImageView) view.findViewById(R.id.projection);
        createDiedrico = new CreateDiedrico(diedrico);

        return view;
    }

    @Override
    public CharSequence getTitle(Resources r) {
        return r.getString(R.string.representation);
    }

    @Override
    public String getSelfTag() {
        return TAG;
    }

    @Override
    public boolean canScrollVertically(int direction) {
        return mListView != null && mListView.canScrollVertically(direction);
    }

    @Override
    public void onFlingOver(int y, long duration) {
        if (mListView != null) {
            mListView.smoothScrollBy(y, (int) duration);
        }
    }

    public void setDiedrico(Diedrico diedrico){
        if(diedrico.getPoints() != null){
            this.createDiedrico.addDiedricoPoints(diedrico.getPoints());
        }

        if(diedrico.getLines() != null){
            this.createDiedrico.addDiedricoLines(diedrico.getLines());
        }

        if(diedrico.getPlanes() != null){
            this.createDiedrico.addPlanes(diedrico.getPlanes());
        }
    }
}
