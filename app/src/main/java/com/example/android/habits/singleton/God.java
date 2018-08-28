package com.example.android.habits.singleton;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.example.android.habits.models.RemindMe;
import com.example.android.habits.observables.ListsObservable;
import com.example.android.habits.utilities.Callback;

public class God {

    private static God god;
    private FirebaseFirestore db;

    private static final String COLLECTION_NAME = "RemindMeCollection";

    public static List<RemindMe> remindMeList;

    // TODO Gestire i permessi di scrittura sul db da firebase

    private God() {
        // TODO MultiUtenti
        // Lista di collections ==> lista di utenti
        // Ogni utente avrà:
        // - Lista di collections ==> lista di RemindMe
        // - un doc con personal info
        setUpDb();

        downloadRemindMeList();
    }

    private void setUpDb() {
        this.db = FirebaseFirestore.getInstance();

        this.db.setFirestoreSettings(
                new FirebaseFirestoreSettings
                        .Builder()
                        .setPersistenceEnabled(true)
                        .build()
        );
    }

    public synchronized static God getInstance() {
        if (god == null)
            god = new God();
        return god;
    }

    /**
     * LOCAL DB METHODS
     */

    public RemindMe getRemindMe(int index) {
        return this.remindMeList.get(index);
    }

    public List<RemindMe> getRemindMeList() {
        return this.remindMeList;
    }

    public void addRemindMe(RemindMe remindMe) {
        remindMeList.add(remindMe);
        Collections.sort(remindMeList, new mComparator());
        addRemindMeFS(remindMe);
    }

    public void updateRemindMe(RemindMe remindMe) {
        int index = remindMeList.indexOf(remindMe);
        remindMeList.set(index, remindMe);
        Collections.sort(remindMeList, new mComparator());
        // TODO creare i vari update
        updateRemindMeFS(remindMe.getTitle(), "newTitle");
    }

    public void deleteRemindMe(RemindMe remindMe) {
        remindMeList.remove(remindMe);
        deleteRemindMeFS(remindMe.getTitle());
    }

    /**
     * FIRESTORE METHODS
     */

    private void getRemindMeListFS(final Callback callback) {
        CollectionReference collectionReference = db.collection(COLLECTION_NAME);
        collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                callback.onSuccess(queryDocumentSnapshots.toObjects(RemindMe.class));
            }
        });
    }

    private void addRemindMeFS(RemindMe remindMe) {
        db.collection(COLLECTION_NAME)
                .document(remindMe.getTitle())
                .set(remindMe)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // TODO retry
                    }
                });
    }

    private void updateRemindMeFS(final String title, String newTitle) {
        DocumentReference documentReference = this.getDocument(COLLECTION_NAME, title);

        documentReference.update("title", newTitle)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // TODO retry
                    }
                });
    }

    private void deleteRemindMeFS(final String title) {
        DocumentReference documentReference = this.getDocument(COLLECTION_NAME, title);

        documentReference.delete()
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // TODO retry
                    }
                });
    }

    /**
     * PRIVATE METHODS
     */

    private class mComparator implements Comparator<RemindMe> {
        public int compare(RemindMe left, RemindMe right) {
            return left.getTitle().compareTo(right.getTitle());
        }
    }

    private void downloadRemindMeList() {
        this.getRemindMeListFS(new Callback() {
            @Override
            public void onSuccess(Object data) {
                if (remindMeList == null) {
                    ListsObservable.getInstance().setValues(data);
                    remindMeList = (List<RemindMe>) data;
                    Collections.sort(remindMeList, new mComparator());
                }
            }

            @Override
            public void onFailure(Error error, String message) {
                // TODO retry
            }
        });
    }

    private DocumentReference getDocument(String collection, String document) {
        return db.collection(collection).document(document);
    }

}
