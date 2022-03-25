package com.jodamoexchange.osam.cores.apps;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.util.Base64;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.jodamoexchange.osam.cores.apps.listeners.AppViewOnClickListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;

public abstract class AppActivity extends AppCompatActivity {

    static final String KEY_MAIN_PROCESS_PID = "fmkiddo_main_process_pid";

    private AppMutablePreferenceController prefControl;
    protected Class<? extends AppMutablePreferenceController> prefControlClass;
    protected AppViewOnClickListener onClickListener;

    public AppFragment getVisibleFragment () {
        FragmentManager manager = this.getSupportFragmentManager();
        AppFragment fragment = null;
        for (Fragment fragment1 : manager.getFragments())
            if (!fragment1.isHidden()) {
                fragment = (AppFragment) fragment1;
                break;
            }
        return fragment;
    }

    public void swapFragment (String fragmentName) {
        this.swapFragment(fragmentName, new Bundle());
    }

    public void swapFragment (String fragmentName, Bundle arguments) {}

    public AppPreferenceController getPreferencesController () {
        return this.getMutablePreferencesController();
    }

    public int getLoggedOusrID () {
        int userid = -1;
        String loginKey = this.getMutablePreferencesController().getString(AppConstants.TOKEN_LOGIN_KEY);
        String decoded  = new String (Base64.decode(loginKey, Base64.NO_WRAP), StandardCharsets.UTF_8);
        JSONObject loginJson = null;
        try {
            loginJson = new JSONObject(decoded);
            userid  = loginJson.getInt(AppConstants.LOGIN_KEY_USERID);
        } catch (JSONException exception) {}
        return userid;
    }

    public String getAppAuthorization () {
        String token        = this.getMutablePreferencesController().getString(AppConstants.TOKEN_LICENSE_KEY);
        byte[] bytesAuth    = token.getBytes(StandardCharsets.UTF_8);
        String auths        = new String (Base64.encodeToString(bytesAuth, Base64.NO_WRAP));
        return "Basic ".concat(auths);
    }

    public void doRebirth (Class<? extends AppActivity> activityClass) {
        Intent mStartActivity = new Intent(this, activityClass);
        int mPendingIntentId = Process.myPid();
        PendingIntent mPendingIntent = PendingIntent.getActivity(this, mPendingIntentId, mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager mgr = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
        Runtime.getRuntime().exit(0);
    }

    public void startNewActivity (Class<? extends AppActivity> activityClass) {
        Intent nextActivity = new Intent(this, activityClass);
        this.startActivity(nextActivity);
        this.finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        this.initCreateApp(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.initStartApp();
    }

    protected void initCreateApp (@Nullable Bundle savedInstanceState) {
        try {
            this.prefControl = this.prefControlClass.getConstructor(Context.class).newInstance(this);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    protected void initStartApp () {}

    protected AppMutablePreferenceController getMutablePreferencesController() {
        return this.prefControl;
    }
}
