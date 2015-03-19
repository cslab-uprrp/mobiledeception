package com.chamas.luis.server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

/**
 * Created by Luis on 3/19/2015.
 */
public class serverService extends Service {
    public boolean serverOn = true;
    public serverService(){

    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        Toast.makeText(this,"Server Service Created", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        Toast.makeText(this, "Server Started", Toast.LENGTH_SHORT).show();
        Server server = new Server(this);
        server.start();
        return START_STICKY;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        serverOn = false;
        Toast.makeText(this, "Server Stopped", Toast.LENGTH_SHORT).show();
    }
}
