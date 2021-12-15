package com.jodamoexchange.osam.cores.apps;

import android.content.Context;

import com.jodamoexchange.osam.cores.Controller;

public abstract class AppController implements Controller {

    private Context appContext;

    public AppController (Context context) {
        this.appContext = context;
    }

    public Context getApplicationContext () {
        return this.appContext;
    }
}
