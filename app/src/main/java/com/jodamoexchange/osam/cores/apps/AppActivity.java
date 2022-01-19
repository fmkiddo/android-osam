package com.jodamoexchange.osam.cores.apps;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public abstract class AppActivity extends AppCompatActivity {

    static final String KEY_MAIN_PROCESS_PID = "fmkiddo_main_process_pid";

    private AppMutablePreferenceController prefControl;
    protected Class<? extends AppMutablePreferenceController> prefControlClass;
    protected AppViewOnClickListener onClickListener;

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

    protected void doRebirth (Class<? extends AppActivity> activityClass) {
        Intent mStartActivity = new Intent(this, activityClass);
        int mPendingIntentId = Process.myPid();
        PendingIntent mPendingIntent = PendingIntent.getActivity(this, mPendingIntentId, mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager mgr = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
        Runtime.getRuntime().exit(0);
    }

    protected void startNewActivity (Class<? extends AppActivity> activityClass) {
        Intent nextActivity = new Intent(this, activityClass);
        this.startActivity(nextActivity);
        this.finish();
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

    protected AppMutablePreferenceController getPreferenceController () {
        return this.prefControl;
    }
}
