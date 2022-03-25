package com.jodamoexchange.osam.cores.app;

import androidx.core.content.ContextCompat;

import com.jodamoexchange.osam.R;
import com.jodamoexchange.osam.cores.apps.AppActivity;
import com.jodamoexchange.osam.cores.apps.AppConstants;

public class Helper {

    private Helper () {}

    public static String getServerURL (String postfix) {
        String serverUrl = AppConstants.SERVER_URL;
        char lastChar = serverUrl.charAt(serverUrl.length() - 1);
        if (lastChar != '/') serverUrl = serverUrl + "/";
        return serverUrl + postfix;
    }

    public static int getDocumentStatusColor (AppActivity activity, int statusCode) {
        int statusColor = -1;
        switch (statusCode) {
            default:
                statusColor = ContextCompat.getColor(activity, R.color.document_waiting);
                break;
            case AppConstants.DOCSTAT_DECLINED:
                statusColor = ContextCompat.getColor(activity, R.color.document_declined);
                break;
            case AppConstants.DOCSTAT_APPROVED:
                statusColor = ContextCompat.getColor(activity, R.color.document_approved);
                break;
            case AppConstants.DOCSTAT_SENT:
                statusColor = ContextCompat.getColor(activity, R.color.document_sent);
                break;
            case AppConstants.DOCSTAT_RECEIVED:
                statusColor = ContextCompat.getColor(activity, R.color.document_received);
                break;
            case AppConstants.DOCSTAT_DISTRIBUTED:
                statusColor = ContextCompat.getColor(activity, R.color.document_distributed);
                break;
        }
        return statusColor;
    }
}
