package com.dommyg.reoutbreakteamcommunicator;

import android.content.res.Resources;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.util.HashMap;
import java.util.Map;

class FirestorePlayerController {
    private static final String TAG = "FirestorePlayerControll";

    private final String KEY_PLAYER_NUMBER = "playerNumber";
    private final String KEY_CHARACTER_NAME = "characterName";
    private final String KEY_HEADSHOT_PATH = "headshotPath";
    private final String KEY_PLAYER_STATUS = "playerStatus";
    private final String KEY_PLAYER_SUB_STATUS = "playerSubStatus";

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference playersReference = db.collection("players");
    private final Query playersReferenceQuery = playersReference.orderBy(KEY_PLAYER_NUMBER,
            Query.Direction.ASCENDING);

    private final Room room;
    private final ListenerRegistration registration;

    /**
     * Constructor used to read and write to "players" collection through createPlayer() and
     * updateStatus().
     */
    FirestorePlayerController() {
        this.room = null;
        this.registration = null;
    }

    /**
     * Constructor used by a Room to listen to the "players" collection to update the Room's
     * characterNames.
     */
    FirestorePlayerController(@NonNull Room room) {
        this.room = room;
        this.registration = playersReferenceQuery.addSnapshotListener(
                new PlayersReferenceListener());
    }

    void createPlayer(Resources resources, WriteBatch writeBatch,
                      DocumentReference documentReference, Character character,
                      int playerNumber) {

        Map<String, Object> mapPlayer = new HashMap<>();
        mapPlayer.put(KEY_PLAYER_NUMBER, playerNumber);
        mapPlayer.put(KEY_CHARACTER_NAME, resources.getString(character.getName()));
        mapPlayer.put(KEY_HEADSHOT_PATH, character.getHeadshotPath());
        mapPlayer.put(KEY_PLAYER_STATUS, new StatusBuilder().create(resources,
                resources.getString(character.getName()), StatusType.NONE));
        mapPlayer.put(KEY_PLAYER_SUB_STATUS, new SubStatusBuilder().create(null, null,
                null, StatusType.NONE));

        writeBatch.set(documentReference, mapPlayer);
    }

    void updateStatus(ChangeStatusActivity activity, StatusType statusType, String username,
                      String status, String subStatus) {
        Map<String, Object> data = new HashMap<>();
        data.put(KEY_PLAYER_STATUS, status);
        data.put(KEY_PLAYER_SUB_STATUS, subStatus);

        playersReference.document(username)
                .update(data)
                .addOnSuccessListener(new UpdateListener(activity, statusType));
    }

    private class UpdateListener implements OnSuccessListener<Void> {
        ChangeStatusActivity activity;
        StatusType statusType;

        UpdateListener(ChangeStatusActivity activity, StatusType statusType) {
            this.statusType = statusType;
            this.activity = activity;
        }

        @Override
        public void onSuccess(Void aVoid) {
            activity.setResult(statusType.getType());
            activity.finish();
        }
    }

    void stopListeningToPlayerReference() {
        registration.remove();
    }

    /**
     * Listens for changes in the "players" collection. When a change occurs, it takes the documents
     * in order of playerNumber, adds the characterNames to a String[] and sets the Room's
     * characterNames to it.
     */
    private class PlayersReferenceListener implements EventListener<QuerySnapshot> {
        @Override
        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots,
                            @Nullable FirebaseFirestoreException e) {
            if (e != null) {
                Log.w(TAG, "onEvent: Listen failed.", e);
                return;
            }

            if (room != null && queryDocumentSnapshots != null) {
                String[] updatedCharacterNames = new String[4];

                for (int i = 0; i < queryDocumentSnapshots.size(); i++) {
                    String characterName = (String) queryDocumentSnapshots.getDocuments()
                            .get(i)
                            .get(KEY_CHARACTER_NAME);

                    updatedCharacterNames[i] = characterName;
                }

                room.setCharacterNames(updatedCharacterNames);
            } else {
                Log.i(TAG, "onEvent: Something went wrong; room and/or documentSnapshot is null.");
            }
        }
    }
}
