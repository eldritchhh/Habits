package com.example.android.habits.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.android.habits.R;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import com.example.android.habits.adapters.RecyclerViewAdapter;

import com.example.android.habits.models.RemindMe;
import com.example.android.habits.observables.ListsObservable;
import com.example.android.habits.singleton.God;

import butterknife.ButterKnife;
import butterknife.OnClick;

// TODO create singleton class + db (già strutturato)

// TODO elenco delle liste collegato a firebase (--> recyclerview)

// TODO onclick apre la lista

// TODO onhold apre popup modify/delete/active(solo se scheduled)

// TODO ultimo elemento recyclerview per creare una nuova lista (--> Create_Activity)

// TODO menu con 3 puntini in alto a sx con preferences, liste non finite, ...
// TODO salvare le cose in locale (mySql) e pushare in Asynctask
// TODO per le liste scheduled countdown dal prossimo scheduling

public class Home_Activity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener, RecyclerViewAdapter.ListItemClickListener, Observer {

    private RecyclerView remindMeListRv;
    private Toast mToast;
    private RecyclerViewAdapter mAdapter;
    private SharedPreferences sharedPreferences;

    private God god;

    /*@BindView(R.id.createBtn)
    Button createBtn;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_);
        ButterKnife.bind(this);

        god = God.getInstance();

        ListsObservable.getInstance().addObserver(this);

        this.remindMeListRv = (RecyclerView) findViewById(R.id.remindMeListRv);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        this.remindMeListRv.setLayoutManager(layoutManager);

        if (god.getRemindMeList() != null) {
            mAdapter = new RecyclerViewAdapter(5, this, god.getRemindMeList());
            remindMeListRv.setAdapter(mAdapter);
        }

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

        setUpPreferences();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.pref_Item:
                startActivity(new Intent(this, Pref_Activity.class));
                break;
            case R.id.x:
                // MAKE STUFFS
                break;
            case R.id.xx:
                // MAKE STUFFS
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpPreferences() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        /*

        ####################################################################
        queste sono le chiamate ai metodi che effettivamente fanno cose
        ad esempio

            private void setNightMode(boolean x){
                View view = this.getWindow().getDecorView();

                if (x) {
                    view.setBackgroundColor(Color.parseColor("#000000"));
                } else {
                    view.setBackgroundColor(Color.parseColor("#FFFFFF"));
                }
            }
        ####################################################################


        setNightMode(sharedPreferences.getBoolean(
                getString(R.string.pref_night_mode_key),
                getResources().getBoolean(R.bool.pref_night_mode_default)));

        setBackgroundColor(sharedPreferences.getString(
                getString(R.string.pref_background_key),
                getResources().getString(R.string.pref_background_default_value)));

        setUsername(sharedPreferences.getString(
                "username",
                getResources().getString(R.string.pref_username_default_value)
        ));
        */

        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        /*
        switch (key){
            case NIGHT_MODE:
                setNightMode(sharedPreferences.getBoolean(key,
                        getResources().getBoolean(R.bool.pref_night_mode_default)));
                break;
            case BACKGROUND:
                setBackgroundColor(sharedPreferences.getString(key,
                        getResources().getString(R.string.pref_background_default_value)));
                break;
            case USERNAME:
                setUsername(sharedPreferences.getString(key,
                        getResources().getString(R.string.pref_username_default_value)));
                break;
            default:
        }
        */
    }

    @Override
    public void update(Observable o, Object arg) {
        mAdapter = new RecyclerViewAdapter(5, this, (List<RemindMe>) arg);
        remindMeListRv.setAdapter(mAdapter);
    }

    @Override
    public void OnListItemClick(int clickedItemId) {
        // TODO si triggera tramite dei services

        Intent intent = new Intent(this, RemindMe_Activity.class);
        intent.putExtra("remindMeId", clickedItemId);
        startActivity(intent);

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

    @OnClick(R.id.floating_action_button)
    public void onClick(){
        // TODO => Create_Activity
    }

}
