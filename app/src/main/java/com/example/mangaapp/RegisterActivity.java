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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    EditText edt_Email,edt_Pass,edt_UserName;
    Button btn_goToLogin,btn_Register;
    FirebaseAuth mAuth;
    FirebaseFirestore fstore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        addControls();
        addEvents();

    }

    private void addEvents() {
        mAuth=FirebaseAuth.getInstance();
        btn_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user =mAuth.getCurrentUser();
                            Toast.makeText(getApplicationContext(),"Đăng kí thành công",Toast.LENGTH_SHORT).show();
                            Intent i= new Intent(RegisterActivity.this,MainActivity.class);
                            DocumentReference df =fstore.collection("Users").document(user.getUid());
                            Map<String,Object> userInfo= new HashMap<>();
                            userInfo.put("UserEmail",edt_Email.getText().toString());
                            userInfo.put("UserName",edt_UserName.getText().toString());
                            ///
                            userInfo.put("isUser","1");
                            ///
                            df.set(userInfo);
                            ////

                            startActivity(i);
                        }else {
                            Toast.makeText(getApplicationContext(),"Đăng kí không thành công",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        btn_goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void addControls() {
        edt_UserName=findViewById(R.id.edt_UserName);
        fstore=FirebaseFirestore.getInstance();
        edt_Email=findViewById(R.id.edt_Email);
        edt_Pass=findViewById(R.id.edt_Pass);
        btn_Register=findViewById(R.id.btn_Register);
        btn_goToLogin=findViewById(R.id.btn_goToLogin);
    }
}