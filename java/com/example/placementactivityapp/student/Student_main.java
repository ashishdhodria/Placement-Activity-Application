package com.example.placementactivityapp.student;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.placementactivityapp.MainActivity;
import com.example.placementactivityapp.R;


public class Student_main extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.notification:
                    SShow_Notifi f1 = new SShow_Notifi();
                    FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
                    ft1.replace(R.id.fragment_container,f1);
                    ft1.addToBackStack(null);
                    ft1.commit();
                    return true;

                case R.id.student:
                    SShow_Selected_Student f2 = new SShow_Selected_Student();
                    FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
                    ft2.replace(R.id.fragment_container,f2);
                    ft2.addToBackStack(null);
                    ft2.commit();
                    return true;

                case R.id.interested:
                    InterestedCompany f3 = new InterestedCompany();
                    FragmentTransaction ft3 = getSupportFragmentManager().beginTransaction();
                    ft3.replace(R.id.fragment_container,f3);
                    ft3.addToBackStack(null);
                    ft3.commit();
                     return true;
                case R.id.company:
                    SShowCompany f4 = new SShowCompany();
                    FragmentTransaction ft4 = getSupportFragmentManager().beginTransaction();
                    ft4.replace(R.id.fragment_container,f4);
                    ft4.addToBackStack(null);
                    ft4.commit();
                    return true;
                case R.id.document:
                    SShow_Pdf f5 = new SShow_Pdf();
                    FragmentTransaction ft5 = getSupportFragmentManager().beginTransaction();
                    ft5.replace(R.id.fragment_container,f5);
                    ft5.addToBackStack(null);
                    ft5.commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main);

        BottomNavigationView navView = findViewById(R.id.nav_view1);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        SShow_Notifi f1 = new SShow_Notifi();
        FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
        ft1.replace(R.id.fragment_container,f1);
        ft1.addToBackStack(null);
        ft1.commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case R.id.logout:
                SharedPreferences sharedPreferences = getSharedPreferences("mypref2",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("email");
                editor.remove("adkey");
                editor.remove("name");
                editor.commit();
                startActivity(new Intent(Student_main.this, MainActivity.class));
                return true;
        }
        return false;
    }
}

