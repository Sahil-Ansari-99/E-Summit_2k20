package com.company.e_summit;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SponsorsAdapter extends RecyclerView.Adapter<SponsorsViewHolder>{
    private Context mContext;
    private List<SponsorsModel> itemList;
    private FirebaseStorage firebaseStorage;

    public SponsorsAdapter(Context mContext, List<SponsorsModel> itemList) {
        this.mContext = mContext;
        this.itemList = itemList;
        firebaseStorage = FirebaseStorage.getInstance();
    }
    @NonNull
    @Override
    public SponsorsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.sponsors_card, parent, false);
        return new SponsorsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SponsorsViewHolder holder, int position) {
        SponsorsModel sponsorsModel = itemList.get(position);
//        StorageReference ref = firebaseStorage.getReferenceFromUrl(sponsorsModel.getUrl());
//        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//            @Override
//            public void onSuccess(Uri uri) {
//                Picasso.with(mContext).load(uri).into(holder.spons_img);
//            }
//        });
        Picasso.with(mContext).load(sponsorsModel.getUrl()).into(holder.spons_img);
        holder.name.setText(sponsorsModel.getName());
        holder.title.setText(sponsorsModel.getTitle());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}

class SponsorsViewHolder extends RecyclerView.ViewHolder {
    TextView name, title;
    ImageView spons_img;

    public SponsorsViewHolder(@NonNull View itemView) {
        super(itemView);

        name = itemView.findViewById(R.id.sponsors_card_name);
        title = itemView.findViewById(R.id.sponsors_card_title);
        spons_img = itemView.findViewById(R.id.sponsors_card_img);
    }
}
