package com.jodamoexchange.osam.cores.app.activities.ui.drawer;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jodamoexchange.osam.R;
import com.jodamoexchange.osam.cores.apps.AppBaseMutableExpandableListAdapter;
import com.jodamoexchange.osam.cores.apps.AppConstants;
import com.jodamoexchange.osam.cores.apps.AppMenu;

public class OsamMenuExpandableListAdapter extends AppBaseMutableExpandableListAdapter<AppMenu, AppMenu> {

    @Override
    public long getGroupId(int groupPosition) {
        return this.getGroup(groupPosition).getResourceId();
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.app_listgroup, null);
        }

        AppMenu menu = this.getGroup(groupPosition);
        boolean resourceValid = false;
        try {
            this.getApplicationContext().getResources().getString(menu.getResourceId());
            resourceValid = true;
        } catch (Resources.NotFoundException exception) {
            Log.w(AppConstants.CODE_ERROR, "Resource not found!");
        }

        TextView tv_groupheader = convertView.findViewById(R.id.tv_listheader);
        if (resourceValid) tv_groupheader.setText(menu.getResourceId());
        else tv_groupheader.setText(menu.getDefaultString());

        return convertView;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return this.getChild(groupPosition, childPosition).getResourceId();
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.app_listitem, null);
        }

        AppMenu child = this.getChild(groupPosition, childPosition);
        boolean resourceValid = false;
        try {
            this.getApplicationContext().getResources().getString(child.getResourceId());
            resourceValid = true;
        } catch (Resources.NotFoundException exception) {
            Log.w(AppConstants.CODE_ERROR, "Resource not found!");
        }

        TextView tv_listitem = convertView.findViewById(R.id.tv_listitem);
        if (resourceValid) tv_listitem.setText(child.getResourceId());
        else tv_listitem.setText(child.getDefaultString());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
