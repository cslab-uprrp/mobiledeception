package com.example.hectorroman.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/**
 * Created by Hector Roman on 3/25/2015.
 */
public class clientThread extends Thread {
    Handler mainHandler;
    String server;
    String sensorRequest;

    Socket clientSocket;
    public clientThread(String serverIP, String request, Handler mHand){
        server = serverIP;
        sensorRequest = request;
        mainHandler = mHand;
    }

    @Override
    public void run() {
        super.run();

        try {
            clientSocket = new Socket(server, 8000);
            DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
            out.writeUTF(sensorRequest);

            DataInputStream in = new DataInputStream(clientSocket.getInputStream());
            double valor  = in.readDouble();

            Bundle msgBundle = new Bundle();
            msgBundle.putDouble("sensor", valor);
            Message msg = new Message();
            msg.setData(msgBundle);
            mainHandler.sendMessage(msg);

        }
        catch(Exception e)
        {

        }
    }
}
