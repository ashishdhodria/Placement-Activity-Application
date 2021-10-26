package com.example.placementactivityapp.student;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.placementactivityapp.POSO.Notification;
import com.example.placementactivityapp.POSO.SelectStudent;
import com.example.placementactivityapp.R;
import com.example.placementactivityapp.RetrofitClient;
import com.example.placementactivityapp.listener.OnItemClickListener;
import com.example.placementactivityapp.tpo.MyNotificationAdapter;
import com.example.placementactivityapp.tpo.ShowNotification;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class SShow_Notifi extends Fragment {


    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    MyNotificationAdapter myNotificationAdapter;
    String email,adkey;
    EditText editText;
    List<Notification> notifications = new ArrayList<Notification>();

    public SShow_Notifi(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return  inflater.inflate(R.layout.activity_show__student, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rcv1);

        editText = view.findViewById(R.id.editText);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("mypref2", MODE_PRIVATE);
        email = sharedPreferences.getString("email", "No");
        adkey = sharedPreferences.getString("adkey", "NO");
        reloadfun();
    }
    void reloadfun() {
        Call<List<Notification>> call = RetrofitClient
                .getInstance()
                .getApi()
                .sgetnotif(adkey);
        call.enqueue(new Callback<List<Notification>>() {
            @Override
            public void onResponse(Call<List<Notification>> call, Response<List<Notification>> response) {
                notifications = response.body();
                myNotificationAdapter = new MyNotificationAdapter(notifications, getActivity(), new OnItemClickListener() {
                    @Override
                    public void onItemClick(final Notification notification) {
                    }
                });
                recyclerView.setAdapter(myNotificationAdapter);
            }

            @Override
            public void onFailure(Call<List<Notification>> call, Throwable t) {
                Toast.makeText(getActivity(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }


    private void filter(String text) {
        ArrayList<Notification> filteredList = new ArrayList<Notification>();

        for (Notification item : notifications) {
            if (item.getTitle().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        myNotificationAdapter.filterList(filteredList);
    }
}
