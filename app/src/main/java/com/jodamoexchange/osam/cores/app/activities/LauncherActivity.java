package com.jodamoexchange.osam.cores.app.activities;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.jodamoexchange.osam.cores.app.OsamAppActivity;
import com.jodamoexchange.osam.cores.apps.AppConstants;
import com.jodamoexchange.osam.cores.apps.AppActivity;

public class LauncherActivity extends OsamAppActivity {

    @Override
    protected void initCreateApp(@Nullable Bundle savedInstanceState) {
        super.initCreateApp(savedInstanceState);
        this.getSupportActionBar().hide();
        if (!this.getMutablePreferencesController().hasProperty(AppConstants.TOKEN_LICENSE_KEY)) Log.e(AppConstants.CODE_ERROR, "Missing Token License Key");
        else {
            Class<? extends AppActivity> newActivity = LoginActivity.class;
            boolean isAuthed = !this.getMutablePreferencesController().compareData(AppConstants.TOKEN_LICENSE_KEY, "");
            boolean isLogged = !this.getMutablePreferencesController().compareData(AppConstants.TOKEN_LOGIN_KEY, "");
            if (isAuthed && isLogged) newActivity = OsamActivity.class;
            this.startNewActivity(newActivity);
        }
    }
}
