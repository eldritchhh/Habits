package com.example.android.habits;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OnClickRemindMe_Activity extends AppCompatActivity {

    // TODO inizilizzo recyclerview dal db mettendo tutto in Todo
    // TODO onclick dell'elemento lo sposta in Done e viceversa
    // TODO salvo lo stato in caso di chiusura dell'app / torno alla home / stuffs
    // TODO quando è finita "elimino lo stato" del remindme (e lo tolgo dal db se l'ho messo)


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_click_remind_me_);

    Button homeBtn = (Button) findViewById(R.id.homeBtn);
        homeBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(OnClickRemindMe_Activity.this, Home_Activity.class));
        }
    });


    }
}
