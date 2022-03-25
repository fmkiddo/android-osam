package com.jodamoexchange.osam.cores.apps.listeners;

import android.content.DialogInterface;

import androidx.appcompat.app.AppCompatActivity;

import com.jodamoexchange.osam.cores.apps.AppActivity;

public interface AppDIOnClickListener extends DialogInterface.OnClickListener {

    boolean setApplicationContext (AppActivity activity);
}
