package com.example.android.habits.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.habits.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RemindMe_Activity extends AppCompatActivity {

    // TODO inizializzo la recyclerview dal db
    // TODO countdown di 5 secondi col primo task
    // click utente
    // TODO evidenzio next
    // click utente
    // TODO evidenzio il prossimo task
    // TODO salvo lo stato in caso di chiusura dell'app / torno alla home / stuffs
    // TODO quando è finita "elimino lo stato" del remindme (e lo tolgo dal db se l'ho messo)

    @BindView(R.id.fiveSecondsPb)
    ProgressBar fiveSecondsPb;

    @BindView(R.id.fiveSecondsTv)
    TextView fiveSecondsTv;

    private int seconds = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remind_me_);
        ButterKnife.bind(this);

    }

    // TODO Ho chiesto a Nova come gestire il listener sul tempo
    // Se clicki il bottone sotto diminuisce del 20% e cambia il testo
    @OnClick(R.id.testBtn)
    public void onClick() {

        if (seconds <= 5) {
            fiveSecondsTv.setText(String.valueOf(++seconds));

            fiveSecondsPb.setProgress(fiveSecondsPb.getProgress() - 20);
        }
    }
    //startActivity(new Intent(RemindMe_Activity.this, Home_Activity.class));
}
