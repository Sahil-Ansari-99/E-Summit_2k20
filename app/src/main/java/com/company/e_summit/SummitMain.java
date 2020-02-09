package com.company.e_summit;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

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

public class SummitMain extends Fragment {
    private CarouselView carouselView;
    private TextView summitTitle, summitTagline, summitAbout, summitContactName, summitContactNumber;
    private Button knowMore, faqButton;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseStorage firebaseStorage;
    private List<String> carouselLinks;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_summit_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        summitTitle = view.findViewById(R.id.summit_main_title);
        summitAbout = view.findViewById(R.id.summit_main_about);
        summitTagline = view.findViewById(R.id.summit_main_tagline);
        knowMore = view.findViewById(R.id.summit_events_btn);
        faqButton = view.findViewById(R.id.summit_main_faq_btn);
        summitContactName = view.findViewById(R.id.summit_main_contact_name);
        summitContactNumber = view.findViewById(R.id.summit_main_contact_number);

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        carouselLinks = new ArrayList<>();

        String summit = MainActivity.currSummit.toLowerCase();

        setUpContactInfo(summit);

        switch (summit) {
            case "youth summit" :
                summitTitle.setText(R.string.youth_summit_title);
                summitTagline.setText(R.string.youth_summit_tagline);
                summitAbout.setText(R.string.youth_summit_about);
                break;

            case "innovator's summit":
                summitTitle.setText(R.string.innovators_summit_title);
                summitTagline.setText(R.string.innovators_summit_tagline);
                summitAbout.setText(R.string.innovators_summit_about);
                break;

            case "startup summit":
                summitTitle.setText(R.string.startup_summit_title);
                summitTagline.setText(R.string.startup_summit_tagline);
                summitAbout.setText(R.string.startup_summit_about);
                break;

            case "techno-entrepreneurship summit":
                summitTitle.setText(R.string.techno_summit_title);
                summitTagline.setText(R.string.techno_summit_tagline);
                summitAbout.setText(R.string.techno_summit_about);
        }
        carouselView = view.findViewById(R.id.youth_summit_carousel);
//        carouselView.setImageListener(new ImageListener() {
//            @Override
//            public void setImageForPosition(int position, ImageView imageView) {
//                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//                imageView.setImageResource(R.mipmap.e_summit_logo);
//            }
//        });
//        carouselView.setPageCount(5);
        getCarouselData(summit);

        knowMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.main_frame_layout, new AgendaMain()).commit();
            }
        });

        faqButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.main_frame_layout, new FaqMain()).commit();
            }
        });
    }

    private void getCarouselData(String summit) {
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("Main_Carousel");
        databaseReference.child(summit).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    carouselLinks.add(child.getValue(String.class));
                }
                setupCarousel();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setupCarousel() {
        carouselView.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, final ImageView imageView) {
                String link = carouselLinks.get(position);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                StorageReference ref = firebaseStorage.getReferenceFromUrl(link);
                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.with(getContext()).load(uri).into(imageView);
                    }
                });
            }
        });
        carouselView.setPageCount(carouselLinks.size());
    }

    private void setUpContactInfo(String summit) {
        switch (summit) {
            case "youth summit":
                summitContactName.setText(getText(R.string.youth_contact_name));
                summitContactNumber.setText(getText(R.string.youth_contact_number));
                break;
            case "innovator's summit":
                summitContactName.setText(getText(R.string.innovator_contact_name));
                summitContactNumber.setText(getText(R.string.innovator_contact_number));
                break;
            case "techno-entrepreneurship summit":
                summitContactName.setText(getText(R.string.techno_contact_name));
                summitContactNumber.setText(getText(R.string.techno_contact_number));
                break;
            case "startup summit":
                summitContactName.setText(getText(R.string.startup_contact_name));
                summitContactNumber.setText(getText(R.string.startup_contact_number));
                break;
        }
    }
}
