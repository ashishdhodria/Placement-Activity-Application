package com.example.placementactivityapp.student;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.placementactivityapp.POSO.Interest;
import com.example.placementactivityapp.R;
import com.example.placementactivityapp.listener.OnItemClickListener5;

import java.util.ArrayList;
import java.util.List;

public class InterestCompanyAdapter extends RecyclerView.Adapter<InterestCompanyAdapter.MyViewHolder> {

    List<Interest> interests = new ArrayList<Interest>();
    Context context;
    OnItemClickListener5 listener5;

    public InterestCompanyAdapter(List<Interest> interests, Context context, OnItemClickListener5 listener5){
        this.interests = interests;
        this.context =context;
        this.listener5 = listener5;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.company_showdetail, viewGroup,false);
       MyViewHolder myViewHolder = new MyViewHolder(view);
       return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {

        myViewHolder.t1.setText(interests.get(i).getScompany());
        myViewHolder.t2.setText(interests.get(i).getPositon());
        myViewHolder.t3.setText(interests.get(i).getCemail());

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener5.onItemClick5(interests.get(i));
            }
        });
    }

    @Override
    public int getItemCount() {
        return interests.size();
    }

    public void filterList(ArrayList<Interest> filteredList) {
        this.interests = filteredList;
        notifyDataSetChanged();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView t1,t2,t3;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            t1 = itemView.findViewById(R.id.textView22);
            t2 = itemView.findViewById(R.id.textView23);
            t3 = itemView.findViewById(R.id.textView24);
        }
    }
}
