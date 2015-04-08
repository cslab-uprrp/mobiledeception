package com.chamas.luis.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by Luis on 3/17/2015.
 */
public class Client extends Thread {
    clientService object;

    public Client(clientService obj){
        object = obj;
    }

    @Override
    public void run(){
        try{
            Socket clientSocket = new Socket(object.ip, 8000);
            OutputStream outToServer = clientSocket.getOutputStream();
            DataOutputStream out = new DataOutputStream(outToServer);
           // out.writeDouble(100);
            out.writeUTF("accelx");
            InputStream inFromServer = clientSocket.getInputStream();
            DataInputStream in = new DataInputStream(inFromServer);
            //String valor = in.readUTF();
            //object.setHello(valor);
            //object.hello = valor;
            double valor = in.readDouble();
            object.sense = valor;
            //object.showVal(valor);
            //object.setSense(valor);
            clientSocket.close();

        }catch (IOException e){
            e.printStackTrace();
        }
    }


}
