package com.example.android.habits.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.habits.R;
import com.example.android.habits.fragments.NextTaskFragment;
import com.example.android.habits.models.RemindMe;
import com.example.android.habits.models.Task;
import com.example.android.habits.singleton.God;

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

    private static final String START = "START";
    private static final String NEXT = "NEXT";
    private static final String DONE = "DONE";

    private static RemindMe remindMe;
    private static int taskIndex;
    private static int tasksSize;

    private static int singleProgress;

    private static String dialog_message = "Banana";
    private static String dialog_title = "Title";

    private long startTime = 0;
    private Handler timerHandler = new Handler();
    private Runnable timerRunnable = new Runnable() {

        @Override
        public void run() {
            long millis = System.currentTimeMillis() - startTime;
            int seconds = (int) (millis / 50);

            fiveSecondsPb.setProgress(100 - seconds);

            timerHandler.postDelayed(this, 25);
        }
    };

    @BindView(R.id.fiveSecondsPb)
    ProgressBar fiveSecondsPb;

    @BindView(R.id.overallPb)
    ProgressBar overallPb;

    @BindView(R.id.fiveSecondsBtn)
    Button fiveSecondsBtn;

    @BindView(R.id.taskTitleTv)
    TextView taskTitleTv;

    NextTaskFragment nextTaskFragment;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remind_me_);
        ButterKnife.bind(this);

        // First Element

        taskIndex = 0;

        int remindMeId = getIntent().getIntExtra("remindMeId", -1);
        remindMe = God.getInstance().getRemindMe(remindMeId);

        tasksSize = remindMe.getTasks().size();

        singleProgress = 100 / tasksSize;

        taskTitleTv.setText(remindMe.getTasks().get(taskIndex).getDescription());

        nextTaskFragment = new NextTaskFragment();

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.nextElementContainer, nextTaskFragment)
                .hide(nextTaskFragment)
                .commit();

        overallPb.setProgress(0);

    }

    @OnClick(R.id.fiveSecondsBtn)
    public void onClick() {
        String btnLabel = fiveSecondsBtn.getText().toString();

        switch (btnLabel) {

            case START:

                timerHandler.removeCallbacks(timerRunnable);

                fiveSecondsPb.setProgress(100);

                if (taskIndex < tasksSize - 1) {

                    String nextTask = remindMe.getTasks().get(taskIndex + 1).getDescription();
                    nextTaskFragment.setNextTask(nextTask);

                    fragmentManager.beginTransaction()
                            .show(nextTaskFragment)
                            .commit();

                    fiveSecondsBtn.setText(NEXT);

                } else {

                    // al posto di scrivere done potremmo lasciare un feedback positivo all'utente e basta

                    fiveSecondsBtn.setText(DONE);
                }

                break;

            case NEXT:

                fragmentManager.beginTransaction()
                        .hide(nextTaskFragment)
                        .commit();

                fiveSecondsBtn.setText(START);

                String currentTask = remindMe.getTasks().get(++taskIndex).getDescription();

                taskTitleTv.setText(currentTask);

                // START 5 sec COUNTDOWN

                overallPb.setProgress(singleProgress * taskIndex);

                // TIMER

                startTime = System.currentTimeMillis();
                timerHandler.postDelayed(timerRunnable, 0);

                break;

            case DONE:
                overallPb.setProgress(100);

                // POPUP - BRAVO COGLIONE

                // 1. Instantiate an AlertDialog.Builder with its constructor
                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                // 2. Chain together various setter methods to set the dialog characteristics
                builder.setMessage(dialog_message)
                        .setTitle(dialog_title);

                // 3. Get the AlertDialog from create()
                AlertDialog dialog = builder.create();

                dialog.show();

                startActivity(new Intent(this, Home_Activity.class));

                break;
            default:
                break;
        }
    }

}
