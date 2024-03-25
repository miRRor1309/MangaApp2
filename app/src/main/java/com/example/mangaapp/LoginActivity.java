package com.example.mangaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

    EditText edt_Email,edt_Pass;
    Button btn_Login,btn_Cancel,btn_goToRegister;
    private LinearLayout layoutForgotPassword;

    FirebaseAuth mAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        addControls();
        addEvents();
        //test connect 3
    }

    private void addEvents() {
        layoutForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(LoginActivity.this, ForgotPassActivity.class);
                startActivity(intent);
            }
        });



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

                mAuth.signInWithEmailAndPassword(edt_Email.getText().toString(),edt_Pass.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(LoginActivity.this,"Đăng nhập thành công",Toast.LENGTH_SHORT).show();
                        checkUserAccessLevel(authResult.getUser().getUid());
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            }
        });
    }

    private void checkUserAccessLevel(String uid) {
        DocumentReference df=fStore.collection("Users").document(uid);
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("TAG","onSuccess: " + documentSnapshot.getData());
                //check user hay admin

                if(documentSnapshot.getString("isAdmin" )!=null){
                    startActivity(new Intent(getApplicationContext(),AdminActivity.class));
                    finish();

                }
                if (documentSnapshot.getString("isUser")!=null){

                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    finish();
                }
            }
        });
    }

    private void addControls() {
        mAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        edt_Pass=findViewById(R.id.edt_Pass);
        edt_Email=findViewById(R.id.edt_Email);
        btn_Login=findViewById(R.id.btn_Login);
        btn_Cancel=findViewById(R.id.btn_Cancel);
        btn_goToRegister=findViewById(R.id.btn_goToRegister);
        layoutForgotPassword=findViewById(R.id.layout_forgot_pasword);
    }

    /*@Override
    protected void onStart() {
        if(FirebaseAuth.getInstance().getCurrentUser() != null)
        {
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }
        super.onStart();
    }*/
}