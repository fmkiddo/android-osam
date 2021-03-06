package com.jodamoexchange.osam.cores.app;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.jodamoexchange.osam.cores.app.controller.CentralPreferenceController;
import com.jodamoexchange.osam.cores.apps.AppActivity;

public abstract class OsamAppActivity extends AppActivity {

    @Override
    protected void initCreateApp(@Nullable Bundle savedInstanceState) {
        this.prefControlClass = CentralPreferenceController.class;
        super.initCreateApp(savedInstanceState);
    }
}
