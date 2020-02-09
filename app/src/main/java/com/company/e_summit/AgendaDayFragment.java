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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AgendaDayFragment extends Fragment {
    private RecyclerView recyclerView;
    private AgendaDayAdapter adapter;
    private List<EventsModel> itemList;
    private FirebaseDatabase firebaseDatabase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_day, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle args = getArguments();
        String summit = MainActivity.currSummit;

        int currDay = args.getInt("day");
        recyclerView = view.findViewById(R.id.agenda_day_rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        itemList = new ArrayList<>();

        adapter = new AgendaDayAdapter(getContext(), itemList);
        recyclerView.setAdapter(adapter);

        firebaseDatabase = FirebaseDatabase.getInstance();
        getAgenda(summit, currDay);
    }

    private void getAgenda(String summit, int day) {
        String dayKey;

        if (day == 0) dayKey = "day0";
        else if (day == 1) dayKey = "day1";
        else dayKey = "day2";

        DatabaseReference databaseReference = firebaseDatabase.getReference().child("Agenda").child(summit).child(dayKey);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                itemList.clear();
                for (DataSnapshot agenda : dataSnapshot.getChildren()) {
                    itemList.add(agenda.getValue(EventsModel.class));
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
