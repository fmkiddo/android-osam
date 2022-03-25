package com.jodamoexchange.osam.cores.app.activities.listeners.dialogs;

import android.content.DialogInterface;

import com.jodamoexchange.osam.cores.app.activities.LoginActivity;
import com.jodamoexchange.osam.cores.apps.listeners.AppAbstractDIOnClickListener;
import com.jodamoexchange.osam.cores.apps.AppConstants;

public class LogoutOnClickListener extends AppAbstractDIOnClickListener {

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            default:
                dialog.dismiss();
                break;
            case DialogInterface.BUTTON_POSITIVE:
                this.getPreferenceController().putValue(AppConstants.TOKEN_LOGIN_KEY, "");
                this.startNewActivity(LoginActivity.class);
                break;
        }
    }
}
