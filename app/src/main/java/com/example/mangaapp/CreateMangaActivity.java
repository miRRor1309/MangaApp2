package com.example.mangaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mangaapp._interface.IClickMangaListener;
import com.example.mangaapp.adapter.MangaAdapter;
import com.example.mangaapp.model.Manga;

import java.util.ArrayList;
import java.util.List;

public class CreateMangaActivity extends AppCompatActivity {
    RecyclerView rvLstManga;
    Button btnAdd,btnBackToMain;
    MangaAdapter mangaAdapter;
    public String DATABASE_NAME = "mangaappdb";
    public static SQLiteDatabase database = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_manga);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateMangaActivity.this, CreateMangaActivity2.class);
                startActivity(intent);
            }
        });

        btnBackToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateMangaActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void addControls() {
        rvLstManga = findViewById(R.id.rvLstManga);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
        rvLstManga.setLayoutManager(gridLayoutManager);
        btnAdd = findViewById(R.id.btnAdd);
        btnBackToMain = findViewById(R.id.btnBackToMain);
        mangaAdapter = new MangaAdapter(getMangaList(), new IClickMangaListener() {
            @Override
            public void onClickManga(Manga manga) {
                onClickGoToDetail(manga);
            }
        });
        rvLstManga.setAdapter(mangaAdapter);
    }

    private List<Manga> getMangaList(){
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

    private void onClickGoToDetail(Manga manga){
        Intent intent = new Intent(this, EditMangaActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("MangaInfo",manga);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}