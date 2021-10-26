package com.example.placementactivityapp.tpo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.placementactivityapp.R;

public class NotificationView extends AppCompatActivity {
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_notifi);
        textView = findViewById(R.id.textView8);
        //getting the notification message
        String message=getIntent().getStringExtra("message");
        textView.setText(message);
    }



}
