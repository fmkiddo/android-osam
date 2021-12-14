package com.jodamoexchange.osam.cores.controllers;

import android.content.Context;

public class MutablePreferencesController extends PreferencesController {

    public MutablePreferencesController(Context context, String name) {
        super(context, name);
    }

    public MutablePreferencesController(Context context, String name, int mode) {
        super(context, name, mode);
    }

    public <V> boolean putValue (String name, V value) {
        if (value instanceof Integer) this.getPreferencesModel().putValue(name, (int) value);
        if (value instanceof Long) this.getPreferencesModel().putValue(name, (long) value);
        if (value instanceof Float) this.getPreferencesModel().putValue(name, (float) value);
        if (value instanceof Boolean) this.getPreferencesModel().putValue(name, (boolean) value);
        if (value instanceof String) this.getPreferencesModel().putValue(name, (String) value);
        return this.getPreferencesModel().hasProperty(name);
    }
}
