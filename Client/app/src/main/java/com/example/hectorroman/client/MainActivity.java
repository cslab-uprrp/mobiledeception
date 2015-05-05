package com.example.hectorroman.client;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.os.IBinder;
import android.content.Context;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.widget.Toast;



import org.w3c.dom.Text;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;


public class MainActivity extends ActionBarActivity {
    public String serverAddress;
    public String sensorReq;
    public Handler handlerMain;
    TextView textLabel;

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        View v = getCurrentFocus();
        boolean ret = super.dispatchTouchEvent(event);

        if (v instanceof EditText) {
            View w = getCurrentFocus();
            int scrcoords[] = new int[2];
            w.getLocationOnScreen(scrcoords);
            float x = event.getRawX() + w.getLeft() - scrcoords[0];
            float y = event.getRawY() + w.getTop() - scrcoords[1];

            Log.d("Activity", "Touch event " + event.getRawX() + "," + event.getRawY() + " " + x + "," + y + " rect " + w.getLeft() + "," + w.getTop() + "," + w.getRight() + "," + w.getBottom() + " coords " + scrcoords[0] + "," + scrcoords[1]);
            if (event.getAction() == MotionEvent.ACTION_UP && (x < w.getLeft() || x >= w.getRight() || y < w.getTop() || y > w.getBottom()) ) {

                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
            }
        }
        return ret;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textLabel = (TextView)findViewById(R.id.label);

        handlerMain = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle bun = msg.getData();
                double sensorValue = bun.getDouble("sensor");
                textLabel.setText(String.valueOf(sensorValue));

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



    public void startLis(View view) {
        EditText IP = (EditText)findViewById(R.id.IP);
        EditText SensorR = (EditText)findViewById(R.id.SensorR);
        serverAddress = IP.getText().toString();
        sensorReq = SensorR.getText().toString();


        Toast.makeText(this, "Starting Sensor Request Thread", Toast.LENGTH_LONG).show();

        clientThread threadCon = new clientThread();
        threadCon.start();
    }



    public class clientThread extends Thread {

        Socket clientSocket;
        public clientThread(){

        }

        @Override
        public void run() {
            super.run();

            try {
                clientSocket = new Socket(serverAddress, 8000);
                DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
                out.writeUTF(sensorReq);

                DataInputStream in = new DataInputStream(clientSocket.getInputStream());
                double valor  = in.readDouble();

                Bundle msgBundle = new Bundle();
                msgBundle.putDouble("sensor", valor);
                Message msg = new Message();
                msg.setData(msgBundle);
                handlerMain.sendMessage(msg);

            }
            catch(Exception e)
            {
                Bundle msgBundle = new Bundle();
                msgBundle.putInt("error", 1);
                Message msg = new Message();
                msg.setData(msgBundle);
                handlerMain.sendMessage(msg);
            }
        }
    }








}

