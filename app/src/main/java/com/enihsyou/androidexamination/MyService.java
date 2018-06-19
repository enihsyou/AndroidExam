package com.enihsyou.androidexamination;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {

    private String TAG = MyService.class.getSimpleName();

    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "Service created");
    }

    @Override
    public int onStartCommand(final Intent intent, final int flags, final int startId) {
        Log.i(TAG, "Service started");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "Service Destroy");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "Bind service");
        return new Binder();
    }
}
