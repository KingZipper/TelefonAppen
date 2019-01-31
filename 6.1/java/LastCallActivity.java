package com.example.williamrudwall.nyatelefonappen;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telecom.Call;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class LastCallActivity extends Activity {

    private ArrayList<String> phoneNumbers = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_call);

        if (ContextCompat.checkSelfPermission(LastCallActivity.this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(LastCallActivity.this, Manifest.permission.READ_CALL_LOG)) {
                ActivityCompat.requestPermissions(LastCallActivity.this, new String[]{Manifest.permission.READ_CALL_LOG}, 1);
            } else {
                ActivityCompat.requestPermissions(LastCallActivity.this, new String[]{Manifest.permission.READ_CALL_LOG}, 1);
            }
        } else {
            // do stuff
            TextView textView = (TextView) findViewById(R.id.textViewen);
            textView.setText(getCallDetails());
            setupAdapter();
        }

    }

    // Metod för att fråga om tillstånd att använda data från samtalshistoriken
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(LastCallActivity.this, Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED) {

                        Toast.makeText(this, "Permission granted!", Toast.LENGTH_SHORT).show();
                        TextView textView = (TextView) findViewById(R.id.textViewen);
                        textView.setText(getCallDetails());
                        setupAdapter();

                    }
                } else {
                    Toast.makeText(this, "No permission GRANTED!", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    //Metod för att hämta numret från samtalshistoriken
    private String getCallDetails() {
        StringBuffer sb = new StringBuffer();
        Cursor managedCursor = getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, null);
        int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);

        while (managedCursor.moveToNext()) {
            String phNumber = managedCursor.getString(number);
                phoneNumbers.add(phNumber);
        }

        managedCursor.close();
        return sb.toString();

    }

    //Metod för att sätta upp Recyclervyn och tilldela adapter till vyn
    private void setupAdapter(){
        RecyclerView recyclerView = findViewById(R.id.recyclerViewen);
        LastCallAdapter lastCallAdapter = new LastCallAdapter(this, phoneNumbers);
        recyclerView.setAdapter(lastCallAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

}
