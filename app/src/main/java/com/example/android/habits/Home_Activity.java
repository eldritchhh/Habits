package com.example.android.habits;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Home_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_);

        // TODO create singleton class + db

        // TODO switch tra onclic e scheduled
        // TODO elenco delle liste collegato a firebase (--> recyclerview)
        // TODO onclick apre la lista
        // TODO onhold apre popup modify/delete/active(solo se scheduled)
        // TODO salvo un contatore per vedere quanti click faccio (statistiche + sapere le più usate)
        // TODO ultimo elemento recyclerview per creare una nuova lista (--> Create_Activity)
        // TODO menu con 3 puntini in alto a sx con preferences, liste non finite, ...
        // TODO salvare le cose in locale (mySql) e pushare in Asynctask
        // TODO per le liste scheduled countdown dal prossimo scheduling

        Button onClickBtn = (Button) findViewById(R.id.onClickBtn);
        onClickBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home_Activity.this, OnClickRemindMe_Activity.class));
            }
        });

        Button onScheduledBtn = (Button) findViewById(R.id.onScheduledBtn);
        onScheduledBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home_Activity.this, ScheduledRemindMe_Activity.class));
            }
        });

        Button editBtn = (Button) findViewById(R.id.editBtn);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home_Activity.this, Edit_Activity.class));
            }
        });

        Button createBtn = (Button) findViewById(R.id.createBtn);
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home_Activity.this, Create_Activity.class));
            }
        });
    }
}
