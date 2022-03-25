package com.jodamoexchange.osam.cores.app.activities.listeners;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.jodamoexchange.osam.R;
import com.jodamoexchange.osam.cores.app.activities.ui.fragments.AssetMoveinDetailFragment;
import com.jodamoexchange.osam.cores.app.activities.ui.fragments.AssetMoveoutDetailFragment;
import com.jodamoexchange.osam.cores.app.models.AssetRequestModel;
import com.jodamoexchange.osam.cores.apps.AppConstants;
import com.jodamoexchange.osam.cores.apps.listeners.AppDialogOnClickListener;
import com.jodamoexchange.osam.cores.apps.AppFragment;
import com.jodamoexchange.osam.cores.apps.listeners.AppViewOnClickListener;
import com.jodamoexchange.osam.cores.apps.controllers.AppHttpRequestController;
import com.jodamoexchange.osam.cores.apps.controllers.JSONRequestController;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;

public class AssetViewClickListener extends AppViewOnClickListener implements Response.Listener<JSONObject>,
                                                                                Response.ErrorListener {

    abstract class InnerAppDialogOnClickListener extends AppDialogOnClickListener {

        private String docnum;
        protected Response.Listener<JSONObject> listener;
        protected Response.ErrorListener errorListener;

        InnerAppDialogOnClickListener (String docnum, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
            this.docnum = docnum;
            this.listener = listener;
            this.errorListener = errorListener;
        }

        protected String getDocnum () {
            return this.docnum;
        }
    }

    class MoveOutSentOnClickListener extends InnerAppDialogOnClickListener {

        MoveOutSentOnClickListener(String docnum, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
            super(docnum, listener, errorListener);
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                default:
                    // do nothing and dismiss the dialog
                    Log.i("userCancel", "Cancelling moveout send action");
                    break;
                case DialogInterface.BUTTON_POSITIVE:
                    JSONObject params   = new JSONObject();
                    JSONObject transmit = new JSONObject();
                    try {
                        transmit.put("do", "marksent");
                        transmit.put("docnum", this.getDocnum());
                        transmit.put(AppConstants.REQ_LOGGEDOUSR, this.getAppActivity().getLoggedOusrID());

                        params.put(AppConstants.REQ_TRIGGER, "moveout-doaction");
                        params.put(AppConstants.REQ_TRANSMIT, transmit);
                    } catch (JSONException exception) {
                        Log.e("JsonAssignError", exception.toString());
                    }

                    Map<String, String> headers = new HashMap<>();
                    headers.put(AppConstants.HEADER_AUTHORIZATION, this.getAppActivity().getAppAuthorization());
                    headers.put(AppConstants.HEADER_CONTENT_TYPE, AppConstants.CONTENT_JSON);
                    headers.put(AppConstants.HEADER_ACCEPT, AppConstants.CONTENT_JSON);

                    AppHttpRequestController<JSONObject> requestController = new JSONRequestController(this.getAppActivity(), AppConstants.REQUEST_URL, AssetRequestModel.class, params, headers);
                    requestController.setListener(this.listener);
                    requestController.setErrorListener(this.errorListener);
                    requestController.execute();
                    break;
            }
            dialog.dismiss();
        }
    }

    class MoveOutApproveOnClickListener extends InnerAppDialogOnClickListener {

        MoveOutApproveOnClickListener(String docnum, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
            super(docnum, listener, errorListener);
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                default:
                    // do nothing and dismiss the dialog
                    Log.i("userCancel", "Cancelling moveout approval");
                    break;
                case DialogInterface.BUTTON_POSITIVE:
                    JSONObject params   = new JSONObject();
                    JSONObject transmit = new JSONObject();
                    try {
                        transmit.put("do", "approve");
                        transmit.put("docnum", this.getDocnum());
                        transmit.put(AppConstants.REQ_LOGGEDOUSR, this.getAppActivity().getLoggedOusrID());

                        params.put(AppConstants.REQ_TRIGGER, "moveout-doaction");
                        params.put(AppConstants.REQ_TRANSMIT, transmit);
                    } catch (JSONException exception) {
                        Log.e("JsonAssignError", exception.toString());
                    }

                    Map<String, String> headers = new HashMap<>();
                    headers.put(AppConstants.HEADER_AUTHORIZATION, this.getAppActivity().getAppAuthorization());
                    headers.put(AppConstants.HEADER_CONTENT_TYPE, AppConstants.CONTENT_JSON);
                    headers.put(AppConstants.HEADER_ACCEPT, AppConstants.CONTENT_JSON);

                    AppHttpRequestController<JSONObject> requestController = new JSONRequestController(this.getAppActivity(), AppConstants.REQUEST_URL, AssetRequestModel.class, params, headers);
                    requestController.setListener(this.listener);
                    requestController.setErrorListener(this.errorListener);
                    requestController.execute();
                    break;
            }
            dialog.dismiss();
        }
    }

    class MoveOutDeclineOnClickListener extends InnerAppDialogOnClickListener {

        MoveOutDeclineOnClickListener(String docnum, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
            super(docnum, listener, errorListener);
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                default:
                    // do nothing and dismiss the dialog
                    Log.i("userCancel", "Cancelling moveout decline");
                    break;
                case DialogInterface.BUTTON_POSITIVE:
                    JSONObject params = new JSONObject();
                    JSONObject transmit = new JSONObject();

                    try {
                        transmit.put("do", "decline");
                        transmit.put("docnum", this.getDocnum());
                        transmit.put(AppConstants.REQ_LOGGEDOUSR, this.getAppActivity().getLoggedOusrID());

                        params.put(AppConstants.REQ_TRIGGER, "moveout-doaction");
                        params.put(AppConstants.REQ_TRANSMIT, transmit);
                    } catch (JSONException exception) {
                        Log.e("JsonAssignError", exception.toString());
                    }

                    Map<String, String> headers = new HashMap<>();
                    headers.put(AppConstants.HEADER_AUTHORIZATION, this.getAppActivity().getAppAuthorization());
                    headers.put(AppConstants.HEADER_CONTENT_TYPE, AppConstants.CONTENT_JSON);
                    headers.put(AppConstants.HEADER_ACCEPT, AppConstants.CONTENT_JSON);

                    AppHttpRequestController<JSONObject> requestController = new JSONRequestController(this.getAppActivity(), AppConstants.REQUEST_URL, AssetRequestModel.class, params, headers);
                    requestController.setListener(this.listener);
                    requestController.setErrorListener(this.errorListener);
                    requestController.execute();
                    break;
            }
            dialog.dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getClass().getName().compareTo(TableRow.class.getName()) == 0) {
            TableRow tableRow = (TableRow) v;
            TableLayout tableLayout = (TableLayout) tableRow.getParent();
            switch (tableLayout.getId()) {
                default:
                    Log.e("event", "Unregistered table row click source");
                    break;
                case R.id.table_assetmo:
                    this.assetMoveoutDocClick(tableRow);
                    break;
                case R.id.table_assetmi:
                    this.assetMoveInDocClick(tableRow);
                    break;
            }
        } else if (v.getClass().getName().equals(Button.class.getName())) {
            Button eventSource = (Button) v;
            String buttonText = eventSource.getText().toString();

            AppFragment displayedFragment = this.getApplicationContext().getVisibleFragment();
            if (displayedFragment.getClass().getName().equals(AssetMoveoutDetailFragment.class.getName())) {
                String docnum = null;

                TextView tvDocnum = v.getRootView().findViewById(R.id.tv_docnum_text);
                if (tvDocnum != null) docnum = tvDocnum.getText().toString();

                if (docnum == null) throw new InvalidParameterException("Text view element not found!");

                AlertDialog.Builder builder = new AlertDialog.Builder(this.getApplicationContext());
                if (buttonText.equals(this.getResources().getText(R.string.text_approve))) {
                    InnerAppDialogOnClickListener approvalListener  = new MoveOutApproveOnClickListener(docnum, this, this);
                    approvalListener.setApplicationContext(this.getApplicationContext());
                    builder.setTitle(R.string.title_apprmvo).setMessage(R.string.msg_apprmvo).setPositiveButton(R.string.dbtn_yes, approvalListener)
                            .setNegativeButton(R.string.dbtn_cancel, approvalListener);
                }

                if (buttonText.equals(this.getResources().getText(R.string.text_decline))) {
                    InnerAppDialogOnClickListener declineListener   = new MoveOutDeclineOnClickListener(docnum, this, this);
                    declineListener.setApplicationContext(this.getApplicationContext());
                    builder.setTitle(R.string.title_declmvo).setMessage(R.string.msg_declmvo).setPositiveButton(R.string.dbtn_yes, declineListener)
                            .setNegativeButton(R.string.dbtn_cancel, declineListener);
                }

                if (buttonText.equals(this.getResources().getText(R.string.text_sent))) {
                    InnerAppDialogOnClickListener sentListener     = new MoveOutSentOnClickListener(docnum, this, this);
                    sentListener.setApplicationContext(this.getApplicationContext());
                    builder.setTitle(R.string.title_senwmvo).setMessage(R.string.msg_senwmvo).setPositiveButton(R.string.dbtn_yes, sentListener)
                            .setNegativeButton(R.string.dbtn_no, sentListener);
                }

                builder.create();
                builder.show();
            }
        }
    }

    @Override
    public void onResponse(JSONObject response) {
        int status = 0;
        try {
            status = response.getInt("status");
        } catch (JSONException exception) {
            Log.e("jsonRtvError", exception.getMessage());
        }

        if (status != 200) ;
        else {
            this.getApplicationContext().getOnBackPressedDispatcher().onBackPressed();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("jsonReqError", error.toString());
    }

    private void assetMoveoutDocClick (TableRow source) {
        TextView docCell = (TextView) source.getChildAt(2);
        Bundle args = new Bundle();
        args.putString(AppConstants.PARAM_DOCNUM, docCell.getText().toString());
        this.getApplicationContext().swapFragment(AssetMoveoutDetailFragment.class.getName(), args);
    }

    private void assetMoveInDocClick (TableRow source) {
        TextView docCell = (TextView) source.getChildAt(2);
        Bundle args = new Bundle();
        args.putString(AppConstants.PARAM_DOCNUM, docCell.getText().toString());
        this.getApplicationContext().swapFragment(AssetMoveinDetailFragment.class.getName(), args);
    }
}
