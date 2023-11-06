package com.example.quickquiz;

import android.content.Context;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
public class ServerConnection {
    private static final String SERVER_IP = "povt-cluster.tstu.tver.ru";
    private static final int SERVER_PORT = 2028;

    private Socket socket;
    private OutputStream outputStream;
    public boolean Connect;
    public ServerConnection() {
        try {
            socket = new Socket(SERVER_IP, SERVER_PORT);
            outputStream = socket.getOutputStream();
            Connect=socket.isConnected();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendData(String data, Context con) {
        try {
            outputStream.write(data.getBytes());
            outputStream.flush();
        } catch (IOException e) {
            Toast.makeText(con,"СаламБратья",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}