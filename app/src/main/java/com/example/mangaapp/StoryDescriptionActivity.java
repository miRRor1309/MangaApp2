package com.example.mangaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mangaapp.model.Manga;

public class StoryDescriptionActivity extends AppCompatActivity {
    TextView txtTenManga, txtDes;
    ImageView ivManga;
    ListView lvChap;
    Button btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_description);
        addControls();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void addControls() {
        txtTenManga = findViewById(R.id.txtTenManga);
        txtDes = findViewById(R.id.txtDes);
        ivManga = findViewById(R.id.ivManga);
        lvChap = findViewById(R.id.lvChap);
        btnBack = findViewById(R.id.btnBack);

        //Bat va hien thi du lieu
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
    }
}