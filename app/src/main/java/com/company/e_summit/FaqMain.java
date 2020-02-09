package com.company.e_summit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FaqMain extends Fragment {
    private RecyclerView recyclerView;
    private List<FaqModel> itemList;
    private FaqAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_faq, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.faq_rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        itemList = new ArrayList<>();

        for (int i = 1; i < 8; i++) {
            addFaqs(i);
        }

        adapter = new FaqAdapter(getContext(), itemList);
        recyclerView.setAdapter(adapter);
    }

    private void addFaqs(int number) {
        FaqModel model;
        switch (number) {
            case 1:
                model = new FaqModel(getString(R.string.faq_q1), getString(R.string.faq_a1));
                itemList.add(model);
                break;

            case 2:
                model = new FaqModel(getString(R.string.faq_q2), getString(R.string.faq_a2));
                itemList.add(model);
                break;

            case 3:
                model = new FaqModel(getString(R.string.faq_q3), getString(R.string.faq_a3));
                itemList.add(model);
                break;

            case 4:
                model = new FaqModel(getString(R.string.faq_q4), getString(R.string.faq_a4));
                itemList.add(model);
                break;

            case 5:
                model = new FaqModel(getString(R.string.faq_q5), getString(R.string.faq_a5));
                itemList.add(model);
                break;

            case 6:
                model = new FaqModel(getString(R.string.faq_q6), getString(R.string.faq_a6));
                itemList.add(model);
                break;

            case 7:
                model = new FaqModel(getString(R.string.faq_q7), getString(R.string.faq_a7));
                itemList.add(model);
                break;
            case 8:
                model = new FaqModel(getString(R.string.faq_q8), getString(R.string.faq_a8));
                itemList.add(model);
                break;
        }
    }
}
