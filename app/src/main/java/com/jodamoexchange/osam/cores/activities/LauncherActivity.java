package com.jodamoexchange.osam.cores.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.jodamoexchange.osam.cores.app.AppConstants;
import com.jodamoexchange.osam.cores.app.controller.CentralPreferenceController;
import com.jodamoexchange.osam.cores.apps.AppActivity;

public class LauncherActivity extends AppActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide();
        this.setPreferenceController(new CentralPreferenceController(this));
        if (!this.getPreferenceController().hasProperty(AppConstants.TOKEN_LICENSE_KEY)) finish();
        else {
            Intent nextIntent = null;
            if (this.getPreferenceController().compareData(AppConstants.TOKEN_LICENSE_KEY, ""))
                nextIntent = new Intent(this.getApplicationContext(), LoginActivity.class);
            else ;

            if (nextIntent == null) finish();
            else this.startActivity(nextIntent);
        }
    }
}
