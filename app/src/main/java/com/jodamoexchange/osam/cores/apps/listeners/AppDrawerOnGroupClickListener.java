package com.jodamoexchange.osam.cores.apps.listeners;

import android.widget.ExpandableListView;

import com.jodamoexchange.osam.cores.apps.AppActivity;
import com.jodamoexchange.osam.cores.apps.AppExpandableListAdapter;

public interface AppDrawerOnGroupClickListener<G, C> extends ExpandableListView.OnGroupClickListener {

    void setApplicationContext (AppActivity appActivity);

    void setListViewAdapter (AppExpandableListAdapter<G, C> adapter);
}
