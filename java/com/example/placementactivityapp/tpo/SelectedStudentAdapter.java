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

import com.example.placementactivityapp.POSO.SelectStudent;
import com.example.placementactivityapp.R;
import com.example.placementactivityapp.listener.OnItemClickListener2;
import com.example.placementactivityapp.listener.OnItemClickListener3;

import java.util.ArrayList;
import java.util.List;

public class SelectedStudentAdapter extends RecyclerView.Adapter<SelectedStudentAdapter.MyViewHolder> {

    List<SelectStudent> selectStudents = new ArrayList<SelectStudent>();
    Context context;
    OnItemClickListener3 listener3;

    public SelectedStudentAdapter(List<SelectStudent> selectStudents, Context context, OnItemClickListener3 listener3){
        this.selectStudents = selectStudents;
        this.context = context;
        this.listener3 = listener3;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_show_se_student, viewGroup, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {

        myViewHolder.t1.setText(selectStudents.get(i).getEmail());
        myViewHolder.t2.setText(selectStudents.get(i).getCemail());
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu popup = new PopupMenu(context,myViewHolder.itemView);
                //inflating menu from xml resource
                popup.inflate(R.menu.popup_menu3);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.company:
                                listener3.onItemClick3(selectStudents.get(i),"company");
                                return true;
                            case R.id.student:
                                listener3.onItemClick3(selectStudents.get(i),"student");
                                return true;
                            case R.id.delete:
                                listener3.onItemClick3(selectStudents.get(i),"delete");
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
        return selectStudents.size();
    }

    public void filterList(ArrayList<SelectStudent> filteredList) {
        this.selectStudents = filteredList;
        notifyDataSetChanged();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView t1, t2;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            t1  = itemView.findViewById(R.id.email);
            t2 = itemView.findViewById(R.id.cemail);
        }
    }
}
