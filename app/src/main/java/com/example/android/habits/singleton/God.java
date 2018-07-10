package com.example.android.habits.singleton;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;

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

        this.getRemindMeListFS(new Callback() {
            @Override
            public void onSuccess(Object data) {
                ListsObservable.getInstance().setValues(data);
            }

            @Override
            public void onFailure(Error error, String message) {
            }
        });
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

    /** LOCAL DB METHODS */

    public RemindMe getRemindMe(int index) {
        return this.remindMeList.get(index);
    }

    public List<RemindMe> getRemindMeList() {
        return this.remindMeList;
    }

    public void addRemindMe(RemindMe remindMe) {
        // TODO Controlli?
        remindMeList.add(remindMe);
        // TODO asyncTask per aggiornare DB
    }

    public void deleteRemindMe(RemindMe remindMe) {
        remindMeList.remove(remindMe);
    }

    /** FIRESTORE METHODS */

    public void getRemindMeListFS(final Callback callback) {
        CollectionReference collectionReference = db.collection(COLLECTION_NAME);
        collectionReference.whereEqualTo("estScheduled", true)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                callback.onSuccess(queryDocumentSnapshots.toObjects(RemindMe.class));
            }
        });
    }

    public void getRemindMeFS(final String title, final Callback callback) {

        // TODO gestire exceptions, error, document not found, etc

        DocumentReference documentReference = this.getDocument(COLLECTION_NAME, title);
        documentReference.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        RemindMe remindMe = documentSnapshot.toObject(RemindMe.class);
                        callback.onSuccess(remindMe);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onFailure(new Error(), "Non sono riuscito a gettare il doc");
                    }
                });
    }

    public void addRemindMeFS(RemindMe remindMe) {
        db.collection(COLLECTION_NAME).document(remindMe.getTitle()).set(remindMe);
    }

    public void updateRemindMeFS(final String title, String newTitle, final Callback callback) {
        DocumentReference documentReference = this.getDocument(COLLECTION_NAME, title);

        documentReference.update("title", newTitle)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // feedback document updated
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // feedback document not updated
                    }
                });
    }

    public void deleteRemindMeFS(final String title, final Callback callback) {
        DocumentReference documentReference = this.getDocument(COLLECTION_NAME, title);

        documentReference.delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // feedback document deleted
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // feedback document not deleted
                    }
                });
    }

    /** PRIVATE METHODS */

    private DocumentReference getDocument(String collection, String document) {
        return db.collection(collection).document(document);
    }

}
