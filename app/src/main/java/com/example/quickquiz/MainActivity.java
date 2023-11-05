package com.example.quickquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
}