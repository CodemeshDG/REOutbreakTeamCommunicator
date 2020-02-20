package com.dommyg.reoutbreakteamcommunicator;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

class FirestoreStatusController {

    private final String KEY_PLAYER_STATUS = "playerStatus";
    private final String KEY_PLAYER_SUB_STATUS = "playerSubStatus";

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference playersReference = db.collection("players");

    void update(ChangeStatusActivity activity, StatusType statusType, String username, String status, String subStatus) {
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
