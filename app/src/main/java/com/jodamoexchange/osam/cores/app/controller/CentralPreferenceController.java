package com.jodamoexchange.osam.cores.app.controller;

import android.content.Context;

import com.jodamoexchange.osam.cores.app.AppConstants;
import com.jodamoexchange.osam.cores.controllers.MutablePreferencesController;

public class CentralPreferenceController extends MutablePreferencesController {

    public CentralPreferenceController (Context context) {
        super(context, AppConstants.SP_TOKEN, Context.MODE_PRIVATE);
        this.putValue(AppConstants.TOKEN_LICENSE_KEY, "");
        this.putValue(AppConstants.TOKEN_LOGIN_KEY, "");
    }
}
