package com.example.mangaapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class CreateMangaActivity2 extends AppCompatActivity {
    ImageView ivHinhManga;
    EditText edtTenManga, edtChapManga, edtDesManga;
    ImageButton ibtnFolder;
    Button btnSubmit;
    int REQUEST_CODE_FOLDER = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_manga2);
        addControls();
        addEvents();
    }

    private void addEvents() {
        ibtnFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,REQUEST_CODE_FOLDER);
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Chuyen data imageview -> byte[]
                BitmapDrawable bitmapDrawable = (BitmapDrawable) ivHinhManga.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100,byteArrayOutputStream);
                byte[] hinhAnh = byteArrayOutputStream.toByteArray();

                ContentValues row = new ContentValues();
                row.put("tenManga",edtTenManga.getText().toString());
                row.put("chapManga",edtChapManga.getText().toString());
                row.put("hinhManga",hinhAnh);
                row.put("descripManga",edtDesManga.getText().toString());

                long kq=MainActivity.database.insert("tblManga",null,row);

                if(kq > 0){
                    Toast.makeText(CreateMangaActivity2.this,"Đã thêm truyện", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(CreateMangaActivity2.this,CreateMangaActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(CreateMangaActivity2.this,"Thêm truyện thất bại", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private void addControls() {
        ivHinhManga = findViewById(R.id.ivHinhManga);

        edtTenManga = findViewById(R.id.edtTenManga);
        edtChapManga = findViewById(R.id.edtChapManga);
        edtDesManga = findViewById(R.id.edtDesManga);
        ibtnFolder = findViewById(R.id.ibtnFolder);
        btnSubmit = findViewById(R.id.btnSubmit);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE_FOLDER && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            try (InputStream inputStream = getContentResolver().openInputStream(uri)) {
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                ivHinhManga.setImageBitmap(bitmap);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}