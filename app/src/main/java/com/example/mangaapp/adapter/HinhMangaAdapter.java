package com.example.mangaapp.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mangaapp.R;
import com.example.mangaapp.model.HinhManga;

public class HinhMangaAdapter extends ArrayAdapter<HinhManga> {
    Activity context;
    int resource;
    public HinhMangaAdapter(@NonNull Activity context, int resource) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = this.context.getLayoutInflater();
        View customView = layoutInflater.inflate(this.resource,null);
        ImageView ivHinhChap = customView.findViewById(R.id.ivHinhChap);

        HinhManga hinhManga = getItem(position);
        byte[] imgChap = hinhManga.getHinhChap();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imgChap,0, imgChap.length);
        ivHinhChap.setImageBitmap(bitmap);

        return customView;
    }
}
