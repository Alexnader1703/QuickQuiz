package com.example.quickquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.MessageFormat;

public class Registration extends AppCompatActivity {
    private String sqlQuery;
    ServerCommunication serverConnector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Button but= findViewById(R.id.button2);

        // Получаем экземпляр ServerCommunication
        SharedPreferences sharedPreferences = getSharedPreferences("MyApp", MODE_PRIVATE);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText nick= findViewById(R.id.editTextNickName);
                EditText log= findViewById(R.id.editTextLogin);
                EditText pas= findViewById(R.id.editTextPassword);
                sqlQuery = "INSERT INTO users (login, pass, nickname) VALUES ('"+log.getText()+"', '"+ pas.getText()+"', '"+nick.getText()+"');";
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            serverConnector = ServerCommunication.getInstance();
                            serverConnector.sendMessage(sqlQuery);
                            Log.d("Регистрация", "Успешная Регистрация");
                            // Сохранить состояние входа в систему
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean("loggedIn", true);
                            editor.putString("login", nick.getText().toString());
                            editor.putInt("Литература", 0);
                            editor.putInt("Музыка", 0);
                            editor.putInt("Игры", 0);
                            editor.putInt("Спорт", 0);
                            editor.putInt("Политика", 0);
                            editor.putInt("Страны", 0);
                            editor.putInt("Аниме", 0);
                            editor.putInt("Древняя русь", 0);
                            editor.putInt("Космос", 0);
                            editor.putInt("Хип-хоп", 0);
                            editor.putInt("Фильмы", 0);
                            editor.putInt("Животные", 0);
                            editor.apply();
                            // Переход на следующую активность
                            Intent intent = new Intent(Registration.this, MainActivity.class);
                            startActivity(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }
}
