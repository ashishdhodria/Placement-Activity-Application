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
import android.widget.TextView;
import android.widget.Toast;

import com.example.placementactivityapp.POSO.Company;
import com.example.placementactivityapp.POSO.Student;
import com.example.placementactivityapp.R;
import com.example.placementactivityapp.RetrofitClient;
import com.example.placementactivityapp.listener.OnItemClickListener2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class ShowCompany extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ShowCompanyAdapter showCompanyAdapter;

    EditText editText;

    List<Company> companies = new ArrayList<Company>();
    public ShowCompany(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.activity_show_company, container,false);
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
        final String email = sharedPreferences.getString("email", "NO");
        final  String adkey = sharedPreferences.getString("adkey", "NO");
        Call<List<Company>> call = RetrofitClient
                .getInstance()
                .getApi()
                .showComp(email);
        call.enqueue(new Callback<List<Company>>() {
            @Override
            public void onResponse(final Call<List<Company>> call, Response<List<Company>> response) {
               companies = response.body();
                showCompanyAdapter = new ShowCompanyAdapter(companies, getActivity(), new OnItemClickListener2() {
                    @Override
                    public void onItemClick2(final Company company, String item) {

                        if (item.equalsIgnoreCase("show")){
                            LayoutInflater li = LayoutInflater.from(getActivity());
                            View v = li.inflate(R.layout.activity_show_company2, null);

                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setView(v);

                            TextView t1, t2 ,t3, t4 ,t5, t6;
                            t1 = v.findViewById(R.id.editText6);
                            t2 = v.findViewById(R.id.editText7);
                            t3 = v.findViewById(R.id.editText14);
                            t4 = v.findViewById(R.id.editText15);
                            t5 = v.findViewById(R.id.editText16);
                            t6 = v.findViewById(R.id.editText17);

                            t1.setText(company.getName());
                            t2.setText(company.getEmail());
                            t3.setText(company.getLocation());
                            t4.setText(company.getIndustry());
                            t5.setText(company.getPosition());
                            t6.setText(company.getRequirement());

                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                        }
                        else if(item.equalsIgnoreCase("update")){

                            LayoutInflater li = LayoutInflater.from(getActivity());
                            View v = li.inflate(R.layout.update_company, null);

                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setView(v);

                            final EditText t1,t3, t4 ,t5, t6;
                            final TextView t2;
                            t1 = v.findViewById(R.id.editText6);
                            t2 = v.findViewById(R.id.editText7);
                            t3 = v.findViewById(R.id.editText14);
                            t4 = v.findViewById(R.id.editText15);
                            t5 = v.findViewById(R.id.editText16);
                            t6 = v.findViewById(R.id.editText17);

                            t1.setText(company.getName());
                            t2.setText(company.getEmail());
                            t3.setText(company.getLocation());
                            t4.setText(company.getIndustry());
                            t5.setText(company.getPosition());
                            t6.setText(company.getRequirement());

                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    String name="",email="",location="", industry="", position="", requirement="";
                                    name = t1.getText().toString();
                                    email = t2.getText().toString();
                                    location = t3.getText().toString();
                                    industry = t4.getText().toString();
                                    position = t5.getText().toString();
                                    requirement = t6.getText().toString();

                                    Call<ResponseBody> call = RetrofitClient
                                            .getInstance()
                                            .getApi()
                                            .updatecompany(name, email, location, industry, position, requirement);


                                    call.enqueue(new Callback<ResponseBody>() {
                                        @Override
                                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                            try {
                                                String s = response.body().string();
                                                Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                                            Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
                                        }

                                    });

                                    ShowCompany f3=new ShowCompany(); //User defined fragment class
                                    FragmentTransaction ft3=getActivity().getSupportFragmentManager().beginTransaction();
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
                        else if(item.equalsIgnoreCase("delete")){

                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle("Delete");
                            builder.setMessage("Do you want to delete");
                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    Call<ResponseBody> call = RetrofitClient
                                            .getInstance()
                                            .getApi()
                                            .deletecompany(company.getEmail());


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

                                    Call<ResponseBody> call1 = RetrofitClient
                                            .getInstance()
                                            .getApi()
                                            .deleteinterest(company.getEmail());

                                    call1.enqueue(new Callback<ResponseBody>() {
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
                                            Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();;
                                        }
                                    });
                                    ShowCompany f3=new ShowCompany(); //User defined fragment class
                                    FragmentTransaction ft3=getActivity().getSupportFragmentManager().beginTransaction();
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

                        else if(item.equalsIgnoreCase("student")){


                            LayoutInflater li = LayoutInflater.from(getActivity());
                            View v = li.inflate(R.layout.activity_select_student, null);

                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setView(v);

                            final  EditText t1;
                            t1 = v.findViewById(R.id.et1);

                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String email1="";
                                    email1 = t1.getText().toString();

                                    Call<ResponseBody> call = RetrofitClient
                                            .getInstance()
                                            .getApi()
                                            .selectstudent(email1,email,adkey, company.getEmail());


                                    call.enqueue(new Callback<ResponseBody>() {
                                        @Override
                                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                            try {
                                                String s = response.body().string();
                                                Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                                            Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
                                        }

                                    });

                                    ShowCompany f3=new ShowCompany(); //User defined fragment class
                                    FragmentTransaction ft3=getActivity().getSupportFragmentManager().beginTransaction();
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
                    }
                });
                recyclerView.setAdapter(showCompanyAdapter);
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
        showCompanyAdapter.filterList(filteredList);
    }
}
