package com.apps.ahfreelancing.caffetask.presentation.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.apps.ahfreelancing.caffetask.R;

public class ConnectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);
        setTitle("تأكد من إتصالك بالأنترنت");
    }
}
