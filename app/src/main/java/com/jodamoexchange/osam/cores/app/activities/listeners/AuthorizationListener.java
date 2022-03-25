package com.jodamoexchange.osam.cores.app.activities.listeners;

import android.content.DialogInterface;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.jodamoexchange.osam.R;
import com.jodamoexchange.osam.cores.app.activities.ui.fragments.UserAuthorizationFragment;
import com.jodamoexchange.osam.cores.app.models.AuthenticationModel;
import com.jodamoexchange.osam.cores.apps.AppConstants;
import com.jodamoexchange.osam.cores.apps.AppFragment;
import com.jodamoexchange.osam.cores.apps.listeners.AppViewOnClickListener;
import com.jodamoexchange.osam.cores.apps.controllers.JSONRequestController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class AuthorizationListener extends AppViewOnClickListener implements DialogInterface.OnClickListener, Response.Listener<JSONObject>, Response.ErrorListener {

    static final String CLIENTCODE          = "clientcode";
    static final String CLIENTPASS          = "clientpasscode";
    static final String HTTP_CREDS_FORMAT   = "fmkiddo_%s:%s";

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            default:
                break;
            case DialogInterface.BUTTON_POSITIVE:
                this.getApplicationContext().finishAffinity();
                break;
        }
        dialog.dismiss();
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        switch (viewId) {
            default:
                break;
            case R.id.btn_authenticate:
                try {
                    this.doAuthentication();
                } catch (JSONException exception) {
                    exception.printStackTrace();
                }
                break;
            case R.id.btn_closeApp:
                AlertDialog.Builder builder = this.createDialogBuilder();
                builder.setTitle(R.string.title_exit).setMessage(R.string.q_exitapp).setPositiveButton(R.string.dbtn_yes, this)
                        .setNegativeButton(R.string.dbtn_no, this);
                builder.show();
                break;
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this.getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
        if (error.networkResponse.statusCode != 200) {
            String body = "";
            try {
                body = new String (error.networkResponse.data, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            Log.e(AppConstants.HTTP_ERROR, body);
        }
    }

    @Override
    public void onResponse(JSONObject response) {
        int statusCode = 500;
        try {
            statusCode = response.getInt("status");
        } catch (JSONException exception) {
            Log.e("onResponseError", exception.getMessage());
        }

        if (statusCode != 200) {
            AlertDialog.Builder builder = this.createDialogBuilder();
            builder.setTitle(R.string.title_autherror).setMessage(R.string.message_autherror).setNeutralButton(R.string.dbtn_close, this);
            builder.show();
        } else {
            String token = null;
            JSONArray message = null;
            try {
                message = response.getJSONArray("message");
                token   = message.getString (1);
                token   = "\"" + token + "\"";
            } catch (JSONException exception) {
                Log.e (AppConstants.HTTP_RESPONSE_ERROR, exception.getMessage());
            }

            String encodedToken = Base64.encodeToString(token.getBytes(StandardCharsets.UTF_8), Base64.NO_WRAP);
            this.prefControl.putValue(AppConstants.TOKEN_LICENSE_KEY, encodedToken);

            AppFragment loginFragment           = new UserAuthorizationFragment();
            FragmentManager fmanager            = this.getApplicationContext().getSupportFragmentManager();
            FragmentTransaction ftransaction    = fmanager.beginTransaction();
            ftransaction.replace(R.id.login_content_layout, loginFragment, AppConstants.DISPLAYED_FRAGMENT);
            ftransaction.commit();
        }
    }

    private void doAuthentication () throws JSONException {
        EditText et_authname        = this.inflatedView.findViewById(R.id.et_authname);
        EditText et_authkey         = this.inflatedView.findViewById(R.id.et_authkey);

        String authname             = et_authname.getText().toString();
        String authkey              = et_authkey.getText().toString();

        JSONObject jsonAuth         = new JSONObject();
        jsonAuth.put(CLIENTCODE, authname);
        jsonAuth.put(CLIENTPASS, authkey);
        String authString           = Base64.encodeToString(jsonAuth.toString().getBytes(StandardCharsets.UTF_8), Base64.NO_WRAP);
        String creds                = String.format(HTTP_CREDS_FORMAT, authString, ":");
        Log.i("auth", creds);
        String credString           = "Basic " + Base64.encodeToString(creds.getBytes(StandardCharsets.UTF_8), Base64.NO_WRAP);

        String URL                  = "api/client-authentication";
        Map<String, String> headers = new HashMap<>();
        headers.put(AppConstants.HEADER_CONTENT_TYPE, "application/json; charset='utf-8'");
        headers.put(AppConstants.HEADER_AUTHORIZATION, credString);
        headers.put(AppConstants.HEADER_ACCEPT, "application/json");

        JSONRequestController requestController = new JSONRequestController(this.getApplicationContext(), URL, AuthenticationModel.class, new JSONObject(), headers);
        requestController.setListener(this);
        requestController.setErrorListener(this);
        requestController.execute();
    }
}
