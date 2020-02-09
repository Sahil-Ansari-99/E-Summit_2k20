package com.company.e_summit;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FaqAdapter extends RecyclerView.Adapter<FaqViewHolder> {
    private Context context;
    private List<FaqModel> itemList;

    public FaqAdapter(Context context, List<FaqModel> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public FaqViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.faq_card, parent, false);
        return new FaqViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FaqViewHolder holder, int position) {
        FaqModel curr = itemList.get(position);

        holder.question.setText(curr.getQuestion());
        holder.answer.setText(curr.getAnswer());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}

class FaqViewHolder extends RecyclerView.ViewHolder {
    TextView question;
    TextView answer;

    public FaqViewHolder(@NonNull View itemView) {
        super(itemView);

        question = itemView.findViewById(R.id.faq_card_question);
        answer = itemView.findViewById(R.id.faq_card_answer);
    }
}