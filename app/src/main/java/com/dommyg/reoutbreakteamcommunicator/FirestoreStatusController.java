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

    void update(SingleFragmentActivity activity, String username, String status, String subStatus) {
        Map<String, Object> data = new HashMap<>();
        data.put(KEY_PLAYER_STATUS, status);
        data.put(KEY_PLAYER_SUB_STATUS, subStatus);

        playersReference.document(username)
                .set(data, SetOptions.merge())
                .addOnSuccessListener(new UpdateListener(activity));
    }

    private class UpdateListener implements OnSuccessListener<Void> {
        SingleFragmentActivity activity;

        UpdateListener(SingleFragmentActivity activity) {
            this.activity = activity;
        }

        @Override
        public void onSuccess(Void aVoid) {
            activity.finish();
        }
    }
}
