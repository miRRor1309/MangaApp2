package com.example.mangaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    EditText edt_TenUser,edt_Pass;
    Button btn_Login,btn_Cancel,btn_goToRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        addControls();
        addEvents();

    }

    private void addEvents() {
        btn_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_goToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable icERR=getResources().getDrawable(R.drawable.error_icon);
                icERR.setBounds(0,0,icERR.getIntrinsicWidth(),icERR.getIntrinsicHeight());
                String name=edt_TenUser.getText().toString().trim();
                String pass=edt_Pass.getText().toString().trim();
                if(name.isEmpty())
                {
                    edt_TenUser.setCompoundDrawables(null,null,icERR,null);
                    edt_TenUser.setError("Vui lòng nhập tên",icERR);
                }
                if(pass.isEmpty())
                {
                    edt_TenUser.setCompoundDrawables(null,null,icERR,null);
                    edt_TenUser.setError("Vui lòng nhập mật khẩu",icERR);
                }
                if(!name.isEmpty() && !pass.isEmpty())
                {
                    edt_TenUser.setCompoundDrawables(null,null,null,null);
                    edt_Pass.setCompoundDrawables(null,null,null,null);
                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void addControls() {
        edt_Pass=findViewById(R.id.edt_Pass);
        edt_TenUser=findViewById(R.id.edt_TenUser);
        btn_Login=findViewById(R.id.btn_Login);
        btn_Cancel=findViewById(R.id.btn_Cancel);
        btn_goToRegister=findViewById(R.id.btn_goToRegister);
    }
}