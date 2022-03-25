package com.jodamoexchange.osam.cores.apps;

import android.widget.ExpandableListAdapter;

import java.util.List;
import java.util.Map;

public interface AppExpandableListAdapter<G, C> extends ExpandableListAdapter {

    void setApplicationContext (AppActivity appActivity);

    AppActivity getApplicationContext ();

    void setListGroup (List<G> groups);

    void setListData (Map<G, List<C>> listData);

    @Override
    G getGroup(int groupPosition);

    @Override
    C getChild(int groupPosition, int childPosition);
}
