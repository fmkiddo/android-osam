package com.jodamoexchange.osam.cores.apps;

import com.jodamoexchange.osam.cores.Model;

public interface AppPreferencesModel extends Model {

    boolean hasProperty (String name);

    <V> boolean compareValue (String name, V value);

    AppPreferencesModel putValue (String name, int value);

    AppPreferencesModel putValue (String name, long value);

    AppPreferencesModel putValue (String name, float value);

    AppPreferencesModel putValue (String name, boolean value);

    AppPreferencesModel putValue (String name, String value);

    int getInt (String name);

    long getLong (String name);

    float getFloat (String name);

    boolean getBoolean (String name);

    String getString (String name);
}
