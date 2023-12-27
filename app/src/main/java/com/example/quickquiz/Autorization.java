package com.example.quickquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Autorization extends AppCompatActivity {
    private String sqlQuery;
    private String result;
    private ProgressDialog progressDialog;
    ServerCommunication serverConnector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autorization);

        Button loginButton = findViewById(R.id.login);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog = ProgressDialog.show(Autorization.this, "Please wait...", "Logging in...");
                progressDialog.dismiss();
                EditText log = findViewById(R.id.editTextLogin);
                EditText pas = findViewById(R.id.editTextPassword);
                sqlQuery = "login:SELECT * FROM users WHERE login = '" + log.getText() + "' AND pass = '" + pas.getText() + "';";

                // Создаем новый поток для отправки запроса на сервер и получения ответа
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            serverConnector = ServerCommunication.getInstance();
                            serverConnector.sendMessage(sqlQuery);
                            String response = serverConnector.receiveMessage();
                            SharedPreferences sharedPreferences = getSharedPreferences("MyApp", MODE_PRIVATE);
                            // Проверяем ответ сервера
                            if (response.equals("true")) {

                                Log.d("Авторизация", "Успешная авторизация");
                                // Сохранить состояние входа в систему
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putBoolean("loggedIn", true);
                                editor.putString("login", log.getText().toString());
                                editor.apply();
                                // Переход на следующую активность
                                Intent intent = new Intent(Autorization.this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                Log.d("Авторизация", "Авторизация не удалась");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });

        Button registerButton = findViewById(R.id.regis);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Создаем Intent для перехода на активити регистрации
                Intent registrationIntent = new Intent(Autorization.this, Registration.class);
                startActivity(registrationIntent);
            }
        });
    }
}
