package com.jodamoexchange.osam.cores;

public interface MutableAttribute<V> extends Attribute<V> {

    boolean putValue (V value);
}
