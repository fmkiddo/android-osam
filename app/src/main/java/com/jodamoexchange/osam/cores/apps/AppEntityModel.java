package com.jodamoexchange.osam.cores.apps;

import com.jodamoexchange.osam.cores.Entity;
import com.jodamoexchange.osam.cores.EntityModel;

import java.util.List;

public abstract class AppEntityModel extends AppModel implements EntityModel {

    private List<Entity> entities;
}
