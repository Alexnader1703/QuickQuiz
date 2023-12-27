    package com.example.quickquiz;

    import androidx.appcompat.app.AppCompatActivity;
    import androidx.fragment.app.Fragment;
    import androidx.fragment.app.FragmentTransaction;

    import android.content.Context;
    import android.content.Intent;
    import android.content.SharedPreferences;
    import android.os.AsyncTask;
    import android.os.Bundle;
    import android.os.Handler;
    import android.os.Looper;
    import android.util.Log;
    import android.view.View;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.FrameLayout;
    import android.widget.ImageButton;
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
    import com.example.quickquiz.ConnectionManager;
    public class MainActivity extends AppCompatActivity {

        private TextView responseTextView;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            SharedPreferences sharedPreferences = getSharedPreferences("MyApp", MODE_PRIVATE);
            boolean loggedIn = sharedPreferences.getBoolean("loggedIn", false);
            String nick = sharedPreferences.getString("login", "");
            TextView name = findViewById(R.id.name);
            name.setText(nick);
            if (!loggedIn) {
                Intent mainIntent = new Intent(MainActivity.this, Autorization.class);
                startActivity(mainIntent);
            }
            ImageButton quit = findViewById(R.id.imageButton);



            quit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("loggedIn", false);
                    editor.putString("login","");
                    editor.apply();
                    Intent mainIntent = new Intent(MainActivity.this, Autorization.class);
                    startActivity(mainIntent);

                }
            });
            SetNewFrag(new CategoryFragment());
        }



        private void SetNewFrag(Fragment fragment) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.mainWindow, fragment);
            ft.addToBackStack(null);
            ft.commit();
        }





    }