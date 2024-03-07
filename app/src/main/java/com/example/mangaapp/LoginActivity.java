package com.example.mangaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText edt_Email,edt_Pass;
    Button btn_Login,btn_Cancel,btn_goToRegister;
    private LinearLayout layoutForgotPassword;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        addControls();
        addEvents();

    }

    private void addEvents() {
        layoutForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickForgotPassword();
            }

            private void onClickForgotPassword() {
                Intent i= new Intent(LoginActivity.this, ForgotPassActivity.class);
                startActivity(i);
            }
        });








        mAuth=FirebaseAuth.getInstance();

        btn_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_goToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToRegister();
            }

            private void goToRegister() {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }

            private void login() {
                Drawable icERR=getResources().getDrawable(R.drawable.error_icon);
                icERR.setBounds(0,0,icERR.getIntrinsicWidth(),icERR.getIntrinsicHeight());
                String email=edt_Email.getText().toString().trim();
                String pass=edt_Pass.getText().toString().trim();
                if(TextUtils.isEmpty(email))
                {
                    Toast.makeText(getApplicationContext(),"Vui lòng nhập email!",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(pass))
                {
                    Toast.makeText(getApplicationContext(),"Vui lòng nhập password!",Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Đăng nhập thành công",Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(LoginActivity.this,MainActivity.class);

                            startActivity(intent);
                        }else {
                            Toast.makeText(getApplicationContext(),"Đăng nhập thành công",Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });
    }

    private void addControls() {
        edt_Pass=findViewById(R.id.edt_Pass);
        edt_Email=findViewById(R.id.edt_Email);
        btn_Login=findViewById(R.id.btn_Login);
        btn_Cancel=findViewById(R.id.btn_Cancel);
        btn_goToRegister=findViewById(R.id.btn_goToRegister);
        layoutForgotPassword=findViewById(R.id.layout_forgot_pasword);
    }
}