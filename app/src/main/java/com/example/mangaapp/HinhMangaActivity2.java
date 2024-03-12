package com.example.mangaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mangaapp.adapter.HinhMangaAdapter;
import com.example.mangaapp.model.HinhManga;
import com.example.mangaapp.model.MangaChap;

public class HinhMangaActivity2 extends AppCompatActivity {
    TextView textView8;
    ListView ImgManga;
    Button btnBackListTruyen;
    HinhMangaAdapter hinhMangaAdapter;
    public String DATABASE_NAME = "mangaappdb";
    public static SQLiteDatabase database = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hinh_manga2);

        //addControls()
        textView8 = findViewById(R.id.textView8);
        ImgManga = findViewById(R.id.ImgManga);
        btnBackListTruyen = findViewById(R.id.btnBackListChap);
        hinhMangaAdapter = new HinhMangaAdapter(this, R.layout.items_hinhchap);
        ImgManga.setAdapter(hinhMangaAdapter);

        btnBackListTruyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        MangaChap mangaChap = (MangaChap) intent.getSerializableExtra("mangaChap");
        textView8.setText(mangaChap.getChapname());

        database = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
        Cursor cursor = database.rawQuery("select * from tblHinhChap", null);
        while(cursor.moveToNext()){
            int maHinhChap = cursor.getInt(0);
            byte[] HinhChap = cursor.getBlob(1);
            int maChap = cursor.getInt(2);
            HinhManga hinhManga = new HinhManga(maHinhChap,maChap,HinhChap);
            if(maChap == mangaChap.getChapid()){
                hinhMangaAdapter.add(hinhManga);
            }
        }
        cursor.close();
    }
}