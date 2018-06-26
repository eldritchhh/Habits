package com.example.android.habits;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import Model.OnClickRemindMe;
import Model.ScheduledRemindMe;
import Model.Task;
import Singleton.Singleton;
import Utilities.Callback;

public class Create_Activity extends AppCompatActivity {

    // TODO creare form per inserire titolo, elementi + schedueling
    // TODO salvo la lista creata nel db
    // TODO scheduling del job (+ check se si riescono a recuperare i dati)

    private Singleton singleton = Singleton.getInstance();

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

        Button createRemindMeBtn = (Button) findViewById(R.id.createRemindMeBtn);
        createRemindMeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText remindMeTitlePt = (EditText) findViewById(R.id.remindMeTitlePt);

                ArrayList<Task> tasks = new ArrayList<>();

                tasks.add(new Task("task 1"));
                tasks.add(new Task("task 2"));
                tasks.add(new Task("task 3"));

                OnClickRemindMe onClickRemindMe = new OnClickRemindMe(remindMeTitlePt.getText().toString(), tasks, false);
                ScheduledRemindMe scheduledRemindMe = new ScheduledRemindMe(remindMeTitlePt.getText().toString() + "sched", tasks, 1);

                singleton.addOnClickRemindMe(onClickRemindMe);
                singleton.addScheduledRemindMe(scheduledRemindMe);

            }
        });

        final TextView remindMeTitleTextView = (TextView) findViewById(R.id.remindMeTitleTextView);

        Button showRemindMeTitleBtn = (Button) findViewById(R.id.showRemindMeTitleBtn);
        showRemindMeTitleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
