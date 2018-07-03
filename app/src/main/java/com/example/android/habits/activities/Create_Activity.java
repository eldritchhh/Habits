package com.example.android.habits.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.android.habits.R;
import com.example.android.habits.models.RemindMe;
import com.example.android.habits.models.Task;
import com.example.android.habits.singleton.God;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.OnClick;

public class Create_Activity extends AppCompatActivity {

    // TODO creare form per inserire titolo, elementi + schedueling
    // TODO salvo la lista creata nel db
    // TODO scheduling del job (+ check se si riescono a recuperare i dati)

    private God god = God.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_);


        Button createRemindMeBtn = (Button) findViewById(R.id.createRemindMeBtn);
        createRemindMeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText remindMeTitlePt = (EditText) findViewById(R.id.remindMeTitlePt);

                ArrayList<Task> tasks = new ArrayList<>();

                tasks.add(new Task("task 1"));
                tasks.add(new Task("task 2"));
                tasks.add(new Task("task 3"));

                List<Boolean> weekdays = new ArrayList<>();
                for (int i = 0; i < 7; i++)
                    weekdays.add(true);

                // month is 0-based
                Date date = new Date(120, 1, 1);

                for (int i = 0; i < 10; i++) {
                    String s1 = remindMeTitlePt.getText().toString() + "_" + i;
                    god.addRemindMeFS(new RemindMe(s1, tasks, date, weekdays));
                }

            }
        });
    }

    @OnClick(R.id.homeBtn)
    public void onClick() {
        startActivity(new Intent(Create_Activity.this, Home_Activity.class));
    }
}
