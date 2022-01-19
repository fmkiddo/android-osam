package com.jodamoexchange.osam.cores.apps;

import com.android.volley.Response;
import com.jodamoexchange.osam.cores.Controller;
import com.jodamoexchange.osam.cores.Entity;

public interface AppRequestController<T> extends Controller {

    boolean execute ();

    void setListener (Response.Listener<T> listener);

    void setErrorListener (Response.ErrorListener errorListener);
}
