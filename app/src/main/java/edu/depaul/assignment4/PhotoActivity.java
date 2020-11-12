package edu.depaul.assignment4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

public class PhotoActivity extends AppCompatActivity {

    private TextView photoOfficeName;
    private TextView photoOfficeHolder;
    private TextView photoLocation;
    private ImageButton imageButton;
    private ImageView photoLogo;
    private ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        photoOfficeName = findViewById(R.id.photoOfficeNameTextview);
        photoOfficeHolder = findViewById(R.id.photoOfficeHolderTextView);
        photoLocation = findViewById(R.id.photoLocationTextview);
        imageButton = findViewById(R.id.photoImageButton);
        photoLogo = findViewById(R.id.photoImageView);
        constraintLayout = findViewById(R.id.photoConstraintLayout);

        photoEnterData();
    }

    private void photoEnterData(){
        Intent intent = getIntent();
        Official official = (Official) intent.getSerializableExtra("photoOfficial");

        photoOfficeName.setText(official.getOfficeName());
        photoOfficeHolder.setText(official.getOfficeHolder());

        if(official.getParty()!=null){
            if (official.getParty().equals("Republican Party") || official.getParty().equals("Republican")){
                constraintLayout.setBackgroundColor(Color.RED);
                photoLogo.setImageResource(R.drawable.rep_logo);
                imageButton.setBackgroundColor(Color.RED);
            }
            else if (official.getParty().equals("Democratic Party") || official.getParty().equals("Democratic")) {
                constraintLayout.setBackgroundColor(Color.BLUE);
                photoLogo.setImageResource(R.drawable.dem_logo);
                imageButton.setBackgroundColor(Color.BLUE);
            }
            else {
                constraintLayout.setBackgroundColor(Color.BLACK);
                photoLogo.setVisibility(View.GONE);
                imageButton.setBackgroundColor(Color.BLACK);
            }
        }else {
            constraintLayout.setBackgroundColor(Color.BLACK);
            photoLogo.setVisibility(View.GONE);
            imageButton.setBackgroundColor(Color.BLACK);
        }

        if(intent.hasExtra("photoLocation")){
            String location = intent.getStringExtra("photoLocation");
            photoLocation.setText(location);
        }
        photoLoadImage(official.getPhotoUrl());
    }

    private void photoLoadImage(String imageURL) {
        // Needs gradle  implementation 'com.squareup.picasso:picasso:2.71828'
            Picasso.get().load(imageURL)
                    .error(R.drawable.brokenimage)
                    .placeholder(R.drawable.placeholder)
                    .into(imageButton);
    }
}