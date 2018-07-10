package com.example.android.habits.observables;

import java.util.List;
import java.util.Observable;

import com.example.android.habits.models.RemindMe;

public class ListsObservable extends Observable {

    public static ListsObservable observable;

    private List<RemindMe> remindMeList;

    public ListsObservable() { }

    public synchronized static ListsObservable getInstance() {
        if (observable == null)
            observable = new ListsObservable();
        return observable;
    }

    public void setValues(Object list) {
        remindMeList = (List<RemindMe>) list;

        setChanged();
        notifyObservers(remindMeList);
        // update lists on firebase
    }
}
