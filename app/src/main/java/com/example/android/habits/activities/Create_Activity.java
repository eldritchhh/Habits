package com.example.android.habits.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

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
    }

    @OnClick(R.id.addTaskBtn)
    public void onClick(){
        this.tasks.add(newTaskEt.getText().toString());
        mAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.doneBtn)
    public void onClickDone(){

        List<Boolean> bools = new ArrayList<>();
        bools.add(true);
        bools.add(true);
        bools.add(false);
        bools.add(true);
        bools.add(true);
        bools.add(false);
        bools.add(true);

        remindMe = new RemindMe(
                remindMeNameEt.getText().toString(),
                mAdapter.getTasksList(),
                Calendar.getInstance().getTime(),
                bools);

        God.getInstance().addRemindMe(remindMe);

        Intent intent = new Intent(this, Home_Activity.class);
        startActivity(intent);
    }

    @OnClick(R.id.cancelBtn)
    public void onClickCancel(){
        startActivity(new Intent(this, Home_Activity.class));
    }

    @Override
    public void OnListItemClick(int clickedItemId) {}
}
