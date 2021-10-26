package com.example.placementactivityapp.student;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
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

import com.example.placementactivityapp.POSO.SelectStudent;
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

import static android.content.Context.MODE_PRIVATE;

public class Show_Student extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private MyStudentAdapter myStudentAdapter;
EditText editText;
    List<Student> students = new ArrayList<Student>();

    String email = "";

    public Show_Student(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       super.onCreateView(inflater, container, savedInstanceState);
       return inflater.inflate(R.layout.activity_show__student, container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView)view.findViewById(R.id.rcv1);

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

        layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("mypref", MODE_PRIVATE);
        email = sharedPreferences.getString("email", "NO");

        Call<List<Student>> call = RetrofitClient
                .getInstance()
                .getApi()
                .showStudent(email);
        call.enqueue(new Callback<List<Student>>() {
            @Override
            public void onResponse(Call<List<Student>> call, Response<List<Student>> response) {
                students=response.body();
                myStudentAdapter = new MyStudentAdapter(students, getActivity().getApplicationContext(), new OnItemClickListener1() {
                    @Override
                    public void onItemClick1(final Student s) {

                        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("Choose the Option");

                        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                final String name, id, email, branch, cgpa, password;
                                name = s.getName();
                                id = s.getId();
                                email = s.getEmail();
                                branch = s.getBranch();
                                cgpa = s.getCgpa();
                                password = s.getPassword();


                                final EditText e1 ,e2, e4, e5, e6;
                                final TextView e3;
                                LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
                                View pview = layoutInflater.inflate(R.layout.update_student, null);


                                e1 = pview.findViewById(R.id.editText8);
                                e2 = pview.findViewById(R.id.editText9);
                                e3 = pview.findViewById(R.id.editText10);
                                e4 = pview.findViewById(R.id.editText11);
                                e5 = pview.findViewById(R.id.editText12);
                                e6 = pview.findViewById(R.id.editText13);

                                e1.setText(name);
                                e2.setText(id);
                                e3.setText(email);
                                e4.setText(branch);
                                e5.setText(cgpa);
                                e6.setText(password);

                                AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                                builder1.setView(pview);

                                builder1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Call<ResponseBody> call = RetrofitClient
                                                .getInstance()
                                                .getApi()
                                                .updatestudent(e1.getText().toString(), e2.getText().toString(), e3.getText().toString(), e4.getText().toString(), e5.getText().toString(), e6.getText().toString());
                                        call.enqueue(new Callback<ResponseBody>() {
                                            @Override
                                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                try{
                                                    String s = response.body().string();
                                                    Toast.makeText(getActivity().getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                                                }catch (IOException e){
                                                    e.printStackTrace();
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                                Toast.makeText(getActivity().getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                                            }
                                        });
                                        Show_Student f4=new Show_Student();
                                        FragmentTransaction ft4 = getActivity().getSupportFragmentManager(). beginTransaction();
                                        ft4.replace(R.id.fragment_container,f4);
                                        ft4.addToBackStack(null);
                                        ft4.commit();

                                    }
                                });
                                builder1.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                                AlertDialog alertDialog = builder1.create();
                                alertDialog.show();
                            }

                        });
                        builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Call<ResponseBody> call = RetrofitClient
                                        .getInstance()
                                        .getApi()
                                        .deletestudent(s.getEmail());


                                call.enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                        try {
                                            String s=response.body().string();
                                            Toast.makeText(getActivity().getApplicationContext(),s,Toast.LENGTH_LONG).show();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }

                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                                        Toast.makeText(getActivity().getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
                                    }
                                });
                                Show_Student f4=new Show_Student();
                                FragmentTransaction ft4 = getActivity().getSupportFragmentManager(). beginTransaction();
                                ft4.replace(R.id.fragment_container,f4);
                                ft4.addToBackStack(null);
                                ft4.commit();

                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                });
                recyclerView.setAdapter(myStudentAdapter);
            }

            @Override
            public void onFailure(Call<List<Student>> call, Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }
    private void filter(String text) {
        ArrayList<Student> filteredList = new ArrayList<Student>();

        for (Student item : students) {
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        myStudentAdapter.filterList(filteredList);
    }
}

