package com.example.placementactivityapp.tpo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.placementactivityapp.POSO.Company;
import com.example.placementactivityapp.R;
import com.example.placementactivityapp.listener.OnItemClickListener2;

import java.util.ArrayList;
import java.util.List;

public class ShowCompanyAdapter extends RecyclerView.Adapter<ShowCompanyAdapter.MyViewHolder> {

    List<Company> companies =new ArrayList<Company>();
    Context context;
    OnItemClickListener2 listener2;

    public ShowCompanyAdapter(List<Company> companies, Context context, OnItemClickListener2 listener2){
        this.companies = companies;
        this.context = context;
        this.listener2 = listener2;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.company_showdetail, viewGroup,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {

        myViewHolder.t1.setText(companies.get(i).getName());
        myViewHolder.t2.setText(companies.get(i).getPosition());
        myViewHolder.t3.setText(companies.get(i).getLocation());
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(context,myViewHolder.itemView);
                //inflating menu from xml resource
                popup.inflate(R.menu.popup_menu_company);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.show:
                                listener2.onItemClick2(companies.get(i), "show");
                                return true;
                            case R.id.update:
                                listener2.onItemClick2(companies.get(i), "update");
                                return true;
                            case R.id.delete:
                                listener2.onItemClick2(companies.get(i), "delete");
                                return true;
                            case R.id.student:
                                listener2.onItemClick2(companies.get(i),"student");
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                popup.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return companies.size();
    }

    public void filterList(ArrayList<Company> filteredList) {
        this.companies = filteredList;
        notifyDataSetChanged();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView t1, t2 ,t3;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            t1 = itemView.findViewById(R.id.textView22);
            t2 = itemView.findViewById(R.id.textView23);
            t3 = itemView.findViewById(R.id.textView24);

        }
    }
}
