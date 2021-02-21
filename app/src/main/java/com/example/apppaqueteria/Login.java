package com.example.apppaqueteria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Login extends AppCompatActivity {
    EditText user,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        user=findViewById(R.id.user);
        password=findViewById(R.id.password);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }
    public void Enviar(View view){


        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://192.168.0.4/Paqueteria/WebPaqueteria/public/api/login/"+user.getText()+"/"+password.getText()+"";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.

                        Log.i("response","Response is: "+ response);
                        try {
                            JSONArray jsonArray=new JSONArray(response);
                            if(jsonArray.length()>0){
                                JSONObject jsonObject=new JSONObject(jsonArray.get(0).toString());
                                startActivity(new Intent(getApplicationContext(),MainActivity.class).putExtra("nombre",jsonObject.getString("nombres")+" "+jsonObject.getString("apellidopaterno")));
                            }else{
                                Toast.makeText(Login.this, "Error de datos.", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("response","That didn't work!"+error);
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);


    }
}