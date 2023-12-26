package com.example.quickquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Autorization extends AppCompatActivity {
    private String sqlQuery;
    private String result;
    private ProgressDialog progressDialog;
    private ServerCommunication serverCommunication;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autorization);

        Button loginButton=findViewById(R.id.login);
        // Получаем экземпляр ServerCommunication
        serverCommunication = ServerCommunication.getInstance();
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText log= findViewById(R.id.editTextLogin);
                EditText pas= findViewById(R.id.editTextPassword);
                sqlQuery = "login:SELECT * FROM users WHERE login = '"+log.getText()+"' AND pass = '"+ pas.getText()+"';";
                //progressDialog = ProgressDialog.show(Autorization.this, "Please wait...", "Logging in...");
                serverCommunication.setMessageListener(new ServerCommunication.MessageListener() {
                    @Override
                    public void onMessageReceived(String message) {
                        // Скрыть индикатор загрузки
                        //progressDialog.dismiss();

                        // Добавляем логирование
                        Log.d("Autorization", "Received message from server: " + message);

                        // Обработка сообщения от сервера
                        if (message != null && message.equals("true")) {
                            Log.d("Autorization", "Login successful.");

                            // Сохранить состояние входа в систему
                            SharedPreferences prefs = getSharedPreferences("MyApp", MODE_PRIVATE);
                            prefs.edit().putBoolean("loggedIn", true).apply();

                            // Переход на следующую активность
                            Intent intent = new Intent(Autorization.this, MainActivity.class);
                            startActivity(intent);

                        } else {
                            Log.d("Autorization", "Login failed.");

                            // Показать сообщение об ошибке
                            Toast.makeText(Autorization.this, "Login failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                serverCommunication.sendMessage(sqlQuery);


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