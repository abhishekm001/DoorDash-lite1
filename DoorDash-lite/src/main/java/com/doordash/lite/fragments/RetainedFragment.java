package com.doordash.lite.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

// View less fragment to tackle transcationTooLarge exception
public class RetainedFragment extends Fragment {

    private static final String TAG = "RetainedFragment";
    private Bundle mInstanceBundle = null;
    //private Map<String, Bundle> mInstanceBundle = null;

    public RetainedFragment() {
        super();
        // this specifies fragment will not be destroyed on configuration change eg.on orientation change
        setRetainInstance(true);
    }

    public static final RetainedFragment getInstance(FragmentManager fragmentManager) {
        RetainedFragment out = (RetainedFragment) fragmentManager.findFragmentByTag(TAG);

        if (out == null) {
            out = new RetainedFragment();
            fragmentManager.beginTransaction().add(out, TAG).commitAllowingStateLoss();
        }
        return out;
    }

    /**
     * Stores bundle state
     *
     * @param instanceState
     * @return
     */
    public RetainedFragment pushData(Bundle instanceState, boolean clearPreviousData) {
        if (this.mInstanceBundle == null) {
            this.mInstanceBundle = instanceState;
        } else {
            if (clearPreviousData) {
                this.mInstanceBundle.clear();
            }
            this.mInstanceBundle.putAll(instanceState);
        }
        return this;
    }

    /**
     * Retrieves savedBundleState
     *
     * @return
     */
    public Bundle popData() {
        Bundle out = this.mInstanceBundle;
        this.mInstanceBundle = null;
        return out;
    }

}
