package com.jodamoexchange.osam.cores.apps.controllers;

import android.content.Context;

import com.android.volley.Response;
import com.jodamoexchange.osam.cores.Entity;
import com.jodamoexchange.osam.cores.apps.AppConstants;
import com.jodamoexchange.osam.cores.apps.AppController;
import com.jodamoexchange.osam.cores.apps.AppRequestController;
import com.jodamoexchange.osam.cores.apps.AppRequestModel;

import java.util.Map;

public abstract class AppHttpRequestController<T> extends AppController implements AppRequestController<T> {

    private AppRequestModel<T> requestModel;

    public AppHttpRequestController(Context context, String URL, Class<? extends AppRequestModel<T>> aClass, T param, Map<String, String> headers) {
        super(context);
        try {
            this.requestModel = aClass.newInstance();
            this.requestModel.setApplicationContext(this.getApplicationContext());
            this.requestModel.setURL(URL);
            this.requestModel.setParam(param);
            this.requestModel.setHeaders(headers);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setListener(Response.Listener<T> listener) {
        this.requestModel.setListener(listener);
    }

    @Override
    public void setErrorListener(Response.ErrorListener errorListener) {
        this.requestModel.setErrorListener(errorListener);
    }

    @Override
    public boolean execute() {
        int responseCode = this.requestModel.execute();
        return responseCode == 200;
    }
}
