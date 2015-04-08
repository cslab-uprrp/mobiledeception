package com.example.hectorroman.multithreadedserver;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

public class serverService extends Service  {
    public boolean serverOn = true;

    public serverService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "Server Service Created", Toast.LENGTH_LONG).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Server Started", Toast.LENGTH_LONG).show();
        Server server = new Server(this);
        server.start();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        serverOn = false;
        Toast.makeText(this, "Server Stopped", Toast.LENGTH_LONG).show();
    }

    public void Toast(double val){
        Toast.makeText(this,"Valor: " + String.valueOf(val),Toast.LENGTH_LONG).show();
    }

}
