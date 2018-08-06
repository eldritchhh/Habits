package com.example.android.habits.activities;

import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.habits.R;
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
    private static int taskIndex = 0;
    private static int tasksSize;

    private static int singleProgress;

    private static String dialog_message = "Banana";
    private static String dialog_title = "Title";

    @BindView(R.id.fiveSecondsPb)
    ProgressBar fiveSecondsPb;

    @BindView(R.id.overallPb)
    ProgressBar overallPb;

    @BindView(R.id.fiveSecondsBtn)
    Button fiveSecondsBtn;

    @BindView(R.id.taskTitleTv)
    TextView taskTitleTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remind_me_);
        ButterKnife.bind(this);

        int remindMeId = getIntent().getIntExtra("remindMeId", -1);
        remindMe = God.getInstance().getRemindMe(remindMeId);

        tasksSize = remindMe.getTasks().size();

        singleProgress = 100 / tasksSize;

        taskTitleTv.setText(remindMe.getTasks().get(taskIndex).getDescription());
        overallPb.setProgress(0);
    }

    @OnClick(R.id.fiveSecondsBtn)
    public void onClick() throws InterruptedException {
        String btnLabel = fiveSecondsBtn.getText().toString();

        switch (btnLabel) {

            case START:
                if (taskIndex < tasksSize - 1) {
                    fiveSecondsBtn.setText(NEXT);
                } else {
                    fiveSecondsBtn.setText(DONE);
                }

                // STOP COUNTDOWN

                fiveSecondsPb.setProgress(100);

                break;

            case NEXT:
                fiveSecondsBtn.setText(START);

                Task task = remindMe.getTasks().get(++taskIndex);

                taskTitleTv.setText(task.getDescription());

                // START 5 sec COUNTDOWN

                overallPb.setProgress(singleProgress * taskIndex);

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

                // 2 Seconds Chiudo l'app o vado alla home
                Thread.sleep(2000);

                startActivity(new Intent(this, Home_Activity.class));

                break;
            default:
                break;
        }
    }

}
