package com.example.placementactivityapp.student;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import android.widget.Toast;

import com.example.placementactivityapp.POSO.Interest;
import com.example.placementactivityapp.POSO.SelectStudent;
import com.example.placementactivityapp.R;
import com.example.placementactivityapp.RetrofitClient;
import com.example.placementactivityapp.tpo.SelectedStudentAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class SShow_Selected_Student extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    SSelectedStudentAdapter sselectedStudentAdapter;

    List<SelectStudent> selectStudents = new ArrayList<SelectStudent>();
    EditText editText;
    public SShow_Selected_Student(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return  inflater.inflate(R.layout.activity_sshow__selected__student, container, false);
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

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("mypref2", MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "NO");
        String adkey = sharedPreferences.getString("adkey", "NO");

        Call<List<SelectStudent>> call = RetrofitClient
                .getInstance()
                .getApi()
                .sgetsestudent(adkey);

        call.enqueue(new Callback<List<SelectStudent>>() {
            @Override
            public void onResponse(Call<List<SelectStudent>> call, Response<List<SelectStudent>> response) {
                selectStudents = response.body();
                sselectedStudentAdapter = new SSelectedStudentAdapter(selectStudents, getActivity());
                recyclerView.setAdapter(sselectedStudentAdapter);
            }

            @Override
            public void onFailure(Call<List<SelectStudent>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
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
        sselectedStudentAdapter.filterList(filteredList);
    }
}

