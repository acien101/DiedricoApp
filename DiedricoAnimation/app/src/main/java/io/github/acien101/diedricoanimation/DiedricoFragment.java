package io.github.acien101.diedricoanimation;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by amil101 on 15/08/16.
 */
public class DiedricoFragment extends BaseFragment{

    static final String TAG = "tag.DiedricoFragment";

    public static DiedricoFragment newInstance(int color) {
        final Bundle bundle = new Bundle();
        bundle.putInt(ARG_COLOR, color);

        final DiedricoFragment fragment = new DiedricoFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    private ListView mListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle sis) {

        final View view = inflater.inflate(R.layout.fragment_diedrico, parent, false);

        TextView text = (TextView) view.findViewById(R.id.test);
        text.setText("asdfaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

        Button button = new Button(parent.getContext());
        button.setText("asdf");

        LinearLayout layout = (LinearLayout) view.findViewById(R.id.layoutTest);
        layout.addView(button);

        return view;
    }

    @Override
    public CharSequence getTitle(Resources r) {
        return r.getString(R.string.app_name);
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
}
