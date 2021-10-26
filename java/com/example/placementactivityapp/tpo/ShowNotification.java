package com.example.placementactivityapp.tpo;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.example.placementactivityapp.POSO.Student;
import com.example.placementactivityapp.R;
import com.example.placementactivityapp.RetrofitClient;
import com.example.placementactivityapp.listener.OnItemClickListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class ShowNotification extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    MyNotificationAdapter myNotificationAdapter;
    String email;

    EditText editText;
    List<Notification> notifications = new ArrayList<Notification>();

    public ShowNotification(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.activity_show_notification, container,false);
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

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("mypref1", MODE_PRIVATE);
        email = sharedPreferences.getString("email", "No");
        reloadfun();
    }
    void reloadfun() {
        Call<List<Notification>> call = RetrofitClient
                .getInstance()
                .getApi()
                .getnotif(email);
        call.enqueue(new Callback<List<Notification>>() {
            @Override
            public void onResponse(Call<List<Notification>> call, Response<List<Notification>> response) {
                notifications = response.body();
                myNotificationAdapter = new MyNotificationAdapter(notifications, getActivity(), new OnItemClickListener() {
                    @Override
                    public void onItemClick(final Notification notification) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("Delete");
                        builder.setMessage("Do you want to delete");
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Call<ResponseBody> call = RetrofitClient
                                        .getInstance()
                                        .getApi()
                                        .deletenotif(notification.getTitle(),email);
                                call.enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                        try {
                                            String s=response.body().string();
                                            Toast.makeText(getActivity(),s,Toast.LENGTH_LONG).show();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }

                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                                        Toast.makeText(getActivity(),t.getMessage(),Toast.LENGTH_LONG).show();
                                    }
                                });
                                ShowNotification f1=new ShowNotification(); //User defined fragment class
                                FragmentTransaction ft=getActivity().getSupportFragmentManager().beginTransaction();
                                ft.replace(R.id.fragment_container,f1);
                                ft.addToBackStack(null);
                                ft.commit();
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();

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
