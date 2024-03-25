package com.example.mangaapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.Toast;

import com.example.mangaapp.model.Manga;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class EditMangaActivity extends AppCompatActivity {
    ImageView ivHinhMangaUpdate;
    EditText edtMaMangaUpdate,edtTenMangaUpdate,edtChapMangaUpdate,edtDesMangaUpdate;
    ImageButton ibtnFolderUpdate;
    Button btnUpdate,btnDelete;
    int REQUEST_CODE_FOLDER = 456;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_manga);
        addControls();
        catchAndShowData();
        addEvents();
    }

    private void catchAndShowData() {
        //Catch Data
        Bundle bundle = getIntent().getExtras();
        if(bundle == null){
            return;
        }
        Manga manga = (Manga) bundle.get("MangaInfo");

        //Show Data
        byte[] anhMangaUpdate = manga.getImgtruyen();
        Bitmap bitmap = BitmapFactory.decodeByteArray(anhMangaUpdate,0,anhMangaUpdate.length);
        ivHinhMangaUpdate.setImageBitmap(bitmap);
        String maMangaUpdate = String.valueOf(manga.getMaTruyen());
        edtMaMangaUpdate.setText(maMangaUpdate);
        edtTenMangaUpdate.setText(manga.getTentruyen());
        edtChapMangaUpdate.setText(manga.getTenchap());
        edtDesMangaUpdate.setText(manga.getDestruyen());
        edtMaMangaUpdate.setEnabled(false);
    }

    private void addEvents() {
        ibtnFolderUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,REQUEST_CODE_FOLDER);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Chuyen data imageview -> byte[]
                BitmapDrawable bitmapDrawable = (BitmapDrawable) ivHinhMangaUpdate.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100,byteArrayOutputStream);
                byte[] hinhAnhUpdate = byteArrayOutputStream.toByteArray();

                ContentValues contentValues = new ContentValues();
                contentValues.put("tenManga",edtTenMangaUpdate.getText().toString());
                contentValues.put("chapManga",edtChapMangaUpdate.getText().toString());
                contentValues.put("hinhManga",hinhAnhUpdate);
                contentValues.put("descripManga",edtDesMangaUpdate.getText().toString());

                long kq = MainActivity.database.update("tblManga",contentValues,"maManga=?",new String[]{edtMaMangaUpdate.getText().toString()});
                if(kq > 0){
                    Toast.makeText(EditMangaActivity.this,"Đã update truyện", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(EditMangaActivity.this,CreateMangaActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(EditMangaActivity.this,"Update truyện thất bại", Toast.LENGTH_LONG).show();

                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditMangaActivity.this);
                builder.setTitle("Delete");
                builder.setMessage("Do you want to delete?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        long kq = MainActivity.database.delete("tblManga","maManga=?",new String[]{edtMaMangaUpdate.getText().toString()});
                        if(kq > 0){
                            Intent intent = new Intent(EditMangaActivity.this,CreateMangaActivity.class);
                            startActivity(intent);
                        }
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.create().show();
            }
        });
    }

    private void addControls() {
        ivHinhMangaUpdate = findViewById(R.id.ivHinhMangaUpdate);
        edtMaMangaUpdate = findViewById(R.id.edtMaMangaUpdate);
        edtTenMangaUpdate = findViewById(R.id.edtTenMangaUpdate);
        edtChapMangaUpdate = findViewById(R.id.edtChapMangaUpdate);
        edtDesMangaUpdate = findViewById(R.id.edtDesMangaUpdate);
        ibtnFolderUpdate = findViewById(R.id.ibtnFolderUpdate);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE_FOLDER && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            try (InputStream inputStream = getContentResolver().openInputStream(uri)) {
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                ivHinhMangaUpdate.setImageBitmap(bitmap);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}