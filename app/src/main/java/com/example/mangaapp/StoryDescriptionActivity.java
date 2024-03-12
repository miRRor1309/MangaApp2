package com.example.mangaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mangaapp.adapter.MangaChapAdapter;
import com.example.mangaapp.model.Manga;
import com.example.mangaapp.model.MangaChap;

public class StoryDescriptionActivity extends AppCompatActivity {
    TextView txtTenManga, txtDes;
    ImageView ivManga;
    ListView lvChap;
    Button btnBack;
    MangaChapAdapter mangaChapAdapter;
    public String DATABASE_NAME = "mangaappdb";
    public static SQLiteDatabase database = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_description);
        addControls();
        catchAndShowData();
        addEvents();
    }

    private void addEvents() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        lvChap.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MangaChap mangaChap = mangaChapAdapter.getItem(position);
                Intent intent = new Intent(StoryDescriptionActivity.this, HinhMangaActivity2.class);
                intent.putExtra("mangaChap",mangaChap);
                startActivity(intent);
            }
        });
    }

    private void catchAndShowData() {
        //Bắt và hiển thị dữ liệu
        Bundle bundle = getIntent().getExtras();
        if(bundle == null){
            return;
        }
        Manga manga = (Manga) bundle.get("MangaObject");

        txtTenManga.setText(manga.getTentruyen());
        byte[] anhManga = manga.getImgtruyen();
        Bitmap bitmap = BitmapFactory.decodeByteArray(anhManga,0,anhManga.length);
        ivManga.setImageBitmap(bitmap);
        txtDes.setText(manga.getDestruyen());

        //List chap
        database = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
        Cursor cursor = database.rawQuery("select * from tblChap", null);
        mangaChapAdapter.clear();
        while (cursor.moveToNext()){
            int maChap = cursor.getInt(0);
            String tenChap = cursor.getString(1);
            String ngayDang = cursor.getString(2);
            int maManga = cursor.getInt(3);
            MangaChap mc = new MangaChap(tenChap,ngayDang,maChap,maManga);
            if(maManga == manga.getMaTruyen()){
                mangaChapAdapter.add(mc);
            }
        }
        cursor.close();
    }

    private void addControls() {
        txtTenManga = findViewById(R.id.txtTenManga);
        txtDes = findViewById(R.id.txtDes);
        ivManga = findViewById(R.id.ivManga);
        lvChap = findViewById(R.id.lvChap);
        btnBack = findViewById(R.id.btnBack);

        mangaChapAdapter = new MangaChapAdapter(this,R.layout.items_chap);
        lvChap.setAdapter(mangaChapAdapter);
    }
}