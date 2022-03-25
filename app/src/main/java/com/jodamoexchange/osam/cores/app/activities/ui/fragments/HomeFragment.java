package com.jodamoexchange.osam.cores.app.activities.ui.fragments;

import com.jodamoexchange.osam.R;
import com.jodamoexchange.osam.cores.app.activities.listeners.HomeListener;
import com.jodamoexchange.osam.cores.apps.AppFragment;

public class HomeFragment extends AppFragment {

    public HomeFragment () {
        super (HomeListener.class);
        this.layoutId   = R.layout.fragment_home;
        this.viewIds    = new int [0];
    }
}
