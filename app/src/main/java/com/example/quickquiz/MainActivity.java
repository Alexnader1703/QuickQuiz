package com.example.quickquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.quickquiz.ServerConnection;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        responseTextView = findViewById(R.id.responseTextView);

        new MyAsyncTask().execute();

        Room_List game_rooms = new Room_List();
        SetNewFrag(game_rooms);
    }
    private void SetNewFrag(Fragment fragment){
        FragmentTransaction ft= getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.mainWindow,fragment);
        ft.addToBackStack(null);
        ft.commit();
    }
    public void BtnGame(View v){
        Room_List game_rooms = new Room_List();
        SetNewFrag(game_rooms);
    }

    private class MyAsyncTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            try {
                Socket socket = new Socket("82.179.140.18", 2041);
                OutputStream outputStream = socket.getOutputStream();

                String message = "Салам Брат";
                outputStream.write(message.getBytes());
                outputStream.flush();

                socket.close();


                return "Успешное подключение";
            } catch (IOException e) {
                e.printStackTrace();
                return "Ошибка подключения: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {

            responseTextView.setText("Server: " + result);


        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }
}