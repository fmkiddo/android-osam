package com.jodamoexchange.osam.cores.apps.models;

import com.android.volley.Request;
import com.android.volley.Response;
import com.jodamoexchange.osam.cores.apps.AppJsonObjectRequest;

import org.json.JSONObject;

import java.util.Map;

public abstract class JSONRequestModel extends AppHttpRequestModel<JSONObject> {

    @Override
    protected Request<JSONObject> initRequest(String URL, JSONObject params, Map<String, String> headers, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        AppJsonObjectRequest objectRequest = new AppJsonObjectRequest(URL, params, listener, errorListener);
        for (Map.Entry<String, String> entry : headers.entrySet()) objectRequest.setHeader(entry.getKey(), entry.getValue());
        return objectRequest;
    }
}
