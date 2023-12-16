package com.example.quickquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.quickquiz.ServerCommunication;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {

    private TextView responseTextView;
    private ServerCommunication serverCommunication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String serverAddress = "82.179.140.18"; // Замените на реальный IP вашего сервера
        int serverPort = 45103; // Замените YOUR_SERVER_PORT на реальный порт вашего сервера
        String messageToSend = "Hello from Android!";

        ServerCommunication serverTask = new ServerCommunication(serverAddress, serverPort, messageToSend);
        serverTask.execute();

        Room_List game_rooms = new Room_List();
        SetNewFrag(game_rooms);
    }



    private void SetNewFrag(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.mainWindow, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    public void BtnGame(View v) {
        Room_List game_rooms = new Room_List();
        SetNewFrag(game_rooms);
    }



}