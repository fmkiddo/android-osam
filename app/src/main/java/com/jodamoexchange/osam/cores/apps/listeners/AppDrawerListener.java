package com.jodamoexchange.osam.cores.apps.listeners;

import androidx.drawerlayout.widget.DrawerLayout;

import com.jodamoexchange.osam.cores.apps.AppActivity;

public interface AppDrawerListener extends DrawerLayout.DrawerListener {

    void setApplicationContext (AppActivity activity);
}
