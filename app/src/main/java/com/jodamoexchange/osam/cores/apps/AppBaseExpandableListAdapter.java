package com.jodamoexchange.osam.cores.apps;

import android.widget.BaseExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AppBaseExpandableListAdapter<G,C> extends BaseExpandableListAdapter implements AppExpandableListAdapter<G,C> {

    private AppActivity context;
    protected List<G> groups;
    protected Map<G, List<C>> structures;

    public AppBaseExpandableListAdapter () {
        this.groups = new ArrayList<>();
        this.structures = new HashMap<>();
    }

    @Override
    public void setApplicationContext(AppActivity appActivity) {
        this.context = appActivity;
    }

    @Override
    public AppActivity getApplicationContext() {
        return this.context;
    }

    @Override
    public void setListGroup(List<G> groups) {
        this.groups = new ArrayList<>(groups);
    }

    @Override
    public void setListData(Map<G, List<C>> listData) {
        this.structures = new HashMap<>(listData);
    }

    @Override
    public G getGroup(int groupPosition) {
        G group;
        try {
            group = this.groups.get (groupPosition);
        } catch (IndexOutOfBoundsException exception) {
            group = null;
        }
        return group;
    }

    @Override
    public int getGroupCount() {
        return this.groups.size();
    }

    @Override
    public C getChild(int groupPosition, int childPosition) {
        List<C> childs = null;
        G group = this.getGroup(groupPosition);
        if (group != null) {
            childs = this.structures.get(group);
            C child;
            try {
                child = childs.get(childPosition);
            } catch (IndexOutOfBoundsException exception) {
                child = null;
            }
            return child;
        }
        return null;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        G group = this.getGroup(groupPosition);
        List<C> childs = this.structures.get(group);
        return childs.size();
    }
}
