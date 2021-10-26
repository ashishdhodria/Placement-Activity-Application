package com.example.placementactivityapp.tpo;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.placementactivityapp.POSO.PDF;
import com.example.placementactivityapp.POSO.Student;
import com.example.placementactivityapp.R;
import com.example.placementactivityapp.RetrofitClient;
import com.example.placementactivityapp.listener.OnItemClickListener6;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class ShowPdf extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    MyPdfAdapter myPdfAdapter;
    EditText editText;

    List<PDF> pdfs = new ArrayList<PDF>();

    public ShowPdf(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.activity_show_company, container,false);
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

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("mypref1", MODE_PRIVATE);
        final String email = sharedPreferences.getString("email", "NO");
        final  String adkey = sharedPreferences.getString("adkey", "NO");

        Call<List<PDF>> call = RetrofitClient
                .getInstance()
                .getApi()
                .showpdf(email);
        call.enqueue(new Callback<List<PDF>>() {
            @Override
            public void onResponse(Call<List<PDF>> call, Response<List<PDF>> response) {
                pdfs = response.body();
                myPdfAdapter = new MyPdfAdapter(pdfs, getActivity(), new OnItemClickListener6() {
                    @Override
                    public void onItemClick6(final PDF pdf) {
                        final String imgname = pdf.getPdf();
                        String file_ext = imgname.substring(imgname.length() - 3);

                        if (file_ext.equalsIgnoreCase("pdf")){
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle("Option");
                            builder.setMessage("Select the Option");

                            builder.setPositiveButton("Open", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(imgname));
                                    startActivity(browserIntent);

                                }
                            });
                            builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Call<ResponseBody> call = RetrofitClient
                                            .getInstance()
                                            .getApi()
                                            .deletepdf(email,pdf.getPdf());



                                    call.enqueue(new Callback<ResponseBody>() {
                                        @Override
                                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                            try {
                                                String s=response.body().string();
                                                Toast.makeText(getActivity(),s,Toast.LENGTH_LONG).show();
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }

                                        }

                                        @Override
                                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                                            Toast.makeText(getActivity(),t.getMessage(),Toast.LENGTH_LONG).show();
                                        }
                                    });
                                    ShowPdf f4=new ShowPdf(); //User defined fragment class
                                    FragmentTransaction ft4=getActivity().getSupportFragmentManager().beginTransaction();
                                    ft4.replace(R.id.fragment_container,f4);
                                    ft4.addToBackStack(null);
                                    ft4.commit();


                                }
                            });
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                        }
                        else {
                            final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle("Option");
                            builder.setMessage("Select the Option");

                            builder.setPositiveButton("Show", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    View view = LayoutInflater.from(getActivity()).inflate(R.layout.photo_detail,null);
                                    AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());

                                    builder1.setView(view);
                                    builder1.setTitle(pdf.getTitle());

                                    ImageView img = view.findViewById(R.id.imageView5);
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
                            });
                            builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Call<ResponseBody> call = RetrofitClient
                                            .getInstance()
                                            .getApi()
                                            .deletepdf(email,imgname);


                                    call.enqueue(new Callback<ResponseBody>() {
                                        @Override
                                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                            try {
                                                String s=response.body().string();
                                                Toast.makeText(getActivity(),s,Toast.LENGTH_LONG).show();
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }

                                        }

                                        @Override
                                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                                            Toast.makeText(getActivity(),t.getMessage(),Toast.LENGTH_LONG).show();
                                        }
                                    });
                                    ShowPdf f4=new ShowPdf(); //User defined fragment class
                                    FragmentTransaction ft4=getActivity().getSupportFragmentManager().beginTransaction();
                                    ft4.replace(R.id.fragment_container,f4);
                                    ft4.addToBackStack(null);
                                    ft4.commit();
                                }
                            });
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
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
