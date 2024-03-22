package com.example.mangaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserInfoActivity extends AppCompatActivity {
    TextView txtUserEmail, txtUserName;
    Button btnLogOut,btnReturn;
    FirebaseAuth mAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        addControls();
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
        mAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        txtUserEmail=findViewById(R.id.txtUserEmail);
        txtUserName=findViewById(R.id.txtUserName);
        btnLogOut=findViewById(R.id.btnLogOut);
        btnReturn=findViewById(R.id.btnReturn);
    }
}