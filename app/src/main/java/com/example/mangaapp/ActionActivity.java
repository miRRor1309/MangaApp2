package com.example.mangaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.bumptech.glide.load.model.ModelLoader;
import com.example.mangaapp.adapter.ActionAdapter;
import com.example.mangaapp.model.Action;

import java.util.ArrayList;

public class ActionActivity extends AppCompatActivity {
RecyclerView recyclerViewAction;
ActionAdapter actionAdapter;
ArrayList<Action>arr_Action;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action);
        addControl();
        LoadData();
    }

    private void LoadData() {
        arr_Action.add(new Action(R.drawable.a1,"Độc cô cầu bại",1));
        arr_Action.add(new Action(R.drawable.a1,"Độc cô cầu bại",2));
        arr_Action.add(new Action(R.drawable.a1,"Độc cô cầu bại",9));
        arr_Action.add(new Action(R.drawable.a1,"Độc cô cầu bại",8));
    }

    private void addControl() {
        recyclerViewAction=findViewById(R.id.recyclerViewAction);
        arr_Action=new ArrayList<>();
        actionAdapter = new ActionAdapter(this,arr_Action);
        recyclerViewAction.setAdapter(actionAdapter);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,3);
        //recyclerViewAction.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAction.setLayoutManager(gridLayoutManager);
    }
}