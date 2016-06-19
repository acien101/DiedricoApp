package io.github.acien101.diedricoanimation;

import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.LinearLayout;

/**
 * Created by amil101 on 19/06/16.
 */
public class ProjectionAnimation extends Animation{
    private final float mStartWeight;
    private final float mDeltaWeight;

    LinearLayout mContent;

    public ProjectionAnimation(LinearLayout mContent, float startWeight, float endWeight){
        mStartWeight = startWeight;
        mDeltaWeight = endWeight - startWeight;

        this.mContent = mContent;
    }

    @Override
    public boolean willChangeBounds() {
        return true;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mContent.getLayoutParams();
        lp.weight = (mStartWeight + (mDeltaWeight * interpolatedTime));
        mContent.setLayoutParams(lp);
    }
}
