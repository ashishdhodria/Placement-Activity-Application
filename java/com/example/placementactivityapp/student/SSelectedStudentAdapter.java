package com.example.placementactivityapp.student;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.placementactivityapp.POSO.SelectStudent;
import com.example.placementactivityapp.R;
import com.example.placementactivityapp.tpo.SelectedStudentAdapter;

import java.util.ArrayList;
import java.util.List;

public class SSelectedStudentAdapter extends RecyclerView.Adapter<SSelectedStudentAdapter.MyViewHolder> {

    List<SelectStudent> selectStudents = new ArrayList<SelectStudent>();
    Context context;

    public  SSelectedStudentAdapter(List<SelectStudent> selectStudents, Context context){
        this.selectStudents = selectStudents;
        this.context =context;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_show_se_student, viewGroup, false);
        SSelectedStudentAdapter.MyViewHolder myViewHolder = new SSelectedStudentAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.t1.setText(selectStudents.get(i).getEmail());
        myViewHolder.t2.setText(selectStudents.get(i).getCemail());
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
