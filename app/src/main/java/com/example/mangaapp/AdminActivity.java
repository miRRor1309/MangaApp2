package com.example.mangaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mangaapp._interface.IClickMangaListener;
import com.example.mangaapp.adapter.MangaAdapter;
import com.example.mangaapp.model.Manga;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


public class AdminActivity extends AppCompatActivity {
    Button btnLogOut,btnAdd,btnBack;
    TextView txtUserEmail, txtUserName;
    FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    public String DATABASE_NAME = "mangaappdb";
    public String DB_SUFFIX_PATH = "/databases/";
    public static SQLiteDatabase database = null;
    TextView txt_Username;
    private RecyclerView recyclerViewManga;
    private MangaAdapter mangaAdapter;
    private SearchView searchView;
    private List<Manga> getMangaList() {
        List<Manga> list = new ArrayList<>();
        database = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
        Cursor cursor = database.rawQuery("select * from tblManga",null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Integer ma = cursor.getInt(0);
            String ten = cursor.getString(1);
            String chap = cursor.getString(2);
            byte[] hinh = cursor.getBlob(3);
            String des=cursor.getString(4);
            list.add(new Manga(chap,ten,hinh,des,ma));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        addControls();
        addEvents();
        checkUserAccessLevel(mAuth.getUid());
    }
    public String getDatabasePath(){
        return getApplicationInfo().dataDir + DB_SUFFIX_PATH + DATABASE_NAME;
    }
    private void processCopy() {
        try{
            File file = getDatabasePath(DATABASE_NAME);
            if(!file.exists()){
                coppyDatabaseFromAsset();
                Toast.makeText(this,"Coppy Databse Success",Toast.LENGTH_SHORT).show();
            }
        }catch(Exception ex){
            Toast.makeText(this,"Coppy Databse Fail",Toast.LENGTH_SHORT).show();
        }
    }
    private void coppyDatabaseFromAsset() {
        try{
            InputStream inputFile = getAssets().open(DATABASE_NAME);
            String outputFileName = getDatabasePath();
            File file = new File(getApplicationInfo().dataDir + DB_SUFFIX_PATH);
            if(!file.exists()){
                file.mkdir();
            }
            OutputStream outputFile = new FileOutputStream(outputFileName);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputFile.read(buffer)) > 0){
                outputFile.write(buffer,0,length);
            }
            outputFile.flush();
            outputFile.close();
            inputFile.close();
        }catch(Exception ex){
            Log.e("Error",ex.toString());
        }
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
            }
        });
    }
    private void addEvents() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(AdminActivity.this, CreateMangaActivity2.class);
                startActivity(i);
            }
        });
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent i= new Intent(AdminActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void addControls() {
        recyclerViewManga = findViewById(R.id.recyclerViewManga);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
        recyclerViewManga.setLayoutManager(gridLayoutManager);

        mangaAdapter = new MangaAdapter(getMangaList(), new IClickMangaListener() {
            @Override
            public void onClickManga(Manga manga) {
                onClickGoToDetail(manga);
            }
        });
        recyclerViewManga.setAdapter(mangaAdapter);
        btnBack=findViewById(R.id.btnBack);
        txtUserEmail=findViewById(R.id.txtUserEmail);
        txtUserName=findViewById(R.id.txtUserName);
        mAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        btnLogOut=findViewById(R.id.btnLogOut);
        btnAdd=findViewById(R.id.btnAdd);
    }
    private void onClickGoToDetail(Manga mg) {
        Intent intent = new Intent(this, EditMangaActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("MangaObject", mg);
        intent.putExtras(bundle);
        startActivity(intent);
    }

}