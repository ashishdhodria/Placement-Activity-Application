package com.example.placementactivityapp.tpo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.placementactivityapp.POSO.Tpo;
import com.example.placementactivityapp.R;

import java.util.ArrayList;
import java.util.List;

public class MyTpoAdapter extends RecyclerView.Adapter <MyTpoAdapter.MyTpoViewHolder> {


    List<Tpo> tpos = new ArrayList<Tpo>();
    Context context;

    public MyTpoAdapter(List<Tpo> tpos , Context context){
        this.tpos = tpos;
        this.context = context;
    }
    @NonNull
    @Override
    public MyTpoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tpo_showdetail, viewGroup, false);
        MyTpoViewHolder myTpoViewHolder = new MyTpoViewHolder(view);
        return myTpoViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyTpoViewHolder myTpoViewHolder, int i) {

        myTpoViewHolder.t1.setText(tpos.get(i).getName());
        myTpoViewHolder.t2.setText(tpos.get(i).getId());
        myTpoViewHolder.t3.setText(tpos.get(i).getEmail());
        myTpoViewHolder.t4.setText(tpos.get(i).getPassword());
    }

    @Override
    public int getItemCount() {
        return tpos.size();
    }

    public static class MyTpoViewHolder extends RecyclerView.ViewHolder {
        TextView t1, t2, t3, t4;

        public MyTpoViewHolder(@NonNull View itemView) {
            super(itemView);
            t1 = (TextView)itemView.findViewById(R.id.name);
            t2 = (TextView)itemView.findViewById(R.id.id);
            t3 = (TextView)itemView.findViewById(R.id.email);
            t4 = (TextView)itemView.findViewById(R.id.password);

        }
    }
}
