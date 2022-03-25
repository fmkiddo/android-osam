package com.jodamoexchange.osam.cores.apps;

public interface AppConstants {

    static final String SP_TOKEN            = "com.fmkiddo.app.compute.osam";
    static final String TOKEN_LICENSE_KEY   = "com.fmkiddo.app.compute.osam.token_auth";
    static final String TOKEN_LOGIN_KEY     = "com.fmkiddo.app.compute.osam.token_user";

    static final String SERVER_URL          = "https://cdn.jodamoexchange.com/restful/";
    static final String REQUEST_URL         = "client/api/mobile-requestdata";

    static final String DISPLAYED_FRAGMENT  = "VISIBLE_FRAGMENT";

    static final String CODE_ERROR          = "AppCodeError";
    static final String HTTP_ERROR          = "HTTPRequestError";
    static final String HTTP_RESPONSE_ERROR = "HTTPResponseError";

    static final String HEADER_CONTENT_TYPE     = "Content-Type";
    static final String HEADER_AUTHORIZATION    = "Authorization";
    static final String HEADER_ACCEPT           = "Accept";

    static final String LOGIN_KEY_USERID        = "id";
    static final String LOGIN_KEY_USERNAME      = "user";

    static final String REQ_TRIGGER             = "data-trigger";
    static final String REQ_TRANSMIT            = "data-transmit";
    static final String REQ_LOGGEDOUSR          = "data-loggedousr";

    static final String CONTENT_JSON            = "application/json";
    static final String RESPONSE_STATUS         = "status";
    static final String RESPONSE_MESSAGE        = "message";

    static final String PARAM_DOCNUM            = "DOCUNUM";

    static final Integer RESPONSE_OK            = 200;

    static final int DOCSTAT_DECLINED       = 0;
    static final int DOCSTAT_WAITING        = 1;
    static final int DOCSTAT_APPROVED       = 2;
    static final int DOCSTAT_SENT           = 3;
    static final int DOCSTAT_RECEIVED       = 4;
    static final int DOCSTAT_DISTRIBUTED    = 5;

}
