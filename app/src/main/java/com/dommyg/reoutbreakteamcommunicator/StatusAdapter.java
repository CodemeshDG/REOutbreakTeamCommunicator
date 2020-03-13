package com.dommyg.reoutbreakteamcommunicator;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.io.IOException;
import java.io.InputStream;

public class StatusAdapter extends FirestoreRecyclerAdapter<StatusItem,
        StatusAdapter.StatusViewHolder> {

    private Context context;
    private ControlPanelFragment controlPanelFragment;

    static class StatusViewHolder extends RecyclerView.ViewHolder {
        TextView textViewPlayerStatus;
        TextView textViewPlayerSubStatus;
        ImageView imageViewPlayerHeadshot;

        StatusViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textViewPlayerStatus = itemView.findViewById(R.id.textViewPlayerStatus);
            this.textViewPlayerSubStatus = itemView.findViewById(R.id.textViewPlayerSubStatus);
            this.imageViewPlayerHeadshot = itemView.findViewById(R.id.imageViewPlayerHeadshot);
        }
    }

    StatusAdapter(@NonNull FirestoreRecyclerOptions<StatusItem> options, Context context,
                  ControlPanelFragment controlPanelFragment) {
        super(options);
        this.context = context;
        this.controlPanelFragment = controlPanelFragment;
    }

    @Override
    protected void onBindViewHolder(@NonNull StatusViewHolder holder, int position,
                                    @NonNull StatusItem model) {
        holder.textViewPlayerStatus.setText(model.getPlayerStatus());
        holder.textViewPlayerSubStatus.setText(model.getPlayerSubStatus());

        AssetManager assetManager = context.getAssets();
        try {
            InputStream inputStream = assetManager.open(model.getHeadshotPath());
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            holder.imageViewPlayerHeadshot.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @NonNull
    @Override
    public StatusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_status, parent, false);
        return new StatusViewHolder(v);
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @Override
    public void onDataChanged() {
        super.onDataChanged();
        controlPanelFragment.checkIfTeammatesExist();
    }
}
