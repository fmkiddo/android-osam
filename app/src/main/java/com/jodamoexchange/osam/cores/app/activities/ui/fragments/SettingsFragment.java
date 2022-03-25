package com.jodamoexchange.osam.cores.app.activities.ui.fragments;

import com.jodamoexchange.osam.R;
import com.jodamoexchange.osam.cores.app.activities.listeners.SettingsListener;
import com.jodamoexchange.osam.cores.apps.AppFragment;

public class SettingsFragment extends AppFragment {

    public SettingsFragment () {
        super (SettingsListener.class);
        this.layoutId   = R.layout.fragment_settings;
        this.viewIds    = new int[0];
    }
}
