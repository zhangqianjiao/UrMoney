package com.example.pipid.urmoney;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import android.util.Log;

import com.google.gson.reflect.TypeToken;
import org.json.JSONObject;
import com.google.gson.Gson;


import org.json.JSONObject;
import org.w3c.dom.Text;

import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.TreeMap;


public class CurrentActivity extends Activity {
    private TextView usd,eur,cny;
    private Button button;
    private EditText edit;
    private Spinner spin;
    private TextView et;
    private static RequestQueue requestQueue;
    private TreeMap<String, Double> treeMap = new TreeMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.currency_main);
        requestQueue = Volley.newRequestQueue(this);
        usd = (TextView) findViewById(R.id.usd);
        button = (Button) findViewById(R.id.btn);
        et = (TextView) findViewById(R.id.et);

        startAPICall();
        Button convert = (Button) findViewById(R.id.btn);
        convert.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(CurrentActivity.this, MainActivity.class);
                //startActivity(intent);

                Double a = Double.valueOf(et.getText().toString());
                Double b = a / treeMap.get("USD");
                usd.setText(new DecimalFormat("#0.00").format(Double.valueOf(b.toString())));
            }
        });
        Button back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CurrentActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    void startAPICall() {
        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                "http://data.fixer.io/api/latest?access_key=61841aaaa4df6dcf00c3465031cf9074",
                null,
                new Response.Listener<JSONObject>() {
                    public void onResponse(final JSONObject response) {
                        //@Override
                        //data = new Computation(response);
                        Gson gson = new Gson();
                        LALA lala = gson.fromJson(response.toString(), LALA.class);
                        treeMap = new TreeMap<>(lala.rates);
                        Log.d("s", lala.rates.toString());
                    }
                }, new Response.ErrorListener() {
                    @Override
                public void onErrorResponse(final VolleyError error) {
                        Log.w("lalala", error.toString());
                }
            });
            requestQueue.add(jsonObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
