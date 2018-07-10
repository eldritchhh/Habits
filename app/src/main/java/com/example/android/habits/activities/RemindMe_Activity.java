package com.example.android.habits.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.android.habits.R;

public class RemindMe_Activity extends AppCompatActivity {

    // TODO inizializzo la recyclerview dal db
    // TODO countdown di 5 secondi col primo task
            // click utente
    // TODO evidenzio next
            // click utente
    // TODO evidenzio il prossimo task
    // TODO salvo lo stato in caso di chiusura dell'app / torno alla home / stuffs
    // TODO quando è finita "elimino lo stato" del remindme (e lo tolgo dal db se l'ho messo)


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remind_me_);

    Button homeBtn = (Button) findViewById(R.id.homeBtn);
    homeBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(RemindMe_Activity.this, Home_Activity.class));
        }
    });

    }
}
