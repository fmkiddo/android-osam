package com.jodamoexchange.osam.cores.apps;

import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AppJsonObjectRequest extends JsonObjectRequest {

    private Map<String, String> headers;

    public AppJsonObjectRequest(int method, String url, @Nullable JSONObject jsonRequest, Response.Listener<JSONObject> listener, @Nullable Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
        this.headers = new HashMap<>();
    }

    public AppJsonObjectRequest(String url, @Nullable JSONObject jsonRequest, Response.Listener<JSONObject> listener, @Nullable Response.ErrorListener errorListener) {
        this (Method.PUT, url, jsonRequest, listener, errorListener);
    }

    public boolean setHeader (String name, String value) {
        this.headers.put(name, value);
        return this.headers.containsKey(name);
    }

    @Override
    public Map<String, String> getHeaders() {
        return this.headers;
    }
}
