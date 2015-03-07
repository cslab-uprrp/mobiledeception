package com.chamas.luis.server;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class MainActivity extends ActionBarActivity {
    private Thread thread;
    private ServerSocket serverSocket;
    private TextView sensorInfo;
    private Runnable abc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorInfo = (TextView)findViewById(R.id.textView);



        thread = new Thread(){
          @Override
          public void run(){
              try {
                  serverSocket = new ServerSocket(8008);
                  serverSocket.setSoTimeout(100000);
                  Toast.makeText(MainActivity.this, "Waiting for client on port.. " + String.valueOf(serverSocket.getLocalPort()), Toast.LENGTH_LONG).show();
                  Socket server = serverSocket.accept();
                  DataInputStream in = new DataInputStream(server.getInputStream());
                  sensorInfo.setText(String.valueOf(in.readDouble()));
                  server.close();
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
        };
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void startServer(View view) {
        thread.run();
    }
}
