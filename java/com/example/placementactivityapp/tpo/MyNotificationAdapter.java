package com.example.placementactivityapp.tpo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.placementactivityapp.POSO.Notification;
import com.example.placementactivityapp.R;
import com.example.placementactivityapp.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class MyNotificationAdapter extends RecyclerView.Adapter<MyNotificationAdapter.MyNotificationHolder> {

    List<Notification> notifications = new ArrayList<Notification>();
    Context context;
    OnItemClickListener listener;


    public MyNotificationAdapter(List<Notification> notifications, Context context, OnItemClickListener listener){
        this.notifications = notifications;
        this.context = context;
        this.listener = listener;

    }
    @NonNull
    @Override
    public MyNotificationHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.display_notifi,viewGroup, false);
        MyNotificationHolder myNotificationHolder = new MyNotificationHolder(view);

        return myNotificationHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyNotificationHolder myNotificationHolder, final int i) {
        myNotificationHolder.t1.setText(notifications.get(i).getTitle());
        myNotificationHolder.t2.setText(notifications.get(i).getDescription());
        myNotificationHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(notifications.get(i));
            }
        });
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public void filterList(ArrayList<Notification> filteredList) {
        this.notifications = filteredList;
        notifyDataSetChanged();
    }

    public static class MyNotificationHolder extends RecyclerView.ViewHolder {

        TextView t1, t2;

        public MyNotificationHolder(@NonNull View itemView) {
            super(itemView);

            t1 = itemView.findViewById(R.id.textView8);
            t2 = itemView.findViewById(R.id.textView9);
        }
    }
}
