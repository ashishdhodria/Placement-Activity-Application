package com.example.placementactivityapp.student;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.placementactivityapp.POSO.Student;
import com.example.placementactivityapp.R;
import com.example.placementactivityapp.RetrofitClient;
import com.example.placementactivityapp.listener.OnItemClickListener1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyStudentAdapter extends RecyclerView.Adapter <MyStudentAdapter.MyViewHolder> {


    List<Student> students = new ArrayList<Student>();
    Context context;
    OnItemClickListener1 listener1;

    public MyStudentAdapter(List<Student> students, Context context, OnItemClickListener1 listener1) {
        this.students = students;
        this.context = context;
        this.listener1 = listener1;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.student_showdetail, viewGroup, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder,final int i) {

        myViewHolder.t1.setText(students.get(i).getName());
        myViewHolder.t2.setText(students.get(i).getEmail());
        myViewHolder.t3.setText(students.get(i).getId());
        myViewHolder.t4.setText(students.get(i).getBranch());
        myViewHolder.t5.setText(students.get(i).getCgpa());
        myViewHolder.t6.setText(students.get(i).getPassword());

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener1.onItemClick1(students.get(i));
            }
        });
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public void filterList(ArrayList<Student> filteredList) {
        this.students = filteredList;
        notifyDataSetChanged();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView t1, t2, t3, t4, t5, t6, t7;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            t1 = (TextView)itemView.findViewById(R.id.name);
            t2 = (TextView)itemView.findViewById(R.id.email);
            t3 = (TextView)itemView.findViewById(R.id.id);
            t4 = (TextView)itemView.findViewById(R.id.branch);
            t5 = (TextView)itemView.findViewById(R.id.cgpa);
            t6 = (TextView)itemView.findViewById(R.id.password);
            
        }
    }

}
