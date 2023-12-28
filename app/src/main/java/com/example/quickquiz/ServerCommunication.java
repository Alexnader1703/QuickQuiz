package com.example.quickquiz;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerCommunication {
    private static ServerCommunication instance = null;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    private static final String SERVER_ADDRESS = "82.179.140.18";
    private static final int SERVER_PORT = 45103;

    private ServerCommunication() throws Exception {
        socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public static synchronized ServerCommunication getInstance() throws Exception {
        if (instance == null) {
            instance = new ServerCommunication();
        }
        return instance;
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    public String receiveMessage() throws Exception {
        return in.readLine();
    }

    public void close() throws Exception {
        in.close();
        out.close();
        socket.close();
    }
}

