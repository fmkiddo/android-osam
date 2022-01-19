package com.jodamoexchange.osam.cores.apps.controllers;

import android.content.Context;

import com.jodamoexchange.osam.cores.apps.AppController;
import com.jodamoexchange.osam.cores.apps.AppPreferenceController;
import com.jodamoexchange.osam.cores.apps.AppPreferencesModel;
import com.jodamoexchange.osam.cores.apps.models.SharedPreferencesModel;

public abstract class PreferencesController extends AppController implements AppPreferenceController {

    protected AppPreferencesModel prefsModel;

    public PreferencesController(Context context, String name) {
        this (context, name, Context.MODE_PRIVATE);
    }

    public PreferencesController(Context context, String name, int mode) {
        super(context);
        this.prefsModel = new SharedPreferencesModel(context.getSharedPreferences(name, mode));
    }

    @Override
    public boolean hasProperty(String name) {
        return this.prefsModel.hasProperty(name);
    }

    @Override
    public <V> boolean compareData(String name, V value) {
        return this.prefsModel.compareValue(name, value);
    }

    @Override
    public long getLong(String name) {
        return this.prefsModel.getLong(name);
    }

    @Override
    public int getInt(String name) {
        return this.prefsModel.getInt(name);
    }

    @Override
    public float getFloat(String name) {
        return this.prefsModel.getFloat(name);
    }

    @Override
    public boolean getBoolean(String name) {
        return this.prefsModel.getBoolean(name);
    }

    @Override
    public String getString(String name) {
        return this.prefsModel.getString(name);
    }
}
