package com.jodamoexchange.osam.cores.apps;

public interface AppMutablePreferenceController extends AppPreferenceController {

    <V> boolean putValue (String name, V value);

    AppPreferenceController toPreferenceController ();
}
