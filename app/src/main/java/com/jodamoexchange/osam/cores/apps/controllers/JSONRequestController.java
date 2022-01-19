package com.jodamoexchange.osam.cores.apps.controllers;

import android.content.Context;

import com.jodamoexchange.osam.cores.apps.AppRequestModel;

import org.json.JSONObject;

import java.util.Map;

public class JSONRequestController extends AppHttpRequestController<JSONObject> {

    public JSONRequestController(Context context, String URL, Class<? extends AppRequestModel<JSONObject>> aClass, JSONObject param, Map<String, String> headers) {
        super(context, URL, aClass, param, headers);
    }
}
