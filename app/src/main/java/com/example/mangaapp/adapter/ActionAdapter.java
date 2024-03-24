package com.example.mangaapp.adapter;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangaapp.ActionActivity;
import com.example.mangaapp.R;
import com.example.mangaapp.model.Action;
import com.google.android.material.transition.Hold;

import java.util.ArrayList;

public class ActionAdapter extends RecyclerView.Adapter<ActionAdapter.ViewHolder>{
    ActionActivity context;
    ArrayList<Action> arr_Action;
    public ActionAdapter(ActionActivity context,ArrayList<Action>arr_Action){
        this.context=context;
        this.arr_Action=arr_Action;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View viewAction=layoutInflater.inflate(R.layout.items_action,parent,false);
        ViewHolder  viewHolderAction=new ViewHolder(viewAction);
        return viewHolderAction;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Action Act=arr_Action.get(position);
        holder.imgAction.setImageResource(Act.getHinhAc());
        holder.txtTenTruyen.setText(Act.getTentruyenAc());
        holder.txtchap.setText(Act.getChapAc()+"");
    }

    @Override
    public int getItemCount() {
        return arr_Action.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAction;
        TextView txtchap,txtTenTruyen;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAction=itemView.findViewById(R.id.imgAction);
            txtchap=itemView.findViewById(R.id.txtChap);
            txtTenTruyen=itemView.findViewById(R.id.txtTenTruyen);
        }
    }

}
