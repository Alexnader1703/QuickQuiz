package com.example.quickquiz;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class ServerCommunication extends AsyncTask<Void, Void, String> {
    private static final String TAG = "ServerTask";
    private String serverAddress;
    private int serverPort;
    private String messageToSend;

    public ServerCommunication(String serverAddress, int serverPort, String messageToSend) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        this.messageToSend = messageToSend;
    }

    @Override
    protected String doInBackground(Void... params) {
        try {
            Socket socket = new Socket(serverAddress, serverPort);

            // Получаем потоки ввода и вывода
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            OutputStream outputStream = socket.getOutputStream();

            // Отправляем сообщение серверу
            outputStream.write(messageToSend.getBytes());

            // Ждем ответа от сервера
            String response = reader.readLine();

            // Закрываем соединение
            socket.close();

            return response;
        } catch (UnknownHostException e) {
            Log.e(TAG, "UnknownHostException: " + e.getMessage());
            return "Error: UnknownHostException";
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
            return "Error: IOException";
        }
    }

    @Override
    protected void onPostExecute(String result) {
        // Обработка ответа от сервера
        Log.d(TAG, "Server response: " + result);
        // Здесь вы можете обновить пользовательский интерфейс или выполнить другие действия с полученными данными.
    }
}