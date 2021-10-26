package com.example.placementactivityapp.tpo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.placementactivityapp.FilePath;
import com.example.placementactivityapp.R;
import com.example.placementactivityapp.VolleyMultipartRequest;

import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class AddPdf extends AppCompatActivity {

    private final static int PICK_FILE_REQUEST =1;
    private String selectedFilePath = null;
    private String SERVER_URL="http://10.0.2.2/college/upload_pdf.php";

    ImageView img;
    TextView tv;
    EditText et;
    Button bt;

    String email, adkey, title="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pdf);


        img = findViewById(R.id.imageView3);
        tv = findViewById(R.id.tv1);
        et = findViewById(R.id.et1);
        bt = findViewById(R.id.bt1);

        SharedPreferences preferences = getSharedPreferences("mypref1", MODE_PRIVATE);
        email = preferences.getString("email", "NO");
        adkey = preferences.getString("adkey", "NO");

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("application/pdf");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Choose File to Upload.."), PICK_FILE_REQUEST);
            }
        });

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                title=et.getText().toString();

                if (selectedFilePath != null) {
                    Toast.makeText(AddPdf.this,"choosed file", Toast.LENGTH_LONG).show();
                    uploadFile(selectedFilePath);
                } else {
                    Toast.makeText(AddPdf.this, "Please choose a File First", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public  void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PICK_FILE_REQUEST) {
                if (data == null) {
                    //no data present
                    return;
                }

                Uri selectedFileUri = data.getData();
                selectedFilePath = FilePath.getPath(AddPdf.this, selectedFileUri);

                if (selectedFilePath != null && !selectedFilePath.equals("")) {
                    tv.setText(selectedFilePath);
                } else {
                    Toast.makeText(AddPdf.this, "Cannot upload file to server", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void uploadFile(String filepath) {
        byte[] byteArray = null;
        try {
            File file = new File(filepath);
            byteArray = new byte[(int) file.length()];
            byteArray = FileUtils.readFileToByteArray(file);


        } catch (Exception e) {
            e.printStackTrace();

        }

        if (byteArray != null) {
            final byte[] finalByteArray = byteArray;
            VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, SERVER_URL,
                    new Response.Listener<NetworkResponse>() {
                        @Override
                        public void onResponse(NetworkResponse response) {
                            try {
                                JSONObject obj = new JSONObject(new String(response.data));
                                Toast.makeText(AddPdf.this, obj.getString("message"), Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(AddPdf.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }) {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();

                    params.put("title", title);
                    params.put("email", email);
                    params.put("adkey", adkey);
                    return params;
                }

                @Override
                public Map<String, DataPart> getByteData() throws AuthFailureError{
                    Map<String, DataPart> params = new HashMap<>();
                    long imagename = System.currentTimeMillis();
                    params.put("pic", new DataPart(imagename+".pdf", finalByteArray));
                    return params;
                }
            };

            //adding the request to volley
            Volley.newRequestQueue(AddPdf.this).add(volleyMultipartRequest);
        }
        else
        {
            Toast.makeText(AddPdf.this,"Cannot upload file",Toast.LENGTH_SHORT).show();
        }
    }


}
