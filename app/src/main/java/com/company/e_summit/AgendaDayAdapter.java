package com.company.e_summit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AgendaDayAdapter extends RecyclerView.Adapter<AgendaViewHolder> {
    private Context context;
    private List<EventsModel> itemList;

    public AgendaDayAdapter(Context context, List<EventsModel> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public AgendaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.agenda_card, parent, false);
        return new AgendaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AgendaViewHolder holder, int position) {
        EventsModel curr = itemList.get(position);

        holder.title.setText(curr.getTitle());
        holder.date.setText(curr.getTime());
        holder.venue.setText(curr.getVenue());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}

class AgendaViewHolder extends RecyclerView.ViewHolder{
    TextView title, date, venue;

    public AgendaViewHolder(@NonNull View itemView) {
        super(itemView);

        title = itemView.findViewById(R.id.agenda_card_title);
        date = itemView.findViewById(R.id.agenda_card_date);
        venue = itemView.findViewById(R.id.agenda_card_venue);
    }
}