package com.jodamoexchange.osam.cores.apps.listeners;

import com.jodamoexchange.osam.cores.apps.AppActivity;
import com.jodamoexchange.osam.cores.apps.AppMutablePreferenceController;
import com.jodamoexchange.osam.cores.apps.listeners.AppDIOnClickListener;

public abstract class AppAbstractDIOnClickListener implements AppDIOnClickListener {

    private AppActivity activity;

    @Override
    public boolean setApplicationContext(AppActivity activity) {
        this.activity = activity;
        return this.activity != null;
    }

    protected AppActivity getApplicationContext () {
        return this.activity;
    }

    protected AppMutablePreferenceController getPreferenceController () {
        return this.activity.getMutablePreferencesController();
    }

    protected void startNewActivity (Class<? extends AppActivity> activityClass) {
        this.activity.startNewActivity(activityClass);
    }
}
