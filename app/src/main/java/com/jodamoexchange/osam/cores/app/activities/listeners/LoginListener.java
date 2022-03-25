package com.jodamoexchange.osam.cores.app.activities.listeners;

import android.content.DialogInterface;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.jodamoexchange.osam.R;
import com.jodamoexchange.osam.cores.app.activities.LauncherActivity;
import com.jodamoexchange.osam.cores.app.activities.OsamActivity;
import com.jodamoexchange.osam.cores.apps.AppConstants;
import com.jodamoexchange.osam.cores.apps.listeners.AppViewOnClickListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

public class LoginListener extends AppViewOnClickListener implements DialogInterface.OnClickListener {

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        switch (viewId) {
            default:
                break;
            case R.id.btn_clientreset:
                AlertDialog.Builder builder = this.createDialogBuilder();
                builder.setTitle(R.string.title_resetclient).setMessage(R.string.q_resetclient);
                builder.setPositiveButton(R.string.dbtn_yes, this).setNegativeButton(R.string.dbtn_no, this);
                builder.show();
                break;
            case R.id.btn_userlogin:
                EditText et_username    = this.inflatedView.findViewById(R.id.et_username);
                EditText et_password    = this.inflatedView.findViewById(R.id.et_password);

                if (et_username.length() > 0 && et_password.length() > 0) this.doUserAuthentication(et_username, et_password);
                else {
                    String toastText = "";
                    if (et_username.length() == 0) {
                        toastText = this.getResources().getString(R.string.toast_emptyuser);
                        et_username.requestFocus();
                    } else {
                        toastText = this.getResources().getString(R.string.toast_emptypass);
                        et_password.requestFocus();
                    }
                    Toast.makeText(this.getApplicationContext(), toastText, Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            default:
                break;
            case DialogInterface.BUTTON_POSITIVE:
                this.prefControl.putValue(AppConstants.TOKEN_LICENSE_KEY, "");
                this.doRebirthApplication(LauncherActivity.class);
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                break;
        }
    }

    private void doUserAuthentication (EditText username, EditText password) {
        boolean authenticationValid = true;

        /**
         * TODO: create login information to API server for verification
         * Here will be codes for user verification request sent process for validation
         * by API server and return whether or not the ID is valid
         */

        if (authenticationValid) {
            JSONObject validation = new JSONObject();
            try {
                validation.put(AppConstants.LOGIN_KEY_USERID, 1);
                validation.put(AppConstants.LOGIN_KEY_USERNAME, "administrator");
            } catch (JSONException exception) {
            }
            String loginKey = Base64.encodeToString(validation.toString().getBytes(StandardCharsets.UTF_8), Base64.NO_WRAP);
            this.prefControl.putValue(AppConstants.TOKEN_LOGIN_KEY, loginKey);
            this.doStartNewActivity(OsamActivity.class);
        }
    }
}
