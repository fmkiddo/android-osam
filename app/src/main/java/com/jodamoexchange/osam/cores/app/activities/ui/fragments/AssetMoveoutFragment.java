package com.jodamoexchange.osam.cores.app.activities.ui.fragments;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.jodamoexchange.osam.R;
import com.jodamoexchange.osam.cores.app.Helper;
import com.jodamoexchange.osam.cores.app.activities.listeners.AssetViewClickListener;
import com.jodamoexchange.osam.cores.app.models.AssetRequestModel;
import com.jodamoexchange.osam.cores.apps.AppConstants;
import com.jodamoexchange.osam.cores.apps.AppFragment;
import com.jodamoexchange.osam.cores.apps.AppRequestController;
import com.jodamoexchange.osam.cores.apps.controllers.JSONRequestController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class AssetMoveoutFragment extends AppFragment implements Response.Listener<JSONObject>, Response.ErrorListener {

    private final int[] indexHeader     = new int[] {R.string.tableheader_linenum, R.string.text_docdate, R.string.text_docnum, R.string.text_docstatus};
    private final String[] stringAttr   = new String[] {"docdate", "docnum", "status"};

    public AssetMoveoutFragment () {
        super (AssetViewClickListener.class);
        this.layoutId = R.layout.fragment_assetmoveout;
        this.viewIds = new int[] {
        };
    }

    private void initData (View inflatedView) {
        Map<String, String> headers = new HashMap<>();
        headers.put(AppConstants.HEADER_AUTHORIZATION, this.getAppActivity().getAppAuthorization());
        headers.put(AppConstants.HEADER_ACCEPT, AppConstants.CONTENT_JSON);
        headers.put(AppConstants.HEADER_CONTENT_TYPE, AppConstants.CONTENT_JSON.concat("; charset='utf-8'"));

        JSONObject jsonParams   = new JSONObject();
        JSONObject jsonTransmit = new JSONObject();
        try {
            jsonParams.put(AppConstants.REQ_TRIGGER, "moveout-documents");
            jsonTransmit.put(AppConstants.REQ_LOGGEDOUSR, this.getAppActivity().getLoggedOusrID());
            jsonParams.put(AppConstants.REQ_TRANSMIT, jsonTransmit);
        } catch (JSONException exception) {
            Log.e("jsonparam_error", exception.getMessage());
        }

        AppRequestController<JSONObject> requestController = new JSONRequestController(this.getAppActivity(), AppConstants.REQUEST_URL,
                AssetRequestModel.class, jsonParams, headers);
        requestController.setListener(this);
        requestController.setErrorListener(this);
        requestController.execute();
    }

    private void loadTableData (JSONArray mvosList, JSONArray docStats) throws JSONException {
        TableLayout tableLayout = this.getView().findViewById(R.id.table_assetmo);
        TableRow headerRow = (TableRow) tableLayout.getChildAt(0);
        int colCount = headerRow.getChildCount();
        if (mvosList.length() == 0) {
            TableRow emptyRow = new TableRow(this.getAppActivity());
            TableRow.LayoutParams layoutParams = new TableRow.LayoutParams();
            layoutParams.span = colCount;
            TextView noDataCell = this.createTableCell();
            noDataCell.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            noDataCell.setText(R.string.text_nodatafound);
            emptyRow.addView(noDataCell, layoutParams);
            tableLayout.addView(emptyRow);
        } else
            for (int idx = 0; idx < mvosList.length(); idx++) {
                TableRow tableRow = new TableRow(this.getAppActivity());
                JSONObject cellData = mvosList.getJSONObject(idx);

                TextView tableCell  = this.createTableCell();
                tableCell.setText(String.valueOf (idx + 1));
                tableCell.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                tableRow.addView(tableCell);

                for (String cellAttr : this.stringAttr) {
                    tableCell = this.createTableCell();
                    tableCell.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    if (cellAttr.compareTo("status") == 0) {
                        int status = cellData.getInt(cellAttr);
                        tableRow.setBackgroundColor(Helper.getDocumentStatusColor(this.getAppActivity(), status));
                        String docStatus = docStats.getString (cellData.getInt(cellAttr));
                        tableCell.setText(docStatus);

                        if (status != 1) {
                            tableCell.setTextColor(Color.WHITE);
                            for (int childAt = 0; childAt < tableRow.getChildCount(); childAt++) {
                                TextView childCell = (TextView) tableRow.getChildAt(childAt);
                                childCell.setTextColor(Color.WHITE);
                            }
                        }
                    } else tableCell.setText(cellData.getString(cellAttr));
                    tableRow.addView(tableCell);
                }
                this.setViewClickListener(tableRow);
                tableLayout.addView(tableRow);
            }
    }

    private void loadTableHeader() {
        TableLayout mvosTable = this.getView().findViewById(R.id.table_assetmo);
        mvosTable.setPadding(10, 10, 10, 10);
        TableRow trow = new TableRow(this.getContext());
        for (int headIdx : this.indexHeader) {
            TextView tv_header = this.createTableCell();
            tv_header.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tv_header.setText(headIdx);
            tv_header.setTypeface(null, Typeface.BOLD);
            trow.addView(tv_header);
        }
        mvosTable.addView(trow);
    }

    @Override
    protected void initViews(View inflatedView, @Nullable Bundle savedInstanceState) {
        super.initViews(inflatedView, savedInstanceState);
        DisplayMetrics dMetrics = new DisplayMetrics();
        this.getAppActivity().getWindowManager().getDefaultDisplay().getMetrics(dMetrics);
        int displayWidth = dMetrics.widthPixels + (dMetrics.widthPixels * 30 / 100);

        TableLayout tableLayout = inflatedView.findViewById(R.id.table_assetmo);
        tableLayout.getLayoutParams().width     = displayWidth;
        tableLayout.getLayoutParams().height    = ViewGroup.LayoutParams.MATCH_PARENT;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        TableLayout layout = this.getView().findViewById(R.id.table_assetmo);
        layout.removeAllViews();
        this.initData(this.getView());
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("volley_err", "Server connection error!");
        Toast.makeText(this.getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        int responseStatus = 400;
        try {
            responseStatus = response.getInt(AppConstants.RESPONSE_STATUS);
        } catch (JSONException exception) {
            Log.e("resp_error", exception.getMessage());
        }

        if (responseStatus != AppConstants.RESPONSE_OK) Log.i("ServerError", "response status = " + responseStatus);
        else {
            String encodedMessages = "";
            try {
                encodedMessages = response.getString(AppConstants.RESPONSE_MESSAGE);
                String messages = new String (Base64.decode(encodedMessages, Base64.NO_WRAP), StandardCharsets.UTF_8);

                JSONObject responseMessages = new JSONObject(messages);
                JSONArray moveOutList = responseMessages.getJSONArray("mvosList");
                JSONArray docstats = responseMessages.getJSONArray("docStats");

                this.loadTableHeader();
                this.loadTableData(moveOutList, docstats);
            } catch (JSONException exception) {
                Log.e("respmsg_error", exception.getMessage());
            }
        }
    }
}
