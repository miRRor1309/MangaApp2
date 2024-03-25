package com.example.mangaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserInfoActivity extends AppCompatActivity {
    TextView txtUserEmail, txtUserName;
    Button btnLogOut,btnReturn,btnChangePass;
    FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    Switch switcher;
    boolean nightmode;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        switcher = findViewById(R.id.switcher);

        // Sử dụng chế độ SharedPreferences để lưu chế độ nếu thoát ứng dụng và quay lại
        sharedPreferences = getSharedPreferences("Mode", Context.MODE_PRIVATE);
        nightmode = sharedPreferences.getBoolean("night", false); // Chế độ sáng là mặc định
        if (nightmode) {
            switcher.setChecked(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        switcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nightmode = !nightmode; // Đảo ngược trạng thái của nightmode
                if (nightmode) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
                editor = sharedPreferences.edit();
                editor.putBoolean("night", nightmode);
                editor.apply();
            }
        });
        addControls();
        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(UserInfoActivity.this,ChangePassActivity.class);
                startActivity(intent);
            }
        });
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent i= new Intent(UserInfoActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(UserInfoActivity.this,MainActivity.class);
                startActivity(i);
            }
        });
        ////
        checkUserAccessLevel(mAuth.getUid());
    }

    private void checkUserAccessLevel(String uid) {
        DocumentReference df=fStore.collection("Users").document(uid);
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("TAG","onSuccess: " + documentSnapshot.getData());
                //check user hay admin
                    txtUserEmail.setText(documentSnapshot.getString("UserEmail"));
                    txtUserName.setText(documentSnapshot.getString("UserName"));
                //setText(arrayListSong.get(position).getTenbaihat())
            }
        });
    }


    private void addControls() {
        btnChangePass=findViewById(R.id.btnChangePass);
        mAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        txtUserEmail=findViewById(R.id.txtUserEmail);
        txtUserName=findViewById(R.id.txtUserName);
        btnLogOut=findViewById(R.id.btnLogOut);
        btnReturn=findViewById(R.id.btnReturn);
    }
}