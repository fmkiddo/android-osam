package com.jodamoexchange.osam.cores.app;

import com.jodamoexchange.osam.cores.apps.AppConstants;

public class Helper {

    private Helper () {}

    public static String getServerURL (String postfix) {
        String serverUrl = AppConstants.SERVER_URL;
        char lastChar = serverUrl.charAt(serverUrl.length() - 1);
        if (lastChar != '/') serverUrl = serverUrl + "/";
        return serverUrl + postfix;
    }
}
