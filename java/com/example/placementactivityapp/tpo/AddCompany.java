package com.example.placementactivityapp.tpo;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.placementactivityapp.R;
import com.example.placementactivityapp.RetrofitClient;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCompany extends AppCompatActivity {

    String name = "", email = "", location = "", industry = "", position = "", requirement = "";

    EditText e1, e2, e3, e4, e5, e6;
    Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_company);

        e1 = findViewById(R.id.editText6);
        e2 = findViewById(R.id.editText7);
        e3 = findViewById(R.id.editText14);
        e4 = findViewById(R.id.editText15);
        e5 = findViewById(R.id.editText16);
        e6 = findViewById(R.id.editText17);

        b1 = findViewById(R.id.button5);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = e1.getText().toString();
                email = e2.getText().toString();
                location = e3.getText().toString();
                industry = e4.getText().toString();
                position = e5.getText().toString();
                requirement = e6.getText().toString();

                SharedPreferences sharedPreferences = getSharedPreferences("mypref1", MODE_PRIVATE);

                String tkey = sharedPreferences.getString("email", "NO");
                String akey = sharedPreferences.getString("adkey", "NO");

                Call<ResponseBody> call = RetrofitClient
                        .getInstance()
                        .getApi()
                        .addcompany(name, email, location, industry, position, requirement, tkey, akey);


                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            String s = response.body().string();
                            Toast.makeText(AddCompany.this, s, Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(AddCompany.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }

                });
                e1.setText("");
                e2.setText("");
                e3.setText("");
                e4.setText("");
                e5.setText("");
                e6.setText("");
            }
        });

    }
}