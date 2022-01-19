package com.jodamoexchange.osam.cores.apps;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.view.View;

import androidx.appcompat.app.AlertDialog;

import com.jodamoexchange.osam.cores.apps.controllers.MutablePreferencesController;

public abstract class AppViewOnClickListener implements View.OnClickListener {

    private AppActivity context;
    private int responseCode = 0;
    protected AppMutablePreferenceController prefControl;
    protected View inflatedView;

    public void setApplicationContext (AppActivity context) {
        this.context = context;
    }

    public void setPreferencesController (AppMutablePreferenceController prefControl) {
        this.prefControl = prefControl;
    }

    public void setFragmentInflatedView (View inflatedView) {
        this.inflatedView = inflatedView;
    }

    public int getResponseCode () {
        return this.responseCode;
    }

    public Resources getResources() {
        return this.getApplicationContext().getResources();
    }

    protected AppActivity getApplicationContext () {
        return this.context;
    }

    protected AlertDialog.Builder createDialogBuilder () {
        return new AlertDialog.Builder(this.getApplicationContext());
    }

    protected void doRebirthApplication (Class<? extends AppActivity> activityClass) {
        this.getApplicationContext().doRebirth(activityClass);
    }

    protected void doStartNewActivity (Class<? extends AppActivity> activityClass) {
        this.getApplicationContext().startNewActivity(activityClass);
    }

    protected void setResponseCode (int responseCode) {
        this.responseCode = responseCode;
    }
}
