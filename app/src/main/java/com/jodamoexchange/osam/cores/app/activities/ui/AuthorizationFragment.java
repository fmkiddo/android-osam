package com.jodamoexchange.osam.cores.app.activities.ui;

import com.jodamoexchange.osam.R;
import com.jodamoexchange.osam.cores.app.activities.listeners.AuthorizationListener;
import com.jodamoexchange.osam.cores.apps.AppFragment;

public class AuthorizationFragment extends AppFragment {

    public AuthorizationFragment () {
        super (AuthorizationListener.class);
        this.layoutId   = R.layout.fragment_authorization;
        this.viewIds    = new int[] {
                R.id.et_authname,
                R.id.et_authkey,
                R.id.btn_authenticate,
                R.id.btn_closeApp
        };
    }
}
