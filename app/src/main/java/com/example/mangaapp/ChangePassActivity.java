package com.example.mangaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ChangePassActivity extends AppCompatActivity {
    EditText edt_Email;
    Button btn_GuiXacNhan,btn_GoBack;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);
        addControls();
        addEvents();

    }

    private void addEvents() {
        btn_GuiXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth=FirebaseAuth.getInstance();
                String emailAddress =edt_Email.getText().toString().trim();;

                mAuth.sendPasswordResetEmail(emailAddress).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),"Email sent.",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(),"Email sent fail.",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
        btn_GoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(ChangePassActivity.this, UserInfoActivity.class);
                startActivity(i);
            }
        });
    }

    private void addControls() {
        edt_Email=findViewById(R.id.edt_Email);
        btn_GuiXacNhan=findViewById(R.id.btn_GuiXacNhan);
        btn_GoBack=findViewById(R.id.btn_GoBack);
    }
}