package com.jodamoexchange.osam.cores.apps;

public interface AppMutableExpandableListAdapter<G, C> extends AppExpandableListAdapter<G, C> {

    AppMutableExpandableListAdapter<G, C> addGroup (G group);

    AppMutableExpandableListAdapter<G, C> addChild (G group, C child);
}
