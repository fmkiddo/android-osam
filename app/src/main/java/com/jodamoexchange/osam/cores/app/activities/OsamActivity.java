package com.jodamoexchange.osam.cores.app.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.jodamoexchange.osam.R;
import com.jodamoexchange.osam.cores.app.OsamAppActivity;
import com.jodamoexchange.osam.cores.app.activities.listeners.dialogs.ExitOnClickListener;
import com.jodamoexchange.osam.cores.app.activities.listeners.dialogs.LogoutOnClickListener;
import com.jodamoexchange.osam.cores.app.activities.listeners.drawer.OsamDrawerListener;
import com.jodamoexchange.osam.cores.app.activities.listeners.drawer.OsamOnChildClickListener;
import com.jodamoexchange.osam.cores.app.activities.listeners.drawer.OsamOnGroupClickListener;
import com.jodamoexchange.osam.cores.app.activities.ui.drawer.OsamMenuExpandableListAdapter;
import com.jodamoexchange.osam.cores.app.activities.ui.fragments.AssetMoveinDetailFragment;
import com.jodamoexchange.osam.cores.app.activities.ui.fragments.AssetMoveinFragment;
import com.jodamoexchange.osam.cores.app.activities.ui.fragments.AssetMoveoutDetailFragment;
import com.jodamoexchange.osam.cores.app.activities.ui.fragments.AssetMoveoutFragment;
import com.jodamoexchange.osam.cores.app.activities.ui.fragments.HomeFragment;
import com.jodamoexchange.osam.cores.app.activities.ui.fragments.SettingsFragment;
import com.jodamoexchange.osam.cores.app.activities.ui.menu.HomeMenu;
import com.jodamoexchange.osam.cores.app.activities.ui.menu.MenuAssetMove;
import com.jodamoexchange.osam.cores.app.activities.ui.menu.MenuAssetReceive;
import com.jodamoexchange.osam.cores.app.activities.ui.menu.MenuGroupAsset;
import com.jodamoexchange.osam.cores.apps.AppConstants;
import com.jodamoexchange.osam.cores.apps.AppFragment;
import com.jodamoexchange.osam.cores.apps.AppMenu;
import com.jodamoexchange.osam.cores.apps.AppMutableExpandableListAdapter;
import com.jodamoexchange.osam.cores.apps.listeners.AppDIOnClickListener;
import com.jodamoexchange.osam.cores.apps.listeners.AppDrawerListener;
import com.jodamoexchange.osam.cores.apps.listeners.AppDrawerOnChildClickListener;
import com.jodamoexchange.osam.cores.apps.listeners.AppDrawerOnGroupClickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OsamActivity extends OsamAppActivity {

    private DrawerLayout drawerLayout;
    private ExpandableListView listView;
    private AppMutableExpandableListAdapter<AppMenu, AppMenu> listViewAdapter;

    @Override
    public void swapFragment(String fragmentName, @Nullable Bundle arguments) {
        FragmentManager manager = this.getSupportFragmentManager();
        Fragment fragment = manager.findFragmentByTag(fragmentName);
        if (arguments != null) fragment.setArguments(arguments);
        FragmentTransaction transaction = manager.beginTransaction();
        for (Fragment fragment1 : manager.getFragments())
            if (fragment1.isVisible()) transaction.hide(fragment1);
        transaction.show(fragment);
        transaction.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        boolean hasVisibleFragment = false;
        FragmentManager manager = this.getSupportFragmentManager();
        for (Fragment fragment : manager.getFragments())
            if (!fragment.isHidden()) {
                hasVisibleFragment = true;
                break;
            }

        if (!hasVisibleFragment) this.swapFragment(HomeFragment.class.getName());
    }

    @Override
    protected void onResume() {
        super.onResume();
        FragmentManager manager = this.getSupportFragmentManager();
        Fragment visibleFragment = null;
        for (Fragment fragment : manager.getFragments())
            if (!fragment.isHidden()) {
                visibleFragment = fragment;
                break;
            }
        if (visibleFragment != null) visibleFragment.onHiddenChanged(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            default:
                break;
            case android.R.id.home:
                this.drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.opt_exit:
                this.askToQuit();
                break;
            case R.id.opt_logout:
                this.askToLogout();
                break;
            case R.id.opt_setting:
                this.swapFragment(SettingsFragment.class.getName());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initCreateApp(@Nullable Bundle savedInstanceState) {
        super.initCreateApp(savedInstanceState);
        this.setContentView(R.layout.activity_osam);
        this.configureMenuList();
        this.configureNavigationDrawer();
        this.configureToolbar();
        this.initFragments();
    }

    private void askToQuit () {
        AppDIOnClickListener exitListener = new ExitOnClickListener();
        exitListener.setApplicationContext(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.title_exit).setMessage(R.string.q_exitapp);
        builder.setPositiveButton(R.string.dbtn_yes, exitListener).setNegativeButton(R.string.dbtn_no, exitListener);
        builder.show();
    }

    private void askToLogout () {
        AppDIOnClickListener logoutListener = new LogoutOnClickListener();
        logoutListener.setApplicationContext(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.title_logout).setMessage(R.string.q_logoutapp);
        builder.setPositiveButton(R.string.dbtn_yes, logoutListener).setNegativeButton(R.string.dbtn_no, logoutListener);
        builder.show();
    }

    private void configureMenuList () {
        this.listViewAdapter = new OsamMenuExpandableListAdapter();
        this.listViewAdapter.setApplicationContext(this);

        AppMenu homeMenu = new HomeMenu();
        AppMenu assetGroupMenu = new MenuGroupAsset();
        AppMenu[] assetGroupChilds = new AppMenu[] {
                new MenuAssetMove(),
                new MenuAssetReceive()
        };

        this.listViewAdapter.addGroup(homeMenu);
        for (AppMenu appMenu : assetGroupChilds) this.listViewAdapter.addChild(assetGroupMenu, appMenu);
    }

    private void configureToolbar () {
        Toolbar toolbar = this.findViewById(R.id.app_toolbar);
        this.setSupportActionBar(toolbar);
        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_drawer);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void configureNavigationDrawer () {
        AppDrawerListener drawerListener = new OsamDrawerListener();
        drawerListener.setApplicationContext(this);

        AppDrawerOnChildClickListener<AppMenu, AppMenu> onChildClickListener = new OsamOnChildClickListener();
        onChildClickListener.setApplicationContext(this);
        onChildClickListener.setListViewAdapter(this.listViewAdapter);

        AppDrawerOnGroupClickListener<AppMenu, AppMenu> onGroupClickListener = new OsamOnGroupClickListener();
        onGroupClickListener.setApplicationContext(this);
        onGroupClickListener.setListViewAdapter(this.listViewAdapter);

        this.drawerLayout = this.findViewById(R.id.osam_drawerlayout);
        this.drawerLayout.addDrawerListener(drawerListener);
        this.listView = this.findViewById(R.id.osam_expandmenu);
        this.listView.setAdapter(this.listViewAdapter);
        this.listView.setOnChildClickListener(onChildClickListener);
        this.listView.setOnGroupClickListener(onGroupClickListener);
    }

    private void initFragments () {
        FragmentManager manager = this.getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        if (manager.getFragments().size() == 0) {
            AppFragment[] appFragments = new AppFragment[]{
                    new HomeFragment(),
                    new SettingsFragment(),
                    new AssetMoveoutFragment(),
                    new AssetMoveinFragment(),
                    new AssetMoveoutDetailFragment(),
                    new AssetMoveinDetailFragment()
            };

            for (AppFragment appFragment : appFragments) {
                transaction.add(R.id.osam_maincontents, appFragment, appFragment.getClass().getName());
                transaction.hide(appFragment);
            }
        }

        transaction.commit();
    }
}
