package com.jodamoexchange.osam.cores.apps;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jodamoexchange.osam.cores.apps.listeners.AppViewOnClickListener;

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

    protected void initViews (View inflatedView, @Nullable Bundle savedInstanceState) {
        this.listener.setFragmentInflatedView(inflatedView);
        for (int viewId : this.viewIds) {
            View v = inflatedView.findViewById(viewId);
            if (v instanceof Button) v.setOnClickListener(this.listener);
        }
    }

    protected TextView createTableCell () {
        return this.createTableCell(30);
    }

    protected TextView createTableCell (int paddingSize) {
        return this.createTableCell(paddingSize, paddingSize, paddingSize, paddingSize);
    }

    protected TextView createTableCell (int left, int top, int right, int bottom) {
        ShapeDrawable border = new ShapeDrawable(new RectShape());
        border.getPaint().setStyle(Paint.Style.STROKE);
        border.getPaint().setColor(Color.BLACK);

        TextView tableCell = new TextView (this.getAppActivity());
        tableCell.setBackground(border);
        tableCell.setPadding (left, top, right, bottom);

        return tableCell;
    }

    protected void setViewClickListener (View v) {
        v.setOnClickListener(this.listener);
    }

    @Nullable
    @Override
    public View onCreateView( @NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(this.layoutId, container, false);
        this.appActivity = (AppActivity) this.getActivity();
        this.listener.setApplicationContext(this.appActivity);
        this.listener.setPreferencesController(this.appActivity.getMutablePreferencesController());
        this.listener.setFragmentInflatedView(inflatedView);
        this.initViews(inflatedView, savedInstanceState);
        return inflatedView;
    }

    public AppActivity getAppActivity () {
        return this.appActivity;
    }
}
