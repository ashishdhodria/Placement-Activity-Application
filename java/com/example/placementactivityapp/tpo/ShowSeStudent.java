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
import com.example.placementactivityapp.POSO.SelectStudent;
import com.example.placementactivityapp.POSO.Student;
import com.example.placementactivityapp.R;
import com.example.placementactivityapp.RetrofitClient;
import com.example.placementactivityapp.listener.OnItemClickListener3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class ShowSeStudent extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    SelectedStudentAdapter selectedStudentAdapter;
    EditText editText;
    List<SelectStudent> selectStudents = new ArrayList<SelectStudent>();
    public ShowSeStudent(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.activity_show__student, container, false);
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
        String email = sharedPreferences.getString("email", "NO");

        Call<List<SelectStudent>> call = RetrofitClient
                .getInstance()
                .getApi()
                .getsestudent(email);
        call.enqueue(new Callback<List<SelectStudent>>() {
            @Override
            public void onResponse(Call<List<SelectStudent>> call, Response<List<SelectStudent>> response) {
                selectStudents = response.body();
                selectedStudentAdapter  = new SelectedStudentAdapter(selectStudents, getActivity(), new OnItemClickListener3() {
                    @Override
                    public void onItemClick3(final SelectStudent selectStudent, String item) {

                        if (item.equalsIgnoreCase("company")){


                            View view = LayoutInflater.from(getActivity()).inflate(R.layout.activity_show_company2,null);
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setView(view);


                            final TextView t11, t12, t13, t14, t15, t16;

                            t11 = view.findViewById(R.id.editText6);
                            t12 = view.findViewById(R.id.editText7);
                            t13 = view.findViewById(R.id.editText14);
                            t14 = view.findViewById(R.id.editText15);
                            t15 = view.findViewById(R.id.editText16);
                            t16 = view.findViewById(R.id.editText17);

                            Call<List<Company>> call = RetrofitClient
                                    .getInstance()
                                    .getApi()
                                    .showComp1(selectStudent.getCemail());
                            call.enqueue(new Callback<List<Company>>() {
                                @Override
                                public void onResponse(Call<List<Company>> call, Response<List<Company>> response) {
                                    List<Company> companies = response.body();
                                    Company c = companies.get(0);
                                    t11.setText(c.getName());
                                    t12.setText(c.getEmail());
                                    t13.setText(c.getLocation());
                                    t14.setText(c.getIndustry());
                                    t15.setText(c.getPosition());
                                    t16.setText(c.getRequirement());
                                }
                                @Override
                                public void onFailure(Call<List<Company>> call, Throwable t) {
                                    Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
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


                            View view = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.student_showdetail1,null);
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setView(view);
                            final TextView t1, t2, t3, t4, t5;
                            t1 = view.findViewById(R.id.name);
                            t2 = view.findViewById(R.id.email);
                            t3 = view.findViewById(R.id.id);
                            t4 = view.findViewById(R.id.cgpa);
                            t5 = view.findViewById(R.id.branch);

                            Call<List<Student>> call = RetrofitClient
                                    .getInstance()
                                    .getApi()
                                    .showStudent1(selectStudent.getEmail());
                            call.enqueue(new Callback<List<Student>>() {
                                @Override
                                public void onResponse(Call<List<Student>> call, Response<List<Student>> response) {
                                    List<Student> students = response.body();
                                    Student s1 = students.get(0);
                                    t1.setText(s1.getName());
                                    t2.setText(s1.getEmail());
                                    t3.setText(s1.getId());
                                    t4.setText(s1.getCgpa());
                                    t5.setText(s1.getBranch());
                                }

                                @Override
                                public void onFailure(Call<List<Student>> call, Throwable t) {
                                    Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
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
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Call<ResponseBody> call = RetrofitClient
                                            .getInstance()
                                            .getApi()
                                            .deletesestudent(selectStudent.getEmail(),selectStudent.getCemail());

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
                                    ShowSeStudent f5=new ShowSeStudent(); //User defined fragment class
                                    FragmentTransaction ft5=getActivity().getSupportFragmentManager().beginTransaction();
                                    ft5.replace(R.id.fragment_container,f5);
                                    ft5.addToBackStack(null);
                                    ft5.commit();
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
                recyclerView.setAdapter(selectedStudentAdapter);

            }

            @Override
            public void onFailure(Call<List<SelectStudent>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void filter(String text) {
        ArrayList<SelectStudent> filteredList = new ArrayList<SelectStudent>();

        for (SelectStudent item : selectStudents) {
            if (item.getEmail().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        selectedStudentAdapter.filterList(filteredList);
    }
}
