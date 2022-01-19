package com.jodamoexchange.osam.cores.apps;

import com.jodamoexchange.osam.cores.Entity;
import com.jodamoexchange.osam.cores.EntityModel;

import java.util.ArrayList;
import java.util.List;

public abstract class AppEntityModel extends AppModel implements EntityModel {

    private List<Entity> entities;

    public AppEntityModel () {
        this.entities = new ArrayList<>();
    }

    public Entity[] toArray () {
        Entity[] entitiesArray = new Entity[this.entities.size()];
        return this.entities.toArray(entitiesArray);
    }
}
