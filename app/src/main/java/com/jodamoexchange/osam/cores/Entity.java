package com.jodamoexchange.osam.cores;

import java.util.List;

public interface Entity extends Core {

    boolean hasAttribute (String name);

    <V> boolean putAttribute (String name, V value);

    <V> V getAttribute (String name);
}
