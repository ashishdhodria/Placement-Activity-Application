package com.example.placementactivityapp.tpo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.placementactivityapp.R;
import com.example.placementactivityapp.VolleyMultipartRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AddImage extends AppCompatActivity {

    ImageView img;
    Button b1, b2, b3;
    EditText et1;
    String email,adkey,title="";
    private String SERVER_URL="http://10.0.2.2/college/upload_pdf.php";
    Bitmap bitmap=null;
    private int flag=0;

    private final static int MY_CAMERA_REQUEST_CODE=200;
    private static final int PERMISSION_CODE=11;

    private String mediaPath;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==0 && resultCode==RESULT_OK && data!=null) {
            bitmap = (Bitmap) data.getExtras().get("data");
            img.setImageBitmap(bitmap);
            flag=1;
        }
        else if (resultCode==RESULT_OK && requestCode==101) {
            Uri filePath = data.getData();
            try {
                bitmap = (Bitmap) MediaStore.Images.Media.getBitmap(AddImage.this.getContentResolver(), filePath);
                img.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_image);

        img = findViewById(R.id.imageView4);
        b1 = findViewById(R.id.button7);
        b2 = findViewById(R.id.button8);
        b3 = findViewById(R.id.button9);
        et1 = findViewById(R.id.editText18);

        SharedPreferences preferences = getSharedPreferences("mypref1", MODE_PRIVATE);
        email = preferences.getString("email", "NO");
        adkey = preferences.getString("adkey", "NO");

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i,101);
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title=et1.getText().toString();


                if(title==null)
                {
                    Toast.makeText(AddImage.this,"Entertitle",Toast.LENGTH_SHORT).show();
                }
                else {
                    if(bitmap==null)
                    {
                        Toast.makeText(AddImage.this,"No image selected",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        uploadBitmap(bitmap);
                     }
                }
            }
        });
    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try
        {
            bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        }
        catch (java.lang.NullPointerException rr)
        {
            rr.printStackTrace();
        }
        return byteArrayOutputStream.toByteArray();
    }
    private void uploadBitmap(final Bitmap bitmap) {

        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, SERVER_URL,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            JSONObject obj = new JSONObject(new String(response.data));
                            Toast.makeText(AddImage.this.getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AddImage.this.getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
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
            public Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("pic", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                return params;
            }
        };

        //adding the request to volley
        Volley.newRequestQueue(AddImage.this.getApplicationContext()).add(volleyMultipartRequest);
    }


    public static boolean hasPermissions(Context context, String... permissions){
        if(context!=null && permissions!=null){
            for(String permission : permissions){
                if(ActivityCompat.checkSelfPermission(context,permission)!= PackageManager.PERMISSION_GRANTED){
                    return false;
                }
            }
        }
        return true;
    }

    @Override

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_REQUEST_CODE) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(AddImage.this, "camera permission granted", Toast.LENGTH_LONG).show();

            } else {

                Toast.makeText(AddImage.this, "camera permission denied", Toast.LENGTH_LONG).show();

            }
        }
        else if (requestCode == PERMISSION_CODE) {

            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(AddImage.this, "gallery permission granted", Toast.LENGTH_LONG).show();

            } else {

                Toast.makeText(AddImage.this, "gallery permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }



}
