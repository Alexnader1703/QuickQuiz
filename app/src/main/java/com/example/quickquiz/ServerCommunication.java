package com.example.quickquiz;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ServerCommunication {
    private static final String SERVER_IP = "82.179.140.18";
    private static final int SERVER_PORT = 45112;

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    // Добавляем статическую переменную instance
    private static ServerCommunication instance;

    // Делаем конструктор приватным
    private ServerCommunication() {
        new ConnectTask().execute();
    }

    // Добавляем статический метод getInstance
    public static synchronized ServerCommunication getInstance() {
        if (instance == null) {
            instance = new ServerCommunication();
        }
        return instance;
    }
    public void sendMessage(String message) {
        // Отправка сообщения выполняется по запросу
        new SendMessageTask().execute(message);
    }

    public void receiveMessage() {
        // Получение сообщения выполняется по запросу
        new ReceiveMessageTask().execute();
    }

    public void closeConnection() {
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class ConnectTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                // Подключение к серверу
                socket = new Socket(SERVER_IP, SERVER_PORT);
                out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (UnknownHostException e) {
                Log.e("ConnectTask", "UnknownHostException: " + e.getMessage());
                e.printStackTrace();
            } catch (IOException e) {
                Log.e("ConnectTask", "IOException: " + e.getMessage());
                e.printStackTrace();
            }
            return null;
        }
    }

    private class SendMessageTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... messages) {
            try {
                // Отправка сообщения
                out.println(messages[0]);
            } catch (Exception e) {
                Log.e("SendMessageTask", "Exception: " + e.getMessage());
                e.printStackTrace();
            }
            return null;
        }
    }
    // Добавляем интерфейс для обратного вызова
    public interface MessageListener {
        void onMessageReceived(String message);
    }

    private MessageListener messageListener;

    public void setMessageListener(MessageListener listener) {
        this.messageListener = listener;
    }
    private class ReceiveMessageTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            try {
                // Получение сообщения
                return in.readLine();
            } catch (IOException e) {
                Log.e("ReceiveMessageTask", "IOException: " + e.getMessage());
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String message) {
            // Вызываем обратный вызов, когда сообщение получено
            if (messageListener != null) {
                messageListener.onMessageReceived(message);
            }
        }
    }
}
