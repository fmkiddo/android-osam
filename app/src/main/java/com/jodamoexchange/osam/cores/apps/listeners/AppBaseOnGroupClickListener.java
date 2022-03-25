package com.jodamoexchange.osam.cores.apps.listeners;

import com.jodamoexchange.osam.cores.Core;
import com.jodamoexchange.osam.cores.apps.AppActivity;
import com.jodamoexchange.osam.cores.apps.AppExpandableListAdapter;
import com.jodamoexchange.osam.cores.apps.listeners.AppDrawerOnGroupClickListener;

public abstract class AppBaseOnGroupClickListener<G, C> implements Core, AppDrawerOnGroupClickListener<G, C> {

    private AppActivity context;
    private AppExpandableListAdapter<G, C> adapter;

    @Override
    public void setListViewAdapter(AppExpandableListAdapter<G, C> adapter) {
        this.adapter = adapter;
    }

    @Override
    public void setApplicationContext(AppActivity appActivity) {
        this.context = appActivity;
    }

    protected AppActivity getApplicationContext () {
        return this.context;
    }

    protected AppExpandableListAdapter<G, C> getAdapter () {
        return this.adapter;
    }
}
