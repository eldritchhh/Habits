package Singleton;

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

import Model.OnClickRemindMe;
import Model.ScheduledRemindMe;
import ListObserver.ListsObservable;
import Utilities.Callback;

public class Singleton {

    private static Singleton singleton;
    private FirebaseFirestore db;

    public final static String SCHEDULED_LIST = "scheduled";
    public final static String ON_CLICK_LIST = "onClick";

    private static final String COLLECTION_NAME = "RemindMeCollection";

    public List<OnClickRemindMe> onClickRemindMeList;
    public List<ScheduledRemindMe> scheduledRemindMeList;

    // TODO Gestire i permessi di scrittura sul db da firebase

    private Singleton() {
        // TODO MultiUtenti
        // Lista di collections ==> lista di utenti
        // Ogni utente avrà:
        // - Lista di collections ==> lista di RemindMe
        // - un doc con personal info
        setUpDb();

        this.getScheduledRemindMeListFS(new Callback() {
            @Override
            public void onSuccess(Object data) {
                ListsObservable.getInstance().setValues(SCHEDULED_LIST, data);
            }

            @Override
            public void onFailure(Error error, String message) {
            }
        });

        this.getOnClickRemindMeListFS(new Callback() {
            @Override
            public void onSuccess(Object data) {
                ListsObservable.getInstance().setValues(ON_CLICK_LIST, data);
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

    public synchronized static Singleton getInstance() {
        if (singleton == null)
            singleton = new Singleton();
        return singleton;
    }

    // LOCAL DB METHODS

    public OnClickRemindMe getOnClickRemindMe(int index) {
        return this.onClickRemindMeList.get(index);
    }

    public ScheduledRemindMe getScheduledRemindMe(int index) {
        return this.scheduledRemindMeList.get(index);
    }

    public List<OnClickRemindMe> getOnClickRemindMeList() {
        return this.onClickRemindMeList;
    }

    public List<ScheduledRemindMe> getScheduledRemindMeList() {
        return this.scheduledRemindMeList;
    }

    public void addOnClickRemindMe(OnClickRemindMe onClickRemindMe) {
        // TODO Controlli?
        onClickRemindMeList.add(onClickRemindMe);
        // TODO asyncTask per aggiornare DB
    }

    public void addScheduledRemindMe(ScheduledRemindMe scheduledRemindMe) {
        // TODO Controlli?
        scheduledRemindMeList.add(scheduledRemindMe);
        // TODO asyncTask per aggiornare DB
    }

    public void deleteScheduledRemindMe(ScheduledRemindMe scheduledRemindMe) {
        scheduledRemindMeList.remove(scheduledRemindMe);
    }

    public void deleteOnClickRemindMe(OnClickRemindMe onClickRemindMe) {
        onClickRemindMeList.remove(onClickRemindMe);
    }

    // FIRESTORE METHODS

    public void getScheduledRemindMeListFS(final Callback callback) {
        CollectionReference collectionReference = db.collection(COLLECTION_NAME);
        collectionReference.whereEqualTo("estScheduled", true)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                callback.onSuccess(queryDocumentSnapshots.toObjects(ScheduledRemindMe.class));
            }
        });
    }

    public void getOnClickRemindMeListFS(final Callback callback) {
        CollectionReference collectionReference = db.collection(COLLECTION_NAME);
        collectionReference.whereEqualTo("estScheduled", false)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                callback.onSuccess(queryDocumentSnapshots.toObjects(OnClickRemindMe.class));
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
                        OnClickRemindMe onClickRemindMe = documentSnapshot.toObject(OnClickRemindMe.class);
                        callback.onSuccess(onClickRemindMe);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onFailure(new Error(), "Non sono riuscito a gettare il doc");
                    }
                });
    }

    public void addOnClickRemindMeFS(OnClickRemindMe onClickRemindMe) {
        db.collection(COLLECTION_NAME).document(onClickRemindMe.getTitle()).set(onClickRemindMe);
    }

    public void addScheduledRemindMeFS(ScheduledRemindMe scheduledRemindMe) {
        db.collection(COLLECTION_NAME).document(scheduledRemindMe.getTitle()).set(scheduledRemindMe);
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

    // PRIVATE METHODS

    private DocumentReference getDocument(String collection, String document) {
        return db.collection(collection).document(document);
    }

}
