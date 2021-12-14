package com.jodamoexchange.osam.cores;

import java.util.List;

public interface EntityModel extends Model {

    <V> List<Entity> find (V value);
}
