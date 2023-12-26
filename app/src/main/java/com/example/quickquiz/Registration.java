package com.example.quickquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.MessageFormat;

public class Registration extends AppCompatActivity {
    private String sqlQuery;
    private ServerCommunication serverCommunication;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Button but= findViewById(R.id.button2);

        // Получаем экземпляр ServerCommunication
        serverCommunication = ServerCommunication.getInstance();
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText nick= findViewById(R.id.editTextNickName);
                EditText log= findViewById(R.id.editTextLogin);
                EditText pas= findViewById(R.id.editTextPassword);
                sqlQuery = "INSERT INTO users (login, pass, nickname) VALUES ('"+log.getText()+"', '"+ pas.getText()+"', '"+nick.getText()+"');";
                serverCommunication.sendMessage(sqlQuery);
            }
        });
    }
}
