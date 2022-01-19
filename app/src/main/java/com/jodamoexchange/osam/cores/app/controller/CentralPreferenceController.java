package com.jodamoexchange.osam.cores.app.controller;

import android.content.Context;

import com.jodamoexchange.osam.cores.apps.AppConstants;
import com.jodamoexchange.osam.cores.apps.AppPreferenceController;
import com.jodamoexchange.osam.cores.apps.controllers.MutablePreferencesController;

public class CentralPreferenceController extends MutablePreferencesController {

    public CentralPreferenceController (Context context) {
        super(context, AppConstants.SP_TOKEN);
        if (!this.hasProperty(AppConstants.TOKEN_LICENSE_KEY)) this.putValue(AppConstants.TOKEN_LICENSE_KEY, "");
        if (!this.hasProperty(AppConstants.TOKEN_LOGIN_KEY)) this.putValue(AppConstants.TOKEN_LOGIN_KEY, "");
    }
}
