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

import org.w3c.dom.Text;

import java.nio.charset.Charset;


public class CurrentActivity extends Activity {
    private TextView usd,eur,cny;
    private Button button;
    private EditText edit;
    private Spinner spin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.currency_main);
        usd = (TextView) findViewById(R.id.usd);
        eur = (TextView) findViewById(R.id.euro);
        cny = (TextView) findViewById(R.id.cny);
        button = (Button) findViewById(R.id.btn);
        spin = (Spinner) findViewById(R.id.spin);

        ArrayAdapter <CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.currency, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spin.setAdapter(adapter);

        Button back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CurrentActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

}
