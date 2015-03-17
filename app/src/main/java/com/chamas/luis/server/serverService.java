package com.chamas.luis.server;

/**
 * Created by Luis on 3/17/2015.
 */
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class serverService extends Service {
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
}
