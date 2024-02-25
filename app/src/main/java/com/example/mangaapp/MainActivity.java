package com.example.mangaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;

import com.example.mangaapp.adapter.MangaAdapter;
import com.example.mangaapp.model.Manga;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerViewManga;
    private MangaAdapter mangaAdapter;
    private SearchView searchView;
    ArrayAdapter adapterData2;
    String arr[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        //test cmt 2
    }

    private void addControls() {
        recyclerViewManga = findViewById(R.id.recyclerViewManga);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
        recyclerViewManga.setLayoutManager(gridLayoutManager);

        mangaAdapter = new MangaAdapter(getMangaList());
        recyclerViewManga.setAdapter(mangaAdapter);
        //gan du lieu vao arr
        arr=getResources().getStringArray(R.array.ar_tenTruyen);
        adapterData2=new ArrayAdapter<>(MainActivity.this,R.layout.item_truyen,arr);
    }

    private List<Manga> getMangaList() {
        List<Manga> list = new ArrayList<>();
        list.add(new Manga("Chap 3689","Võ Luyện Đỉnh Phong",R.drawable.a1));
        list.add(new Manga("Chap 14","Đao Kiếm Thần Vực",R.drawable.a2));
        list.add(new Manga("Chap 801","Nguyên Tôn",R.drawable.a3));
        list.add(new Manga("Chap 1158","Toàn Chức Pháp Sư",R.drawable.a4));
        list.add(new Manga("Chap 3689","Võ Luyện Đỉnh Phong",R.drawable.a1));
        list.add(new Manga("Chap 14","Đao Kiếm Thần Vực",R.drawable.a2));
        list.add(new Manga("Chap 801","Nguyên Tôn",R.drawable.a3));
        list.add(new Manga("Chap 1158","Toàn Chức Pháp Sư",R.drawable.a4));
        list.add(new Manga("Chap 3689","Võ Luyện Đỉnh Phong",R.drawable.a1));
        list.add(new Manga("Chap 14","Đao Kiếm Thần Vực",R.drawable.a2));
        list.add(new Manga("Chap 801","Nguyên Tôn",R.drawable.a3));
        list.add(new Manga("Chap 1158","Toàn Chức Pháp Sư",R.drawable.a4));
        return list;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

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
}