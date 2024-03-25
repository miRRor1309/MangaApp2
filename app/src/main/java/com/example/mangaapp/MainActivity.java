package com.example.mangaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
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

public class MainActivity extends AppCompatActivity {
    TextView txt_Username;
    private RecyclerView recyclerViewManga;
    private MangaAdapter mangaAdapter;
    private SearchView searchView;
    private Button menuUser;
    FirebaseAuth mAuth;
    FirebaseFirestore fStore;

    public String DATABASE_NAME = "mangaappdb";
    public String DB_SUFFIX_PATH = "/databases/";
    public static SQLiteDatabase database = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        processCopy();
        addControls();

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

                if(documentSnapshot.getString("isAdmin" )!=null){
                    startActivity(new Intent(getApplicationContext(),AdminActivity.class));
                    finish();

                }
                if (documentSnapshot.getString("isUser")!=null){

                    startActivity(new Intent(getApplicationContext(),UserInfoActivity.class));
                    finish();
                }
            }
        });
    }
    private void addControls() {
        mAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        txt_Username=findViewById(R.id.edt_Email);
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
    }

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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.mnuUser){
            checkUserAccessLevel(mAuth.getUid());
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mnu=getMenuInflater();
        mnu.inflate(R.menu.main_menu,menu);
        //getMenuInflater().inflate(R.menu.main_menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.menuSearch).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mangaAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mangaAdapter.getFilter().filter(newText);
                return false;
            }
        });


        return true;
    }

    private void onClickGoToDetail(Manga mg) {
        Intent intent = new Intent(this, StoryDescriptionActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("MangaObject", mg);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}