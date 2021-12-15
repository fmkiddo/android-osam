package com.jodamoexchange.osam.cores;

public interface Attribute<V> extends Core {

    boolean isSameClassType (V value);

    V getValue ();

    int compareTo (V newValue);
}
