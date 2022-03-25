package com.jodamoexchange.osam.cores.apps;

import java.util.ArrayList;
import java.util.List;

public abstract class AppBaseMutableExpandableListAdapter<G, C> extends AppBaseExpandableListAdapter<G, C>
        implements AppMutableExpandableListAdapter<G, C> {

    @Override
    public AppMutableExpandableListAdapter<G, C> addGroup(G group) {
        if (!this.groups.contains(group)) {
            this.groups.add(group);
            if (!this.structures.containsKey(group)) this.structures.put(group, new ArrayList<>());
        }
        return this;
    }

    @Override
    public AppMutableExpandableListAdapter<G, C> addChild(G group, C child) {
        if (!this.groups.contains(group)) this.addGroup(group).addChild(group, child);
        else {
            List<C> childs = this.structures.get(group);
            if (!childs.contains(child)) childs.add(child);
        }
        return this;
    }
}
