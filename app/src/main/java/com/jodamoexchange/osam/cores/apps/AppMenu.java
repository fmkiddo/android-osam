package com.jodamoexchange.osam.cores.apps;

import com.jodamoexchange.osam.cores.Core;

public interface AppMenu extends Core {

    int getResourceId ();

    String getDefaultString ();

    AppFragment getFragment ();
}
