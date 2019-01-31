package com.example.williamrudwall.nyatelefonappen;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
    EditText phoneNumber;
    Button call_button, lastCall_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        phoneNumber = (EditText) findViewById(R.id.editText1);
        call_button = (Button) findViewById(R.id.button1);
        lastCall_button = (Button) findViewById(R.id.lastCall);


// Knapp för att ta sig till samtalshistoriken
        lastCall_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, new LastCallActivity().getClass());
                startActivity(i);
            }
        });

        // knapp för att ringa nummret man skrivit in
        call_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tele = new Intent(Intent.ACTION_DIAL);
               tele = tele.setData(Uri.parse("tel:"+phoneNumber.getText().toString()));
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    startActivity(tele);
                    return;
                }


            }
        });
    }
}
