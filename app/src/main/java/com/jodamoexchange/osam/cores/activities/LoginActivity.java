package com.jodamoexchange.osam.cores.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.jodamoexchange.osam.cores.app.AppConstants;
import com.jodamoexchange.osam.cores.apps.AppActivity;
import com.jodamoexchange.osam.cores.controllers.MutablePreferencesController;

public class LoginActivity extends AppActivity {

    private MutablePreferencesController prefsController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide();
        this.prefsController = new MutablePreferencesController(this, AppConstants.TOKEN_LICENSE_KEY);
        this.setPreferenceController(this.prefsController);
    }
}
