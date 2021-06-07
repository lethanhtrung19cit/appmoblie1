package com.google.firebase.referencecode.projectlastterm;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ListOrderAdapter extends RecyclerView.Adapter<ListOrderViewHolder> {
    ArrayList<ListOrderModel> list;
    Context context;
    public ListOrderAdapter(){}
    public ListOrderAdapter(ArrayList<ListOrderModel> list, Context context)
    {
        this.list=list;
        this.context=context;
    }
    @NonNull
    @Override
    public ListOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_listorder, parent, false);

        return new ListOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListOrderViewHolder holder, int position) {
        ListOrderModel current = list.get(position);
        holder.nameFood.setText(current.getNameFood());
        holder.price.setText(String.valueOf(current.getPrice()));
        holder.amount.setText(String.valueOf(current.getAmount()));
        holder.sumPrice.setText(String.valueOf(current.getSumPrice()));
        Picasso.get().load(current.getUrlImg()).into(holder.imageFood);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
