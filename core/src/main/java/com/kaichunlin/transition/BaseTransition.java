package com.kaichunlin.transition;

import android.view.View;
import android.view.animation.Interpolator;

/**
 * Provides common implementations for all transitions.
 * <p>
 * Created by Kai-Chun Lin on 2015/4/18.
 */
abstract class BaseTransition<T extends BaseTransition, S extends BaseTransition.Setup> implements ITransition<S> {
    private String mId;
    boolean mReverse;
    S mSetup;
    Interpolator mInterpolator;
    View mTarget;
    boolean mUpdateStateAfterUpdateProgress;

    public BaseTransition(String id) {
        this.mId = id;
    }

    @Override
    public T setId(String id) {
        mId = id;
        return self();
    }

    @Override
    public String getId() {
        return mId == null ? (mId = toString()) : mId;
    }

    @Override
    public T setSetup(S setup) {
        mSetup = setup;
        return self();
    }

    @Override
    public T reverse() {
        String id = getId();
        String REVERSE = "_REVERSE";
        if (id.endsWith(REVERSE)) {
            setId(id.substring(0, id.length() - REVERSE.length()));
        } else {
            setId(id + REVERSE);
        }

        mReverse = !mReverse;
        return self();
    }

    @Override
    public void setTarget(View target) {
        mTarget = target;
        invalidate();
    }

    @Override
    public View getTarget() {
        return mTarget;
    }

    public T setUpdateStateAfterUpdateProgress(boolean updateStateAfterUpdateProgress) {
        mUpdateStateAfterUpdateProgress = updateStateAfterUpdateProgress;
        return self();
    }

    /**
     * Invalidates the current transition, which may mean the currently running transition is stopped
     */
    protected abstract void invalidate();

    protected abstract T self();

    @Override
    public T setInterpolator(Interpolator interpolator) {
        mInterpolator = interpolator;
        return self();
    }

    @Override
    public BaseTransition clone() {
        BaseTransition newClone = null;
        try {
            newClone = (BaseTransition) super.clone();
            newClone.setId(newClone.getId() + "_CLONE");
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return newClone;
    }

    /**
     * Represents an object that will create ITransitionController Objects to be added to a TransitionManager
     */
    public interface Setup {
    }
}
