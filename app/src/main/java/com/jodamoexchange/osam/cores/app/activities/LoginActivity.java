package com.jodamoexchange.osam.cores.app.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.jodamoexchange.osam.R;
import com.jodamoexchange.osam.cores.app.activities.ui.AuthorizationFragment;
import com.jodamoexchange.osam.cores.app.activities.ui.UserAuthorizationFragment;
import com.jodamoexchange.osam.cores.apps.AppConstants;
import com.jodamoexchange.osam.cores.apps.AppFragment;

public class LoginActivity extends AppAbstractActivity {

    @Override
    protected void initCreateApp(@Nullable Bundle savedInstanceState) {
        super.initCreateApp(savedInstanceState);
        this.getSupportActionBar().hide();
        this.setContentView(R.layout.activity_login);

        FragmentManager fmanager            = this.getSupportFragmentManager();

        Fragment displayedFragment = null;
        if (fmanager.findFragmentByTag(AppConstants.DISPLAYED_FRAGMENT) != null)
            displayedFragment = fmanager.findFragmentByTag(AppConstants.DISPLAYED_FRAGMENT);
        else {
            if (this.getPreferenceController().compareData(AppConstants.TOKEN_LICENSE_KEY, "")) displayedFragment = new AuthorizationFragment();
            else displayedFragment = new UserAuthorizationFragment();
        }
        FragmentTransaction fTransaction    = fmanager.beginTransaction();
        fTransaction.replace(R.id.content_layout, displayedFragment, AppConstants.DISPLAYED_FRAGMENT);
        fTransaction.commit();
    }
}
