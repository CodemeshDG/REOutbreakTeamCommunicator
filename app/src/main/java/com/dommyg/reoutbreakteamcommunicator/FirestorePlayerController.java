package com.dommyg.reoutbreakteamcommunicator;

import android.content.res.Resources;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;

import java.util.HashMap;
import java.util.Map;

class FirestorePlayerController {

    // TODO: Add method for creating when room is created or joined.

    private final String KEY_PLAYER_NUMBER = "playerNumber";
    private final String KEY_CHARACTER_NAME = "characterName";
    private final String KEY_HEADSHOT_PATH = "headshotPath";
    private final String KEY_PLAYER_STATUS = "playerStatus";
    private final String KEY_PLAYER_SUB_STATUS = "playerSubStatus";

//    private final int ARRAY_INDEX_CHARACTER_NAME = 0;
//    private final int ARRAY_INDEX_HEADSHOT_PATH = 1;

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference playersReference = db.collection("players");

    void createPlayer(Resources resources, WriteBatch creationBatch,
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

        creationBatch.set(documentReference, mapPlayer);
    }

//    private String[] getCharacterDetails(Resources resources, int selectedCharacter) {
//        String[] characterDetails = new String[2];
//        Character character;
//
//        switch (selectedCharacter) {
//            case R.id.radioButtonCharacter1:
//                character = Character.ALYSSA;
//                break;
//            case R.id.radioButtonCharacter2:
//                character = Character.CINDY;
//                break;
//            case R.id.radioButtonCharacter3:
//                character = Character.DAVID;
//                break;
//            case R.id.radioButtonCharacter4:
//                character = Character.GEORGE;
//                break;
//            case R.id.radioButtonCharacter5:
//                character = Character.JIM;
//                break;
//            case R.id.radioButtonCharacter6:
//                character = Character.KEVIN;
//                break;
//            case R.id.radioButtonCharacter7:
//                character = Character.MARK;
//                break;
//            case R.id.radioButtonCharacter8:
//                character = Character.YOKO;
//                break;
//            default:
//                return null;
//        }
//
//        characterDetails[ARRAY_INDEX_CHARACTER_NAME] = resources.getString(character.getName());
//        characterDetails[ARRAY_INDEX_HEADSHOT_PATH] = character.getHeadshotPath();
//        return characterDetails;
//    }

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
}
