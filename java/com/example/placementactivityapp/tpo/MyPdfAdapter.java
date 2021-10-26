package com.example.placementactivityapp.tpo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.placementactivityapp.POSO.PDF;
import com.example.placementactivityapp.R;
import com.example.placementactivityapp.listener.OnItemClickListener;
import com.example.placementactivityapp.listener.OnItemClickListener6;

import java.util.ArrayList;
import java.util.List;

public class MyPdfAdapter extends RecyclerView.Adapter<MyPdfAdapter.MyViewHolder> {

List<PDF> pdfs = new ArrayList<PDF>();
Context context;
OnItemClickListener6 listener6;

public MyPdfAdapter(List<PDF> pdfs, Context context, OnItemClickListener6 listener6){
    this.pdfs = pdfs;
    this.context = context;
    this.listener6 = listener6;
}

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_show_pdf,viewGroup, false);
    MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.t1.setText(pdfs.get(i).getTitle());
        final String imgname = pdfs.get(i).getPdf();
        String file_ext = imgname.substring(imgname.length() - 3);

        if (file_ext.equalsIgnoreCase("png")){
            Glide.with(myViewHolder.img.getContext()).load(imgname).into(myViewHolder.img);
        }
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener6.onItemClick6(pdfs.get(i));
            }
        });
    }

    @Override
    public int getItemCount() {
        return pdfs.size();
    }

    public void filterList(ArrayList<PDF> filteredList) {
        this.pdfs = filteredList;
        notifyDataSetChanged();
   }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView t1;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.imageView2);
            t1 = itemView.findViewById(R.id.tv1);
        }
    }
}
