package com.jodamoexchange.osam.cores.app.activities.ui.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.jodamoexchange.osam.R;
import com.jodamoexchange.osam.cores.app.activities.listeners.AssetViewClickListener;
import com.jodamoexchange.osam.cores.app.models.AssetRequestModel;
import com.jodamoexchange.osam.cores.apps.AppConstants;
import com.jodamoexchange.osam.cores.apps.AppFragment;
import com.jodamoexchange.osam.cores.apps.controllers.AppHttpRequestController;
import com.jodamoexchange.osam.cores.apps.controllers.JSONRequestController;
import com.jodamoexchange.osam.cores.apps.exceptions.InvalidResponseException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AssetMoveoutDetailFragment extends AppFragment implements Response.Listener<JSONObject>, Response.ErrorListener {

    private String docnum;

    public AssetMoveoutDetailFragment () {
        super (AssetViewClickListener.class);
        this.layoutId = R.layout.fragment_assetmoveoutdetail;
        this.viewIds = new int[] {
        };
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            this.docnum = this.getArguments().getString(AppConstants.PARAM_DOCNUM);
            this.initData();
        } else {
            LinearLayout layoutButton = this.getView().findViewById(R.id.layout_buttons);
            layoutButton.removeAllViews();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("dataLoadError", error.toString());
    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            JSONObject responseData = this.loadDataRequest(response);
            if (this.processJson(responseData)) {
                JSONObject dataMvos = responseData.getJSONObject("dataTransmit").getJSONObject("data-moveout");
                int status = dataMvos.getInt("status");
                this.loadButtons(status, this.getView());
            }
        } catch (JSONException|InvalidResponseException exception) {
            Log.e("jsonresponse_error", exception.getMessage());
        }
    }

    @Override
    protected void initViews(View inflatedView, @Nullable Bundle savedInstanceState) {
        super.initViews(inflatedView, savedInstanceState);

        OnBackPressedCallback backPressedCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                getAppActivity().swapFragment(AssetMoveoutFragment.class.getName());
            }
        };
        this.getAppActivity().getOnBackPressedDispatcher().addCallback(this, backPressedCallback);
    }

    private JSONObject loadDataRequest(JSONObject response) throws JSONException, InvalidResponseException {
        int responseCode = response.getInt(AppConstants.RESPONSE_STATUS);
        if (responseCode != AppConstants.RESPONSE_OK) throw new InvalidResponseException("Invalid response code " + String.valueOf(responseCode));
        else {
            byte[] messageBytes = Base64.decode(response.getString(AppConstants.RESPONSE_MESSAGE), Base64.NO_WRAP);
            String jsonString   = new String (messageBytes, StandardCharsets.UTF_8);
            return new JSONObject(jsonString);
        }
    }

    private void processDocument (JSONObject docmvo) throws JSONException {
        TextView tvDocNum = this.getView().findViewById(R.id.tv_docnum_text);
        tvDocNum.setText(docmvo.getString("docnum"));

        TextView tvDocFrom = this.getView().findViewById(R.id.tv_docfrom_text);
        tvDocFrom.setText(docmvo.getString("olct_fromname"));

        TextView tvDocTo = this.getView().findViewById(R.id.tv_docdest_text);
        tvDocTo.setText(docmvo.getString("olct_toname"));
    }

    private void processDocumentDetailHeader () {
        int[] tableResources = new int[] {
                R.string.tableheader_linenum,
                R.string.tableheader_barcode,
                R.string.tableheader_desc,
                R.string.tableheader_sblsource,
                R.string.tableheader_qty
        };

        TableLayout layout = this.getView().findViewById(R.id.tbl_mvodetail);
        layout.setPadding(10, 10, 10, 10);
        TableRow headerRow = new TableRow(this.getAppActivity());

        for (int resource : tableResources) {
            TextView textView = this.createTableCell();
            textView.setText(resource);
            textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            textView.setTypeface(null, Typeface.BOLD);

            headerRow.addView(textView);
        }

        layout.addView(headerRow);
    }

    private void processDocumentDetail (JSONArray docdetmvo) throws JSONException {
        TableLayout layout = this.getView().findViewById(R.id.tbl_mvodetail);
        for (int index=0; index<docdetmvo.length(); index++) {
            JSONObject detail = docdetmvo.getJSONObject(index);
            TableRow newRow = new TableRow(this.getAppActivity());
            Iterator<String> keys = detail.keys();

            int line = 1;
            while (keys.hasNext()) {
                String key = keys.next();
                if (!key.equals("osbl_idx")) {
                    TextView cell = this.createTableCell();
                    if (key.equals("oita_idx")) cell.setText(String.valueOf(line));
                    else cell.setText(detail.getString(key));
                    newRow.addView(cell);
                }
            }
            line++;

            layout.addView(newRow);
        }
    }

    private boolean processJson (JSONObject jsonObject) throws JSONException {
        if (jsonObject.getBoolean("good")) {
            JSONObject dataTransmit = jsonObject.getJSONObject("dataTransmit");

            TableLayout tableLayout = this.getView().findViewById(R.id.tbl_mvodetail);
            if (tableLayout.getChildCount() == 0) {
                this.processDocument(dataTransmit.getJSONObject("data-moveout"));

                TextView tvDocStatus = this.getView().findViewById(R.id.tv_docstatus_text);
                tvDocStatus.setText(jsonObject.getString("documentStatus"));
                this.processDocumentDetailHeader();
                this.processDocumentDetail(dataTransmit.getJSONArray("data-moveoutdetail"));
            }
            return true;
        }
        return false;
    }

    private void loadButtons (int status, View inflatedView) {

        LinearLayout buttonLayout = inflatedView.findViewById(R.id.layout_buttons);
        if (buttonLayout.getChildCount() > 0) return;

        List<Button> buttons = new ArrayList<>();

        switch (status) {
            default:
                break;
            case AppConstants.DOCSTAT_WAITING:
                Button buttonApprove    = new Button(this.getAppActivity());
                buttonApprove.setText(R.string.text_approve);
                buttons.add(buttonApprove);
                Button buttonDecline    = new Button(this.getAppActivity());
                buttonDecline.setText(R.string.text_decline);
                buttons.add(buttonDecline);
                break;
            case AppConstants.DOCSTAT_APPROVED:
                Button buttonSend       = new Button(this.getAppActivity());
                buttonSend.setText(R.string.text_sent);
                buttons.add(buttonSend);
                break;
        }

        LinearLayout layoutButtons = inflatedView.findViewById(R.id.layout_buttons);
        for (Button theButton : buttons) {
            theButton.setPadding(10, 0, 10, 0);
            layoutButtons.addView(theButton);
            this.setViewClickListener(theButton);
        }
    }

    private void initData () {
        Map<String, String> headers = new HashMap<>();
        headers.put(AppConstants.HEADER_AUTHORIZATION, this.getAppActivity().getAppAuthorization());
        headers.put(AppConstants.HEADER_CONTENT_TYPE, AppConstants.CONTENT_JSON.concat("; charset='utf-8'"));
        headers.put(AppConstants.HEADER_ACCEPT, AppConstants.CONTENT_JSON);

        JSONObject params       = new JSONObject();
        JSONObject dataTransmit = new JSONObject();

        if (this.docnum == null) Log.e("strange", "Strangely the docnum not saved");
        try {
            params.put("data-trigger", "moveout-documentdetailed");
            dataTransmit.put("data-docnum", this.docnum);
            dataTransmit.put("data-loggedousr", this.getAppActivity().getLoggedOusrID());
            params.put("data-transmit", dataTransmit);
        } catch (JSONException exception) {
            Log.e("PutError", exception.getMessage());
        }

        AppHttpRequestController<JSONObject> requestController = new JSONRequestController (this.getAppActivity(), AppConstants.REQUEST_URL, AssetRequestModel.class, params, headers);
        requestController.setListener(this);
        requestController.setErrorListener(this);
        requestController.execute();
    }
}
