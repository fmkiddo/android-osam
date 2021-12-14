package com.jodamoexchange.osam.cores.apps;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jodamoexchange.osam.cores.controllers.MutablePreferencesController;
import com.jodamoexchange.osam.cores.controllers.PreferencesController;

public abstract class AppActivity extends AppCompatActivity {

    private MutablePreferencesController mainPrefsController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void setPreferenceController (MutablePreferencesController prefsController) {
        this.mainPrefsController = prefsController;
    }

    protected PreferencesController getPreferenceController () {
        return this.mainPrefsController;
    }
}
