package com.jodamoexchange.osam.cores.app.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.jodamoexchange.osam.R;
import com.jodamoexchange.osam.cores.app.OsamAppActivity;
import com.jodamoexchange.osam.cores.app.activities.ui.fragments.AuthorizationFragment;
import com.jodamoexchange.osam.cores.app.activities.ui.fragments.UserAuthorizationFragment;
import com.jodamoexchange.osam.cores.apps.AppConstants;

public class LoginActivity extends OsamAppActivity {

    @Override
    protected void initCreateApp(@Nullable Bundle savedInstanceState) {
        super.initCreateApp(savedInstanceState);
        this.setContentView(R.layout.activity_login);

        FragmentManager fmanager            = this.getSupportFragmentManager();

        Fragment displayedFragment = null;
        if (fmanager.findFragmentByTag(AppConstants.DISPLAYED_FRAGMENT) != null)
            displayedFragment = fmanager.findFragmentByTag(AppConstants.DISPLAYED_FRAGMENT);
        else {
            if (this.getMutablePreferencesController().compareData(AppConstants.TOKEN_LICENSE_KEY, "")) displayedFragment = new AuthorizationFragment();
            else displayedFragment = new UserAuthorizationFragment();
        }
        FragmentTransaction fTransaction    = fmanager.beginTransaction();
        fTransaction.replace(R.id.login_content_layout, displayedFragment, AppConstants.DISPLAYED_FRAGMENT);
        fTransaction.commit();
    }
}
