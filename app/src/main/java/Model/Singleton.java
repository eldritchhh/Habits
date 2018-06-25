package Model;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Francesco on 25/06/2018.
 */

public class Singleton {

    private static Singleton singleton;
    private FirebaseFirestore db;
    private FirebaseFirestoreSettings settings;

    // TODO Gestire i permessi di scrittura sul db da firebase

    private Singleton(){
        setUpDb();
    }

    private void setUpDb(){
        db = FirebaseFirestore.getInstance();

        settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        db.setFirestoreSettings(settings);
    }

    public synchronized static Singleton getInstance(){
        if(singleton == null)
            singleton = new Singleton();
        return singleton;
    }

    public void addRemindMe(RemindMe remindMetoAdd) {
        // Map<String, Object> doc = new HashMap<>();
        // TODO LO METTIAMO ANCHE COME PARAMETRO O SOLO COME NOME DEL FILE?
        //doc.put("title", RemindMetoAdd.getTitle());
        //doc.put("tasks", RemindMetoAdd.getElements());

        if(remindMetoAdd instanceof OnClickRemindMe) {
            //doc.put("scheduled", ((OnClickRemindMe) RemindMetoAdd).getSchedultime());
            CollectionReference collection = db.collection("OnClickRemindMeCollection");
            collection.document(remindMetoAdd.getTitle()).set(remindMetoAdd);
        } else {
            CollectionReference collection = db.collection("ScheduledRemindMeCollection");
            collection.document(remindMetoAdd.getTitle()).set(remindMetoAdd);
        }
    // TODO GESTIRE GLI ERRORI E PREVEDERE UN QUALCHE GENERE DI RITORNO PER DARE UN FEEDBACK ALL'UTENTE
    }

    public RemindMe getRemindMe(final String title){
        RemindMe remindMetoLoad = null;

        // TODO NON SO QUANTO SIA LEGALE QUELLO CHE FACCIO
              //ANCHE PERCHè NON SO COME FARLO RITORNARE

        DocumentReference onClick = db.collection("OnClickRemindMeCollection").document(title);
        onClick.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        RemindMe remindMetoLoad = documentSnapshot.toObject(RemindMe.class);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        DocumentReference scheduled = db.collection("ScheduledRemindMeCollection").document(title);
                        scheduled.get()
                                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        RemindMe remindMetoLoad = documentSnapshot.toObject(RemindMe.class);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        RemindMe remindMetoLoad = null;
                                    }
                                });
                    }
                });
        return remindMetoLoad;
    }

    public void updateRemindMe(final String title){
        RemindMe remindMetoUpdate = getRemindMe(title);
        // LO MODIFICO


        // TODO gestire ritorno, come per add
    }
}
