package com.jodamoexchange.osam.cores.apps;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class AppFragment extends Fragment {

    protected int[] viewIds;
    protected int layoutId;
    private AppViewOnClickListener listener;
    private AppActivity appActivity;

    protected AppFragment (Class<? extends AppViewOnClickListener> listenerClass) {
        try {
            this.listener = listenerClass.newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.appActivity = (AppActivity) this.getActivity();
        View inflatedView = inflater.inflate(this.layoutId, container, false);
        this.listener.setApplicationContext(this.appActivity);
        this.listener.setPreferencesController(this.appActivity.getPreferenceController());
        this.listener.setFragmentInflatedView(inflatedView);
        this.initViews(inflatedView);
        return inflatedView;
    }

    protected void initViews (View inflatedView) {
        this.listener.setFragmentInflatedView(inflatedView);
        for (int viewId : this.viewIds) {
            View v = inflatedView.findViewById(viewId);
            if (v instanceof Button) v.setOnClickListener(this.listener);
        }
    }
}
