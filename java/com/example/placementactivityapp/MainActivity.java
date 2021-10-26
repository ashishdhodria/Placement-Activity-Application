package com.example.placementactivityapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.placementactivityapp.POSO.Admin;
import com.example.placementactivityapp.POSO.Student;
import com.example.placementactivityapp.POSO.Tpo;
import com.example.placementactivityapp.admin.AdminMain;
import com.example.placementactivityapp.admin.SignUp;
import com.example.placementactivityapp.student.Student_main;
import com.example.placementactivityapp.tpo.TpoMain;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Button b1, b2;
    EditText e1, e2;
    Spinner sp;

    String seleceted_item = "";
    String username="", password="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        e1 = (EditText)findViewById(R.id.editText1);
        e2 = (EditText)findViewById(R.id.editText2);

        b1 = (Button)findViewById(R.id.login);
        b2 = (Button)findViewById(R.id.signup);

        sp = (Spinner)findViewById(R.id.spinner1);

        List<String> op = new ArrayList<String>();
        op.add("Admin");
        op.add("Tpo");
        op.add("Student");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, op);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapter);

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                seleceted_item = sp.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                seleceted_item = "Admin";
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username = e1.getText().toString();
                password = e2.getText().toString();


                if (seleceted_item.equalsIgnoreCase("Admin")) {
                    Call<List<Admin>> call = RetrofitClient
                            .getInstance()
                            .getApi()
                            .getAdmin(username, password);
                    call.enqueue(new Callback<List<Admin>>() {
                        @Override
                        public void onResponse(Call<List<Admin>> call, Response<List<Admin>> response) {
                            List<Admin> admins=response.body();
                            String email="";
                            for (Admin s:admins) {
                                email = s.getEmail();
                            }
                            SharedPreferences sharedPreferences = getSharedPreferences("mypref", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.clear();
                            editor.putString("email", email);
                            editor.commit();
                            startActivity( new Intent(MainActivity.this, AdminMain.class));
                        }

                        @Override
                        public void onFailure(Call<List<Admin>> call, Throwable t) {
                            Toast.makeText(MainActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    });


                }
                else if (seleceted_item.equalsIgnoreCase("Tpo")) {
                    Call<List<Tpo>> call = RetrofitClient
                            .getInstance()
                            .getApi()
                            .getTpo(username, password);
                    call.enqueue(new Callback<List<Tpo>>() {
                        @Override
                        public void onResponse(Call<List<Tpo>> call, Response<List<Tpo>> response) {
                            List<Tpo> tpos=response.body();
                            String email="", adkey="";
                            for (Tpo s:tpos) {
                                email = s.getEmail();
                                adkey = s.getAdkey();
                            }
                            SharedPreferences sharedPreferences = getSharedPreferences("mypref1", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.clear();
                            editor.putString("email", email);
                            editor.putString("adkey", adkey);
                            editor.commit();
                            startActivity( new Intent(MainActivity.this, TpoMain.class));

                        }

                        @Override
                        public void onFailure(Call<List<Tpo>> call, Throwable t) {
                            Toast.makeText(MainActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    });
                }
                else if (seleceted_item.equalsIgnoreCase("Student")) {
                    Call<List<Student>> call = RetrofitClient
                            .getInstance()
                            .getApi()
                            .getStudent(username, password);
                    call.enqueue(new Callback<List<Student>>() {
                        @Override
                        public void onResponse(Call<List<Student>> call, Response<List<Student>> response) {
                            List<Student> students=response.body();
                            String email="";
                            String adkey="";
                            String name="";
                            for (Student s:students) {
                                email = s.getEmail();
                                adkey = s.getAdkey();
                                name = s.getName();
                            }
                            SharedPreferences sharedPreferences = getSharedPreferences("mypref2", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.clear();
                            editor.putString("email", email);
                            editor.putString("adkey", adkey);
                            editor.putString("name", name);
                            editor.commit();
                            startActivity(new Intent(MainActivity.this, Student_main.class));
                        }

                        @Override
                        public void onFailure(Call<List<Student>> call, Throwable t) {
                            Toast.makeText(MainActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SignUp.class));
            }
        });
    }
}
