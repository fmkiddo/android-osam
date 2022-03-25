package com.jodamoexchange.osam.cores.apps.listeners;

import android.content.DialogInterface;

import com.jodamoexchange.osam.cores.apps.AppActivity;
import com.jodamoexchange.osam.cores.apps.listeners.AppDIOnClickListener;

public abstract class AppDialogOnClickListener implements AppDIOnClickListener {

    private AppActivity appActivity = null;

    @Override
    public boolean setApplicationContext(AppActivity activity) {
        this.appActivity = activity;
        return (this.appActivity != null);
    }

    protected AppActivity getAppActivity () {
        return this.appActivity;
    }

}
