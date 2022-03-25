package com.jodamoexchange.osam.cores.app.activities.ui.fragments;

import com.jodamoexchange.osam.R;
import com.jodamoexchange.osam.cores.app.activities.listeners.AuthorizationListener;
import com.jodamoexchange.osam.cores.app.activities.listeners.LoginListener;
import com.jodamoexchange.osam.cores.apps.AppFragment;

public class UserAuthorizationFragment extends AppFragment {

    public UserAuthorizationFragment () {
        super (LoginListener.class);
        this.layoutId   = R.layout.fragment_userverification;
        this.viewIds    = new int[] {
                R.id.et_username,
                R.id.et_password,
                R.id.btn_userlogin,
                R.id.btn_clientreset
        };
    }
}
