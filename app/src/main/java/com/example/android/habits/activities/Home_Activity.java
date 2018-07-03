package com.example.android.habits.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.Toast;

import com.example.android.habits.R;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import com.example.android.habits.adapters.RecyclerViewAdapter;

import com.example.android.habits.models.RemindMe;
import com.example.android.habits.observables.ListsObservable;
import com.example.android.habits.singleton.God;

// TODO create singleton class + db (già strutturato)

// TODO elenco delle liste collegato a firebase (--> recyclerview)

// TODO onclick apre la lista

// TODO onhold apre popup modify/delete/active(solo se scheduled)

// TODO ultimo elemento recyclerview per creare una nuova lista (--> Create_Activity)

// TODO menu con 3 puntini in alto a sx con preferences, liste non finite, ...
// TODO salvare le cose in locale (mySql) e pushare in Asynctask
// TODO per le liste scheduled countdown dal prossimo scheduling

public class Home_Activity extends AppCompatActivity implements RecyclerViewAdapter.ListItemClickListener, Observer {

    private RecyclerView remindMeListRv;
    private Toast mToast;
    private RecyclerViewAdapter mAdapter;

    /*@BindView(R.id.createBtn)
    Button createBtn;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_);

        God.getInstance();

        ListsObservable.getInstance().addObserver(this);

        this.remindMeListRv = (RecyclerView) findViewById(R.id.remindMeListRv);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        this.remindMeListRv.setLayoutManager(layoutManager);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                viewHolder.itemView.setBackgroundColor(Color.GREEN);
            }
        }).attachToRecyclerView(remindMeListRv);



        /*Button onScheduledBtn = (Button) findViewById(R.id.onScheduledBtn);
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
        });*/

    }

    /*@OnClick(R.id.createBtn)
    public void create(){
        startActivity(new Intent(Home_Activity.this, Create_Activity.class));
    }*/

    @Override
    public void update(Observable o, Object arg) {
        mAdapter = new RecyclerViewAdapter(5, this, (List<RemindMe>) arg);
        remindMeListRv.setAdapter(mAdapter);
    }

    @Override
    public void OnListItemClick(int clickedItemId) {
        startActivity(new Intent(getApplicationContext(), Create_Activity.class));

        /*if (mToast != null) mToast.cancel();

        String toastMessage = "Item #" + clickedItemId + " clicked.";
        mToast = Toast.makeText(this, toastMessage, Toast.LENGTH_LONG);

        mToast.show();*/
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                viewHolder.itemView.setBackgroundColor(Color.GREEN);
            }
        }).attachToRecyclerView(remindMeListRv);
    }

}
