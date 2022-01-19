package com.jodamoexchange.osam.cores.apps.controllers;

import android.content.Context;

import com.jodamoexchange.osam.cores.apps.AppMutablePreferenceController;
import com.jodamoexchange.osam.cores.apps.AppPreferenceController;

public abstract class MutablePreferencesController extends PreferencesController implements AppMutablePreferenceController {

    public MutablePreferencesController(Context context, String name) {
        this (context, name, Context.MODE_PRIVATE);
    }

    public MutablePreferencesController(Context context, String name, int mode) {
        super(context, name, mode);
    }

    public <V> boolean putValue (String name, V value) {
        if (value instanceof Integer) this.prefsModel.putValue(name, (int) value);
        if (value instanceof Long) this.prefsModel.putValue(name, (long) value);
        if (value instanceof Float) this.prefsModel.putValue(name, (float) value);
        if (value instanceof Boolean) this.prefsModel.putValue(name, (boolean) value);
        if (value instanceof String) this.prefsModel.putValue(name, (String) value);
        return this.prefsModel.hasProperty(name);
    }

    @Override
    public AppPreferenceController toPreferenceController() {
        return this;
    }
}
