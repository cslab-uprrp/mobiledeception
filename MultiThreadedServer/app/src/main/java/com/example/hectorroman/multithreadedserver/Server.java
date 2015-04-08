package com.example.hectorroman.multithreadedserver;

import android.os.Handler;
import android.widget.Toast;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static android.widget.Toast.*;
import static android.widget.Toast.makeText;

/**
 * Created by Hector Roman on 3/15/2015.
 */
public class Server extends Thread {
    ServerSocket serverSocket;
    serverService object;


    public Server(serverService obj) {
        object = obj;
    }

    @Override
    public void run() {
        super.run();
        try{
            serverSocket = new ServerSocket(8000);
        }
        catch (Exception a){
//            object.showToast("Error Binding Socket");
//            Toast.makeText(object.getApplicationContext(), "Error Binding Socket", LENGTH_LONG).show();
        }

        while(object.serverOn){
            try {
                Socket clientSocket = serverSocket.accept();
                clientThread newClient = new clientThread(clientSocket, object);
                newClient.start();
            }
            catch(Exception b){
//                object.showToast("Error Connecting To Client");
//                Toast.makeText(object.getApplicationContext(), "Error Connecting To Client", LENGTH_LONG).show();
            }

        }
        try {
            serverSocket.close();
        } catch (Exception ex) {
//            object.showToast("Error Closing Server Socket");
//            Toast.makeText(object.getApplicationContext(),"Error Closing Server Socket", LENGTH_LONG).show();
        }
    }
}
