package com.jodamoexchange.osam.cores.apps.listeners;

import android.widget.ExpandableListView;

import com.jodamoexchange.osam.cores.apps.AppActivity;
import com.jodamoexchange.osam.cores.apps.AppExpandableListAdapter;

import java.util.List;
import java.util.Map;

public interface AppDrawerOnChildClickListener<G, C> extends ExpandableListView.OnChildClickListener {

    void setApplicationContext (AppActivity appActivity);

    void setListViewAdapter (AppExpandableListAdapter<G, C> adapter);
}
