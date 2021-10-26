package com.example.placementactivityapp.student;

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

import com.example.placementactivityapp.POSO.Interest;
import com.example.placementactivityapp.POSO.PDF;
import com.example.placementactivityapp.R;
import com.example.placementactivityapp.RetrofitClient;
import com.example.placementactivityapp.listener.OnItemClickListener5;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class InterestedCompany extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    InterestCompanyAdapter interestCompanyAdapter;
    List<Interest> interests = new ArrayList<Interest>();
    EditText editText;
    public InterestedCompany(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.activity_interested_company, container, false);
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
        final String email = sharedPreferences.getString("email", "NO");
        final  String adkey = sharedPreferences.getString("adkey", "NO");
        final String name = sharedPreferences.getString("name", "NO");

        Call<List<Interest>> call = RetrofitClient
                .getInstance()
                .getApi()
                .getinterest(email);
        call.enqueue(new Callback<List<Interest>>() {
            @Override
            public void onResponse(Call<List<Interest>> call, Response<List<Interest>> response) {
                interests = response.body();
                interestCompanyAdapter = new InterestCompanyAdapter(interests, getActivity(), new OnItemClickListener5() {
                    @Override
                    public void onItemClick5(final Interest interest) {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("Remove Interest");
                        builder.setMessage("Do you want to remove interest.");

                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Call<ResponseBody> call = RetrofitClient
                                        .getInstance()
                                        .getApi()
                                        .deleteinterestchoice(interest.getSemail(),interest.getCemail());


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
                                InterestedCompany f3 = new InterestedCompany();
                                FragmentTransaction ft3 = getActivity().getSupportFragmentManager().beginTransaction();
                                ft3.replace(R.id.fragment_container,f3);
                                ft3.addToBackStack(null);
                                ft3.commit();
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
                recyclerView.setAdapter(interestCompanyAdapter);
            }

            @Override
            public void onFailure(Call<List<Interest>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }
    private void filter(String text) {
        ArrayList<Interest> filteredList = new ArrayList<Interest>();

        for (Interest item : interests) {
            if (item.getScompany().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        interestCompanyAdapter.filterList(filteredList);
    }
}
