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
import com.jodamoexchange.osam.cores.apps.controllers.AppHttpRequestController;
import com.jodamoexchange.osam.cores.apps.controllers.JSONRequestController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class AssetMoveinFragment extends AppFragment implements Response.Listener<JSONObject>,
                                                                Response.ErrorListener {

    private final int[] indexHeader   = new int[] {R.string.tableheader_linenum, R.string.text_docdate, R.string.text_docnum, R.string.text_docstatus};
    private final String[] stringAttr = new String[] {"docdate", "docnum", "status"};

    public AssetMoveinFragment () {
        super(AssetViewClickListener.class);
        this.layoutId = R.layout.fragment_assetmovein;
        this.viewIds = new int[] {
        };
    }

    private void loadTableData (JSONArray mvisList, JSONArray docStats) throws JSONException {
        TableLayout mvisTable   = this.getView().findViewById(R.id.table_assetmi);
        TableRow headerRow      = (TableRow) mvisTable.getChildAt(0);
        int colCount    = headerRow.getChildCount();
        if (mvisList.length() == 0) {
            TableRow emptyRow = new TableRow(this.getAppActivity());
            TableRow.LayoutParams layoutParams = new TableRow.LayoutParams();
            layoutParams.span = colCount;
            TextView noDataCell = this.createTableCell();
            noDataCell.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            noDataCell.setText(R.string.text_nodatafound);
            emptyRow.addView(noDataCell, layoutParams);
            mvisTable.addView(emptyRow);
        } else
            for (int index = 0; index < mvisList.length(); index++) {
                TableRow newRow = new TableRow(this.getAppActivity());

                JSONObject dataRow = mvisList.getJSONObject(index);
                TextView cell   = this.createTableCell();

                cell.setText(String.valueOf (index+1));
                cell.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                newRow.addView(cell);

                for (String attr : this.stringAttr) {
                    cell = this.createTableCell();
                    cell.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    if (attr.compareTo("status") == 0) {
                        int status = dataRow.getInt(attr);
                        newRow.setBackgroundColor(Helper.getDocumentStatusColor(this.getAppActivity(), status));
                        String docStatus = docStats.getString(status);
                        cell.setText(docStatus);
                        if (status != 1) {
                            cell.setTextColor(Color.WHITE);
                            for (int childAt = 0; childAt < newRow.getChildCount(); childAt++) {
                                TextView childCell = (TextView) newRow.getChildAt(childAt);
                                childCell.setTextColor(Color.WHITE);
                            }
                        }
                    } else cell.setText(dataRow.getString(attr));
                    newRow.addView(cell);
                }
                this.setViewClickListener(newRow);
                mvisTable.addView(newRow);
            }
    }

    private void loadTableHeader () {
        TableLayout mvisTable = this.getView().findViewById(R.id.table_assetmi);
        TableRow trow = new TableRow(this.getAppActivity());
        for (int headIdx : this.indexHeader) {
            TextView tv_header = this.createTableCell();
            tv_header.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tv_header.setText(headIdx);
            tv_header.setTypeface(null, Typeface.BOLD);
            trow.addView(tv_header);
        }
        mvisTable.addView(trow);
    }

    private void initData (View inflatedView) {
        Map<String, String> header = new HashMap<>();
        header.put(AppConstants.HEADER_AUTHORIZATION, this.getAppActivity().getAppAuthorization());
        header.put(AppConstants.HEADER_CONTENT_TYPE, AppConstants.CONTENT_JSON);
        header.put(AppConstants.HEADER_ACCEPT, AppConstants.CONTENT_JSON);

        JSONObject params   = new JSONObject();
        JSONObject transmit = new JSONObject();

        try {
            transmit.put(AppConstants.REQ_LOGGEDOUSR, this.getAppActivity().getLoggedOusrID());
            params.put(AppConstants.REQ_TRANSMIT, transmit);
            params.put(AppConstants.REQ_TRIGGER, "movein-documents");
        } catch (JSONException exception) {
            Log.e("jsonparam_error", exception.getMessage());
        }

        AppHttpRequestController<JSONObject> requestController = new JSONRequestController(this.getAppActivity(), AppConstants.REQUEST_URL, AssetRequestModel.class, params, header);
        requestController.setListener(this);
        requestController.setErrorListener(this);
        requestController.execute();
    }

    @Override
    protected void initViews(View inflatedView, @Nullable Bundle savedInstanceState) {
        super.initViews(inflatedView, savedInstanceState);
        DisplayMetrics dMetrics = new DisplayMetrics();
        this.getAppActivity().getWindowManager().getDefaultDisplay().getMetrics(dMetrics);
        int displayWidth = dMetrics.widthPixels + (dMetrics.widthPixels * 30 / 100);

        TableLayout tableLayout = inflatedView.findViewById(R.id.table_assetmi);
        tableLayout.getLayoutParams().width     = displayWidth;
        tableLayout.getLayoutParams().height    = ViewGroup.LayoutParams.MATCH_PARENT;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        TableLayout layout = this.getView().findViewById(R.id.table_assetmi);
        layout.removeAllViews();
        this.initData(this.getView());
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
                JSONArray moveInList = responseMessages.getJSONArray("mvisList");
                JSONArray docstats = responseMessages.getJSONArray("docStats");

                this.loadTableHeader();
                this.loadTableData(moveInList, docstats);
            } catch (JSONException exception) {
                Log.e("respmsg_error", exception.getMessage());
            }
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("volley_err", "Server connection error!");
        Toast.makeText(this.getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
    }
}
