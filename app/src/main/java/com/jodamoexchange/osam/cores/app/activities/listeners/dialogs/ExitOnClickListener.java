package com.jodamoexchange.osam.cores.app.activities.listeners.dialogs;

import android.content.DialogInterface;

import com.jodamoexchange.osam.cores.apps.listeners.AppAbstractDIOnClickListener;

public class ExitOnClickListener extends AppAbstractDIOnClickListener {

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (which == DialogInterface.BUTTON_POSITIVE) this.getApplicationContext().finishAffinity();
    }
}
