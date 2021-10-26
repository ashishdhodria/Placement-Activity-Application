package com.example.placementactivityapp.student;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.placementactivityapp.POSO.PDF;
import com.example.placementactivityapp.R;
import com.example.placementactivityapp.RetrofitClient;
import com.example.placementactivityapp.listener.OnItemClickListener6;
import com.example.placementactivityapp.tpo.MyPdfAdapter;
import com.example.placementactivityapp.tpo.ShowPdf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class SShow_Pdf extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    MyPdfAdapter myPdfAdapter;

    EditText editText;
    List<PDF> pdfs = new ArrayList<PDF>();

    public SShow_Pdf() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.activity_show_company, container, false);
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
        layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("mypref2", MODE_PRIVATE);
        final String email = sharedPreferences.getString("email", "NO");
        final String adkey = sharedPreferences.getString("adkey", "NO");

        Call<List<PDF>> call = RetrofitClient
                .getInstance()
                .getApi()
                .sshowpdf(adkey);
        call.enqueue(new Callback<List<PDF>>() {
            @Override
            public void onResponse(Call<List<PDF>> call, Response<List<PDF>> response) {
                pdfs = response.body();
                myPdfAdapter = new MyPdfAdapter(pdfs, getActivity(), new OnItemClickListener6() {
                    @Override
                    public void onItemClick6(final PDF pdf) {
                        final String imgname = pdf.getPdf();
                        String file_ext = imgname.substring(imgname.length() - 3);

                        if (file_ext.equalsIgnoreCase("pdf")) {
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(imgname));
                            startActivity(browserIntent);
                        } else {
                            View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.photo_detail, null);
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());

                            builder1.setView(view1);
                            builder1.setTitle(pdf.getTitle());

                            ImageView img = view1.findViewById(R.id.imageView5);
                            Glide.with(img.getContext()).load(imgname).into(img);

                            builder1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            builder1.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            AlertDialog alertDialog1 = builder1.create();
                            alertDialog1.show();

                        }
                    }
                });
                recyclerView.setAdapter(myPdfAdapter);
            }

            @Override
            public void onFailure(Call<List<PDF>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    private void filter(String text) {
        ArrayList<PDF> filteredList = new ArrayList<PDF>();

        for (PDF item : pdfs) {
            if (item.getTitle().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        myPdfAdapter.filterList(filteredList);
    }
}
