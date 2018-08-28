package com.example.android.habits.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.dpro.widgets.OnWeekdaysChangeListener;
import com.dpro.widgets.WeekdaysPicker;
import com.example.android.habits.R;
import com.example.android.habits.adapters.TasksRecyclerViewAdapter;
import com.example.android.habits.models.RemindMe;
import com.example.android.habits.singleton.God;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Create_Activity extends AppCompatActivity implements TasksRecyclerViewAdapter.ListItemClickListener {

    // TODO creare form per inserire titolo, elementi + schedueling
    // TODO salvo la lista creata nel db
    // TODO scheduling del job (+ check se si riescono a recuperare i dati)

    private God god = God.getInstance();

    private List<String> tasks;

    private TasksRecyclerViewAdapter mAdapter;

    private RemindMe remindMe;

    @BindView(R.id.remindMeNameEt)
    EditText remindMeNameEt;

    @BindView(R.id.tasksListRv)
    RecyclerView tasksListRv;

    @BindView(R.id.newTaskEt)
    EditText newTaskEt;

    @BindView(R.id.weekdays)
    WeekdaysPicker weekdays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_);
        ButterKnife.bind(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        this.tasksListRv.setLayoutManager(layoutManager);

        this.tasks = new ArrayList<>();

        mAdapter = new TasksRecyclerViewAdapter(5, this, this.tasks);
        tasksListRv.setAdapter(mAdapter);

        weekdays.setOnWeekdaysChangeListener(new OnWeekdaysChangeListener() {
            @Override
            public void onChange(View view, int clickedDayOfWeek, List<Integer> selectedDays) {

            }
        });
    }

    @OnClick(R.id.addTaskBtn)
    public void onClick() {
        this.tasks.add(newTaskEt.getText().toString());
        mAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.doneBtn)
    public void onClickDone() {

        List<Integer> selectedDays = weekdays.getSelectedDays();

        List<Boolean> weekdaysSelected = new ArrayList<>(7);

        for (int i = 0; i < 7; i++) {
            weekdaysSelected.add(false);
        }

        // S M T W T F S
        for (int day : selectedDays) {
            weekdaysSelected.set(day - 1, true);
        }

        remindMe = new RemindMe(
                remindMeNameEt.getText().toString(),
                mAdapter.getTasksList(),
                Calendar.getInstance().getTime(),
                weekdaysSelected);

        God.getInstance().addRemindMe(remindMe);

        Intent intent = new Intent(this, Home_Activity.class);
        startActivity(intent);
    }

    @OnClick(R.id.cancelBtn)
    public void onClickCancel() {
        startActivity(new Intent(this, Home_Activity.class));
    }

    @Override
    public void OnListItemClick(int clickedItemId) {
    }
}
