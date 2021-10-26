package com.example.placementactivityapp.tpo;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.placementactivityapp.R;
import com.example.placementactivityapp.RetrofitClient;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SaveNotification extends AppCompatActivity {

    EditText e1, e2;
    Button b1;

    String title="", description="", email="",adkey="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.getnotification);

        e1 = (EditText)findViewById(R.id.editText3);
        e2 = (EditText)findViewById(R.id.editText5);

        b1 = (Button)findViewById(R.id.button4);

        SharedPreferences sharedPreferences =getSharedPreferences("mypref1", MODE_PRIVATE);
        email = sharedPreferences.getString("email", "NO");
        adkey= sharedPreferences.getString("adkey", "NO");
        Toast.makeText(SaveNotification.this, email, Toast.LENGTH_LONG).show();
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = e1.getText().toString();
                description = e2.getText().toString();

                addNotification();

                Call<ResponseBody> call = RetrofitClient
                        .getInstance()
                        .getApi()
                        .addnotification(title, description,email,adkey);


                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            String s=response.body().string();
                            Toast.makeText(SaveNotification.this,s, Toast.LENGTH_LONG).show();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(SaveNotification.this,t.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
    public void addNotification(){
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_notifications_black_24dp)//set icon for notification
                        .setContentTitle(e1.getText().toString()) //set title of notification
                        .setContentText(e2.getText().toString())//this is notification message
                        .setAutoCancel(true) // makes auto cancel of notification
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT); //set priority of notification


        Intent notificationIntent = new Intent(this, NotificationView.class);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //notification message will get at NotificationView
        notificationIntent.putExtra("message", e2.getText().toString());

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
        e1.setText("");
        e2.setText("");
    }
}
