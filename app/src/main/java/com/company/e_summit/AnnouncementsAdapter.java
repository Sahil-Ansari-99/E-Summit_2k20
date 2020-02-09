package com.company.e_summit;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AnnouncementsAdapter extends RecyclerView.Adapter<AnnouncementsViewHolder>{
    List<AnnouncementsModel> itemList;
    Context context;

    public AnnouncementsAdapter(List<AnnouncementsModel> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    @NonNull
    @Override
    public AnnouncementsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.announcements_card, parent, false);
        return new AnnouncementsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnnouncementsViewHolder holder, int position) {
        final AnnouncementsModel curr = itemList.get(position);

        holder.title.setText(curr.getTitle());
        holder.description.setText(curr.getDescription());

        final String type = curr.getType();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type.equals("form")) {
                    String link = curr.getLink();
                    if (!link.equals(null)) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(link));
                        context.startActivity(intent);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}

class AnnouncementsViewHolder extends RecyclerView.ViewHolder {
    TextView title, description;

    public AnnouncementsViewHolder(@NonNull View itemView) {
        super(itemView);

        title = itemView.findViewById(R.id.announcements_card_title);
        description = itemView.findViewById(R.id.announcements_card_desc);
    }
}