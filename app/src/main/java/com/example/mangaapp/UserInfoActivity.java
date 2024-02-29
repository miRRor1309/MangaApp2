package com.example.mangaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UserInfoActivity extends AppCompatActivity {
    Button btn_quaylai;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        addControls();
        addEvents();

    }

    private void addEvents() {
        btn_quaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(UserInfoActivity.this ,MainActivity.class);
                startActivity(i);
            }
        });
    }

    private void addControls() {
        btn_quaylai=findViewById(R.id.btn_quaylai);
    }
}