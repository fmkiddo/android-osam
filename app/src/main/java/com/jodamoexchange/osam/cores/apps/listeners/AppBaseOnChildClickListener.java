package com.jodamoexchange.osam.cores.apps.listeners;

import com.jodamoexchange.osam.cores.Core;
import com.jodamoexchange.osam.cores.apps.AppActivity;
import com.jodamoexchange.osam.cores.apps.AppExpandableListAdapter;
import com.jodamoexchange.osam.cores.apps.listeners.AppDrawerOnChildClickListener;

import java.util.List;
import java.util.Map;

public abstract class AppBaseOnChildClickListener<G, C> implements Core, AppDrawerOnChildClickListener<G, C> {

    private AppActivity context;
    private AppExpandableListAdapter<G, C> adapter;

    @Override
    public void setApplicationContext(AppActivity appActivity) {
        this.context = appActivity;
    }

    @Override
    public void setListViewAdapter(AppExpandableListAdapter<G, C> adapter) {
        this.adapter = adapter;
    }

    protected AppActivity getApplicationContext () {
        return this.context;
    }

    protected AppExpandableListAdapter<G, C> getAdapter () {
        return this.adapter;
    }
}
