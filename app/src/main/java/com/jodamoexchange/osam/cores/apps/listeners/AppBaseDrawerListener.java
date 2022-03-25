package com.jodamoexchange.osam.cores.apps.listeners;

import com.jodamoexchange.osam.cores.apps.AppActivity;
import com.jodamoexchange.osam.cores.apps.listeners.AppDrawerListener;

public abstract class AppBaseDrawerListener implements AppDrawerListener {

    private AppActivity appActivity;

    @Override
    public void setApplicationContext(AppActivity activity) {
        this.appActivity = activity;
    }

    protected AppActivity getApplicationContext () {
        return this.appActivity;
    }
}
