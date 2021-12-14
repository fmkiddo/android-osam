package com.jodamoexchange.osam.cores.controllers;

import android.content.Context;

import com.jodamoexchange.osam.cores.apps.AppController;
import com.jodamoexchange.osam.cores.apps.AppPreferencesModel;
import com.jodamoexchange.osam.cores.models.SharedPreferencesModel;

public class PreferencesController extends AppController {

    private AppPreferencesModel prefsModel;

    public PreferencesController(Context context, String name) {
        super(context);
        this.prefsModel = new SharedPreferencesModel(context.getSharedPreferences(name, Context.MODE_PRIVATE));
    }

    public PreferencesController(Context context, String name, int mode) {
        super(context);
        this.prefsModel = new SharedPreferencesModel(context.getSharedPreferences(name, mode));
    }

    public boolean hasProperty (String name) {
        return this.prefsModel.hasProperty(name);
    }

    public <V> boolean compareData (String name, V value) {
        return this.prefsModel.compareValue(name, value);
    }

    protected AppPreferencesModel getPreferencesModel () {
        return this.prefsModel;
    }
}
