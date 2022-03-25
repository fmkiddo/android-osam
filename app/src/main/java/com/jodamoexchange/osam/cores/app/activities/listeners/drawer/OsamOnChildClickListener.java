package com.jodamoexchange.osam.cores.app.activities.listeners.drawer;

import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.jodamoexchange.osam.R;
import com.jodamoexchange.osam.cores.app.activities.ui.fragments.AssetMoveinFragment;
import com.jodamoexchange.osam.cores.app.activities.ui.fragments.AssetMoveoutFragment;
import com.jodamoexchange.osam.cores.apps.listeners.AppBaseOnChildClickListener;
import com.jodamoexchange.osam.cores.apps.AppMenu;

public class OsamOnChildClickListener extends AppBaseOnChildClickListener<AppMenu, AppMenu> {

    @Override
    public boolean onChildClick(
            ExpandableListView parent,
            View v,
            int groupPosition,
            int childPosition,
            long id) {
        AppMenu childMenu = this.getAdapter().getChild(groupPosition, childPosition);
        int childMenuId = childMenu.getResourceId();
        switch (childMenuId) {
            default:
                Toast.makeText(this.getApplicationContext(), childMenuId, Toast.LENGTH_LONG).show();
                break;
            case R.string.menu_title_assetout:
                this.getApplicationContext().swapFragment(AssetMoveoutFragment.class.getName());
                break;
            case R.string.menu_title_assetin:
                this.getApplicationContext().swapFragment(AssetMoveinFragment.class.getName());
                break;
        }
        return true;
    }
}
