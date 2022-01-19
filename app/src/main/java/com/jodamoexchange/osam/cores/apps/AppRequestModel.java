package com.jodamoexchange.osam.cores.apps;

import android.content.Context;

import com.android.volley.Response;
import com.jodamoexchange.osam.cores.Model;

import java.util.Map;

public interface AppRequestModel<T> extends Model {

    void setApplicationContext (Context context);

    void setListener (Response.Listener<T> listener);

    void setErrorListener (Response.ErrorListener errorListener);

    int execute ();

    void setURL (String URL);

    void setParam (T object);

    void setHeaders (Map<String, String> headers);
}
