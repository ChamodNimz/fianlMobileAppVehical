package com.example.acer.vehiclepersonalassistant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {


        Button Add, Edit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Add = findViewById(R.id.btnApt);
        Edit = findViewById(R.id.btnEdit);

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent goToNextActivity = new Intent(MainActivity.this,makeAppointment.class);
                startActivity(goToNextActivity);
            }
        });

        Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent goToNextActivity1 = new Intent(MainActivity.this,EditAppointment.class);
                startActivity(goToNextActivity1);
            }
        });
    }
}
