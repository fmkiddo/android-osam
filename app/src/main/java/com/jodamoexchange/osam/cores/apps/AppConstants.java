package com.jodamoexchange.osam.cores.apps;

public interface AppConstants {

    static final String SP_TOKEN            = "com.fmkiddo.app.compute.osam";
    static final String TOKEN_LICENSE_KEY   = "com.fmkiddo.app.compute.osam.token_auth";
    static final String TOKEN_LOGIN_KEY     = "com.fmkiddo.app.compute.osam.token_user";

    static final String SERVER_URL          = "https://cdn.jodamoexchange.com/restful/";

    static final String DISPLAYED_FRAGMENT  = "VISIBLE_FRAGMENT";

    static final String CODE_ERROR          = "AppCodeError";
    static final String HTTP_ERROR          = "HTTPRequestError";
    static final String HTTP_RESPONSE_ERROR = "HTTPResponseError";

    static final String HEADER_CONTENT_TYPE     = "Content-Type";
    static final String HEADER_AUTHENTICATION   = "Authorization";
    static final String HEADER_ACCEPT           = "Accept";
}
