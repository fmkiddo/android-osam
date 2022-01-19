package com.jodamoexchange.osam.cores.apps;

import com.jodamoexchange.osam.cores.Core;

public interface AppPreferenceController extends Core {

    boolean hasProperty (String name);

    <V> boolean compareData (String name, V value);

    long getLong (String name);

    int getInt (String name);

    float getFloat (String name);

    boolean getBoolean (String name);

    String getString (String name);
}
