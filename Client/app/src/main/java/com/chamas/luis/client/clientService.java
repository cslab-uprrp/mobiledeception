package com.chamas.luis.client;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

/**
 * Created by Luis on 3/17/2015.
 */
public class clientService extends Service {
    double sense;
    String hello="nope";
//    String ip;

    MainActivity qwe;

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "Client Service Created", Toast.LENGTH_SHORT).show();
//        Intent activityThatCalled=null;
//        ip = qwe.ip;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Client Started", Toast.LENGTH_SHORT).show();
        Client client = new Client(this);
        client.start();
        return START_STICKY;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Toast.makeText(this, "sensor value: " + String.valueOf(sense), Toast.LENGTH_LONG).show();
    }

    public void setSense(double num){

//        if(num > sense || num < sense){
//           Toast.makeText(this, String.valueOf(num) + " and " + String.valueOf(sense), Toast.LENGTH_SHORT).show();
//        }
        sense = num;
    }

    public void setHello(String yo){
        hello = yo;
    }

}
