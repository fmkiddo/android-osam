package com.jodamoexchange.osam.cores.apps.models;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.jodamoexchange.osam.cores.Entity;
import com.jodamoexchange.osam.cores.app.Helper;
import com.jodamoexchange.osam.cores.apps.AppModel;
import com.jodamoexchange.osam.cores.apps.AppRequestModel;
import com.jodamoexchange.osam.cores.apps.helper.EntityHelper;

import java.util.Map;

public abstract class AppHttpRequestModel<T> extends AppModel implements AppRequestModel<T> {

    private Entity result;
    private String URL;
    private T params;
    private Map<String, String> headers;
    private Request<T> requestObject;
    private Response.Listener<T> listener;
    private Response.ErrorListener errorListener;
    protected Context applicationContext;

    public AppHttpRequestModel () {
        super ();
        this.result = EntityHelper.newEntity();
    }

    protected abstract Request<T> initRequest (String URL, T params, Map<String, String> headers, Response.Listener<T> listener, Response.ErrorListener errorListener);

    @Override
    public void setParam(T object) {
        this.params = object;
    }

    @Override
    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    @Override
    public void setURL(String URL) {
        this.URL = Helper.getServerURL(URL);
    }

    @Override
    public void setApplicationContext(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setListener(Response.Listener<T> listener) {
        this.listener = listener;
    }

    @Override
    public void setErrorListener(Response.ErrorListener errorListener) {
        this.errorListener = errorListener;
    }

    @Override
    public int execute() {
        int responseCode = 500;
        this.requestObject = this.initRequest(this.URL, this.params, this.headers, this.listener, this.errorListener);
        RequestQueue queue = Volley.newRequestQueue(this.applicationContext);
        queue.add(this.requestObject);
        return responseCode;
    }

    protected <V> void putAttribute (String name, V value) {
        this.result.putAttribute(name, value);
    }
}
