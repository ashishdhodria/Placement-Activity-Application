package com.example.placementactivityapp.admin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.placementactivityapp.MainActivity;
import com.example.placementactivityapp.R;
import com.example.placementactivityapp.student.AddStudent;
import com.example.placementactivityapp.student.Show_Student;
import com.example.placementactivityapp.tpo.AddTpo;
import com.example.placementactivityapp.tpo.Show_Tpo;

public class AdminMain extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.addstudent:
                    AddStudent f1 = new AddStudent();
                    FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
                    ft1.replace(R.id.fragment_container,f1);
                    ft1.addToBackStack(null);
                    ft1.commit();
                    return true;

                case R.id.addtpo:
                    AddTpo f2=new AddTpo();
                    FragmentTransaction ft2 = getSupportFragmentManager(). beginTransaction();
                    ft2.replace(R.id.fragment_container,f2);
                    ft2.addToBackStack(null);
                    ft2.commit();
                    return true;

                case R.id.TPO:

                    Show_Tpo f3 = new Show_Tpo();
                    FragmentTransaction ft3 = getSupportFragmentManager().beginTransaction();
                    ft3.replace(R.id.fragment_container, f3);
                    ft3.addToBackStack(null);
                    ft3.commit();
                    return true;
                case R.id.STUDENT:
                    Show_Student f4=new Show_Student();
                    FragmentTransaction ft4 = getSupportFragmentManager(). beginTransaction();
                    ft4.replace(R.id.fragment_container,f4);
                    ft4.addToBackStack(null);
                    ft4.commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        AddStudent f1 = new AddStudent();
        FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
        ft1.replace(R.id.fragment_container,f1);
        ft1.addToBackStack(null);
        ft1.commit();
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

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
                SharedPreferences sharedPreferences = getSharedPreferences("mypref",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("email");
                editor.commit();
                startActivity(new Intent(AdminMain.this, MainActivity.class));
                return true;
        }
        return false;
    }
}
