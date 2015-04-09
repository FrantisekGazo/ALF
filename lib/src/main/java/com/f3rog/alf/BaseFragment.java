package com.f3rog.alf;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Base Fragment which performs injection using the activity-scoped object graph
 * and also inject views with ButterKnife.
 *
 * @author f3rog
 */
public abstract class BaseFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Assume that it lives within a DaggerActivity host
        ((BaseActivity) getActivity()).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(getLayout(), container, false);
        ButterKnife.inject(this, rootView);
        return rootView;
    }

    protected <T> T getView(int id) {
        return (T) getView().findViewById(id);
    }

    protected abstract int getLayout();

}
