package com.jodamoexchange.osam.cores.apps;

import android.content.SharedPreferences;

import java.util.Map;

public abstract class AbstractAppPreferencesModel implements AppPreferencesModel {

    private SharedPreferences prefs;

    public AbstractAppPreferencesModel (SharedPreferences sp) {
        this.prefs = sp;
    }

    @Override
    public boolean hasProperty(String name) {
        return this.prefs.contains(name);
    }

    @Override
    public <V> boolean compareValue(String name, V value) {
        if (!this.hasProperty(name)) return false;
        else for (Map.Entry<String, ?> entry : this.prefs.getAll().entrySet())
            if (entry.getKey().equals(name)) return entry.getValue().equals(value);
        return false;
    }

    @Override
    public AppPreferencesModel putValue(String name, int value) {
        this.edit().putInt(name, value).commit();
        return this;
    }

    @Override
    public AppPreferencesModel putValue(String name, long value) {
        this.edit().putLong(name, value).commit();
        return this;
    }

    @Override
    public AppPreferencesModel putValue(String name, float value) {
        this.edit().putFloat(name, value).commit();
        return this;
    }

    @Override
    public AppPreferencesModel putValue(String name, String value) {
        this.edit().putString(name, value).commit();
        return this;
    }

    @Override
    public AppPreferencesModel putValue(String name, boolean value) {
        this.edit().putBoolean(name, value).commit();
        return this;
    }

    @Override
    public boolean getBoolean(String name) {
        return this.prefs.getBoolean(name, false);
    }

    @Override
    public float getFloat(String name) {
        return this.prefs.getFloat(name, -1);
    }

    @Override
    public int getInt(String name) {
        return this.prefs.getInt(name, -1);
    }

    @Override
    public long getLong(String name) {
        return this.prefs.getLong(name, -1);
    }

    @Override
    public String getString(String name) {
        return this.prefs.getString(name, "default-value");
    }

    private SharedPreferences.Editor edit () {
        return this.prefs.edit();
    }
}
