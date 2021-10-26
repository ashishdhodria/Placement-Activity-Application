package com.example.placementactivityapp.admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.placementactivityapp.MainActivity;
import com.example.placementactivityapp.R;
import com.example.placementactivityapp.RetrofitClient;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp extends AppCompatActivity {

    String username="", email="", password="";
    EditText e1, e2, e3;
    Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signupadmin);

        e1 = (EditText)findViewById(R.id.editText);
        e2 = (EditText)findViewById(R.id.editText2);
        e3 = (EditText)findViewById(R.id.editText4);

        b1 = (Button)findViewById(R.id.button);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = e1.getText().toString();
                email = e2.getText().toString();
                password = e3.getText().toString();

                Call<ResponseBody> call = RetrofitClient
                        .getInstance()
                        .getApi()
                        .addadmin(username, email, password);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try{
                            String s = response.body().string();
                            Toast.makeText(SignUp.this, s, Toast.LENGTH_SHORT).show();
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(SignUp.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

                e1.setText("");
                e2.setText("");
                e3.setText("");
                startActivity(new Intent(SignUp.this, MainActivity.class));
            }

        });
    }
}
