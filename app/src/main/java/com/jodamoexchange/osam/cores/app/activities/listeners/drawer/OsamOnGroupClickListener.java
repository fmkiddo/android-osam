package com.jodamoexchange.osam.cores.app.activities.listeners.drawer;

import android.view.View;
import android.widget.ExpandableListView;

import com.jodamoexchange.osam.R;
import com.jodamoexchange.osam.cores.app.activities.ui.fragments.HomeFragment;
import com.jodamoexchange.osam.cores.apps.listeners.AppBaseOnGroupClickListener;
import com.jodamoexchange.osam.cores.apps.AppMenu;

public class OsamOnGroupClickListener extends AppBaseOnGroupClickListener<AppMenu, AppMenu> {

    @Override
    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
        int resId = this.getAdapter().getGroup(groupPosition).getResourceId();
        switch (resId) {
            default:
                break;
            case R.string.menu_title_home:
                this.getApplicationContext().swapFragment(HomeFragment.class.getName());
                return true;
        }
        return false;
    }
}
