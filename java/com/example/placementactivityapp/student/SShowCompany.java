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

import com.example.placementactivityapp.POSO.Company;
import com.example.placementactivityapp.POSO.PDF;
import com.example.placementactivityapp.R;
import com.example.placementactivityapp.RetrofitClient;
import com.example.placementactivityapp.listener.OnItemClickListener2;
import com.example.placementactivityapp.tpo.ShowCompany;
import com.example.placementactivityapp.tpo.ShowCompanyAdapter;
import com.example.placementactivityapp.tpo.ShowSeStudent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class SShowCompany extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    SShowCompanyAdapter sshowCompanyAdapter;
    EditText editText;
    List<Company> companies = new ArrayList<Company>();
    public SShowCompany(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.activity_show_company, container, false);
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
        Call<List<Company>> call = RetrofitClient
                .getInstance()
                .getApi()
                .sshowComp(adkey,email);
        call.enqueue(new Callback<List<Company>>() {
            @Override
            public void onResponse(Call<List<Company>> call, Response<List<Company>> response) {
                companies = response.body();
                sshowCompanyAdapter = new SShowCompanyAdapter(companies, getActivity(), new OnItemClickListener2() {
                    @Override
                    public void onItemClick2(final Company company, String item) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("Interest");
                        builder.setMessage("Are you Intrested in this company.");

                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Call<ResponseBody> call = RetrofitClient
                                        .getInstance()
                                        .getApi()
                                        .interestcompany(name,email,company.getName(),company.getEmail(),adkey,company.getPosition());


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

                                SShowCompany f4 = new SShowCompany();
                                FragmentTransaction ft4 = getActivity().getSupportFragmentManager().beginTransaction();
                                ft4.replace(R.id.fragment_container,f4);
                                ft4.addToBackStack(null);
                                ft4.commit();

                            }
                        });
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();

                    }
                });
                recyclerView.setAdapter(sshowCompanyAdapter);
            }

            @Override
            public void onFailure(Call<List<Company>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }
    private void filter(String text) {
        ArrayList<Company> filteredList = new ArrayList<Company>();

        for (Company item : companies) {
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        sshowCompanyAdapter.filterList(filteredList);
    }
}
