package com.jodamoexchange.osam.cores.app.activities.ui.fragments;

import com.jodamoexchange.osam.R;
import com.jodamoexchange.osam.cores.app.activities.listeners.AssetViewClickListener;
import com.jodamoexchange.osam.cores.apps.AppFragment;

public class AssetMoveinDetailFragment extends AppFragment {

    public AssetMoveinDetailFragment () {
        super (AssetViewClickListener.class);
        this.layoutId   = R.layout.fragment_assetmoveindetail;
        this.viewIds    = new int[] {};
    }
}
