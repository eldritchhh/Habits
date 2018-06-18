package com.example.android.habits;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Create_Activity extends AppCompatActivity {

    // TODO creare form per inserire titolo, elementi + schedueling
    // TODO salvo la lista creata nel db
    // TODO scheduling del job (+ check se si riescono a recuperare i dati)


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_);

        Button homeBtn = (Button) findViewById(R.id.homeBtn);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Create_Activity.this, Home_Activity.class));
            }
        });
    }
}
