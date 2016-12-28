package io.github.acien101.diedricoanimation;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by amil101 on 17/12/16.
 */
public class ExplanationFragment extends BaseFragment{

    static final String TAG = "tag.ExplanationFragment";
    private int explanation = R.string.firtstext;
    TextView textView;

    public static ExplanationFragment newInstance() {
        final Bundle bundle = new Bundle();

        final ExplanationFragment fragment = new ExplanationFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    private ListView mListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle sis) {

        final View view = inflater.inflate(R.layout.fragment_explanation, parent, false);

        textView = (TextView) view.findViewById(R.id.explanationText);
        textView.setText(explanation);

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

    public void setExplanation(int exp){
        this.explanation = exp;
    }

}
