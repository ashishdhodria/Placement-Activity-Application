package com.example.placementactivityapp.student;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.placementactivityapp.POSO.Company;
import com.example.placementactivityapp.R;
import com.example.placementactivityapp.listener.OnItemClickListener2;
import com.example.placementactivityapp.tpo.ShowCompanyAdapter;

import java.util.ArrayList;
import java.util.List;

public class SShowCompanyAdapter extends RecyclerView.Adapter<SShowCompanyAdapter.MyViewHolder> {
    List<Company> companies =new ArrayList<Company>();
    Context context;
    OnItemClickListener2 listener2;

    public SShowCompanyAdapter(List<Company> companies, Context context, OnItemClickListener2 listener2){
        this.companies = companies;
        this.context = context;
        this.listener2 = listener2;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.company_showdetail, viewGroup,false);
        SShowCompanyAdapter.MyViewHolder myViewHolder = new SShowCompanyAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.t1.setText(companies.get(i).getName());
        myViewHolder.t2.setText(companies.get(i).getPosition());
        myViewHolder.t3.setText(companies.get(i).getLocation());

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener2.onItemClick2(companies.get(i),"add");
            }
        });
    }

    @Override
    public int getItemCount() {
        return companies.size();
    }

    public void filterList(ArrayList<Company> filteredList) {
        this.companies = filteredList;
        notifyDataSetChanged();;
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
