package com.example.mangaapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mangaapp.R;
import com.example.mangaapp.model.MangaChap;

public class MangaChapAdapter extends ArrayAdapter<MangaChap> {
    Activity context;
    int resource;
    public MangaChapAdapter(@NonNull Activity context, int resource) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = this.context.getLayoutInflater();
        View customView = layoutInflater.inflate(this.resource,null);
        TextView txtChapName = customView.findViewById(R.id.txtChapName);
        TextView txtDateChap = customView.findViewById(R.id.txtDateChap);

        MangaChap mc = getItem(position);
        txtChapName.setText(mc.getChapname());
        txtDateChap.setText(mc.getNgaydang());
        return customView;
    }
}
