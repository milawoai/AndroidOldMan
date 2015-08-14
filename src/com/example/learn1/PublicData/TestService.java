package com.example.learn1.PublicData;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by a0153-00401 on 15/8/13.
 */
public class TestService extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent,int flags,int startId)
    {
       return super.onStartCommand(intent, flags,startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
