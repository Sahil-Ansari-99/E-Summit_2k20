package com.company.e_summit;

import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;
import java.util.List;

public class EventsMain extends Fragment {
    private CarouselView mainCarousel, day1Carousel, day2Carousel;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseStorage firebaseStorage;
    private List<CarouselModel> itemList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_events, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mainCarousel = view.findViewById(R.id.events_carousel_main);
        day1Carousel = view.findViewById(R.id.events_carousel_day1);
        day2Carousel = view.findViewById(R.id.events_carousel_day2);

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        itemList = new ArrayList<>();

        day1Carousel.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setImageResource(R.mipmap.e_summit_logo);
            }
        });
//
//        day1Carousel.setImageListener(new ImageListener() {
//            @Override
//            public void setImageForPosition(int position, ImageView imageView) {
//                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//                imageView.setImageResource(R.mipmap.e_summit_logo);
//            }
//        });
//
//        day2Carousel.setImageListener(new ImageListener() {
//            @Override
//            public void setImageForPosition(int position, ImageView imageView) {
//                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//                imageView.setImageResource(R.mipmap.e_summit_logo);
//            }
//        });
//
        day1Carousel.setPageCount(3);
//        day1Carousel.setPageCount(5);
//        day2Carousel.setPageCount(5);

        getEventsData(mainCarousel, 1);
        getEventsData(day2Carousel, 2);
    }

    private void getEventsData(final CarouselView carouselView, int day) {
        String dayKey;

        if (day == 0) dayKey = "day0";
        else if (day == 1) dayKey = "day1";
        else dayKey = "day2";
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("Events").child(MainActivity.currSummit).child(dayKey);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                itemList.clear();
                for (DataSnapshot event : dataSnapshot.getChildren()) {
                    itemList.add(event.getValue(CarouselModel.class));
                }
                setupCarouselView(carouselView, itemList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setupCarouselView(CarouselView carouselView, final List<CarouselModel> itemList) {
        carouselView.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, final ImageView imageView) {
                CarouselModel model = itemList.get(position);
                StorageReference ref = firebaseStorage.getReferenceFromUrl(model.getLink());
                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.with(getContext()).load(uri).into(imageView);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println(e.getMessage());
                    }
                });
            }
        });
        carouselView.setPageCount(itemList.size());
    }
}
