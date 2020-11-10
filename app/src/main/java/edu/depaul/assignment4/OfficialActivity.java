package edu.depaul.assignment4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class OfficialActivity extends AppCompatActivity {

    private TextView location;
    private TextView title;
    private TextView name;
    private TextView party;
    private TextView address;
    private TextView phone;
    private TextView email;
    private TextView website;
    private TextView addressInfo;
    private TextView phoneInfo;
    private TextView emailInfo;
    private TextView websiteInfo;
    private ImageView symbol;
    private ImageButton photo;
    private ImageButton facebook;
    private ImageButton twitter;
    private ImageButton youtube;
    private Official official;
    private ScrollView scrollView;
    private static final String TAG = "OfficialActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_official);
        initializeTextViews();
        enterData();
        link();
    }

    private void initializeTextViews(){
        location = findViewById(R.id.locationTextview);
        title = findViewById(R.id.titleTextview);
        name = findViewById(R.id.nameTextView);
        party = findViewById(R.id.partyTextView);
        address = findViewById(R.id.addressTextview);
        phone = findViewById(R.id.phoneTextview);
        email = findViewById(R.id.emailTextView);
        website = findViewById(R.id.websiteTextView);
        addressInfo = findViewById(R.id.addressInfoTextView);
        phoneInfo = findViewById(R.id.phoneInfoTextView);
        emailInfo = findViewById(R.id.emailInfoTextview);
        websiteInfo = findViewById(R.id.websiteInfoTextview);
        photo = findViewById(R.id.imageButton);
        facebook = findViewById(R.id.facebookImageButton);
        twitter = findViewById(R.id.twitterImageButton);
        youtube = findViewById(R.id.youtubeImageButton);
        scrollView = findViewById(R.id.scrollview);
        symbol = findViewById(R.id.imageView);
    }

    private void enterData(){
        Intent intent = getIntent();
        if(intent.hasExtra("location")){
            location.setText(intent.getStringExtra("location"));
        }
        official = (Official) intent.getSerializableExtra("official");
        if(official.getParty()!=null){
            if (official.getParty().equals("Republican Party")){
                scrollView.setBackgroundColor(Color.RED);
                facebook.setBackgroundColor(Color.RED);
                twitter.setBackgroundColor(Color.RED);
                youtube.setBackgroundColor(Color.RED);
                party.setText(String.format("(%s)", official.getParty()));
                symbol.setImageResource(R.drawable.rep_logo);
                photo.setBackgroundColor(Color.RED);
            }
            else if (official.getParty().equals("Democratic Party")) {
                scrollView.setBackgroundColor(Color.BLUE);
                facebook.setBackgroundColor(Color.BLUE);
                twitter.setBackgroundColor(Color.BLUE);
                youtube.setBackgroundColor(Color.BLUE);
                party.setText(String.format("(%s)", official.getParty()));
                symbol.setImageResource(R.drawable.dem_logo);
                photo.setBackgroundColor(Color.BLUE);
            }
            else {
                scrollView.setBackgroundColor(Color.BLACK);
                symbol.setVisibility(View.GONE);
            }
        }else {
            scrollView.setBackgroundColor(Color.BLACK);
            symbol.setVisibility(View.GONE);
        }
        if (official.getOfficeName() != null)
            title.setText(official.getOfficeName());
        if (official.getOfficeHolder() != null)
            name.setText(official.getOfficeHolder());
        if (official.getOfficeAddress() != null)
            addressInfo.setText(official.getOfficeAddress());
        else {
            address.setVisibility(View.GONE);
            addressInfo.setVisibility(View.GONE);
        }
        if (official.getOfficePhone() != null)
            phoneInfo.setText(official.getOfficePhone());
        else{
            phone.setVisibility(View.GONE);
            phoneInfo.setVisibility(View.GONE);
        }
        if (official.getOfficeEmail() != null)
            emailInfo.setText(official.getOfficeEmail());
        else{
            email.setVisibility(View.GONE);
            emailInfo.setVisibility(View.GONE);
        }
        if (official.getWebsiteUrl() != null){
            websiteInfo.setText(official.getWebsiteUrl());
        Log.d(TAG, "enterData: " +official.getWebsiteUrl());}
        else{
            website.setVisibility(View.GONE);
            websiteInfo.setVisibility(View.GONE);
        }
        if (official.getSocialMediaChannel().getFacebookUrl() == null)
                facebook.setVisibility(View.GONE);
        if (official.getSocialMediaChannel().getYoutubeUrl() == null)
                youtube.setVisibility(View.GONE);
        if (official.getSocialMediaChannel().getTwitterUrl() == null)
                twitter.setVisibility(View.GONE);
            loadRemoteImage(official.getPhotoUrl());
    }

    private void loadRemoteImage(final String imageURL) {
        // Needs gradle  implementation 'com.squareup.picasso:picasso:2.71828'

        if(imageURL == null){
            photo.setImageResource(R.drawable.missing);
        }else {
            Picasso.get().load(imageURL)
                    .error(R.drawable.brokenimage)
                    .placeholder(R.drawable.placeholder)
                    .into(photo);
        }
    }

    private void link(){
        Linkify.addLinks(addressInfo, Linkify.MAP_ADDRESSES);
        Linkify.addLinks(phoneInfo, Linkify.PHONE_NUMBERS);
        Linkify.addLinks(emailInfo, Linkify.EMAIL_ADDRESSES);
        Linkify.addLinks(websiteInfo, Linkify.WEB_URLS);
    }

    public void twitterClicked(View v) {
        Intent intent;
        String name = official.getSocialMediaChannel().getTwitterUrl();
        try {
            // get the Twitter app if possible
            getPackageManager().getPackageInfo("com.twitter.android", 0);
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=" + name));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } catch (Exception e) {
            // no Twitter app, revert to browser
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/" + name));
        }
        startActivity(intent);
    }

    public void facebookClicked(View v) {
        String FACEBOOK_URL = "https://www.facebook.com/" + official.getSocialMediaChannel().getFacebookUrl();
        String urlToUse;
        PackageManager packageManager = getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { //newer versions of fb app
                urlToUse = "fb://facewebmodal/f?href=" + FACEBOOK_URL;
            } else { //older versions of fb app
                urlToUse = "fb://page/" + official.getSocialMediaChannel().getFacebookUrl();
            }
        } catch (PackageManager.NameNotFoundException e) {
            urlToUse = FACEBOOK_URL; //normal web url
        }
        Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
        facebookIntent.setData(Uri.parse(urlToUse));
        startActivity(facebookIntent);
    }

    public void youTubeClicked(View v) {
        String name = official.getSocialMediaChannel().getYoutubeUrl();
        Intent intent;
        try {
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setPackage("com.google.android.youtube");
            intent.setData(Uri.parse("https://www.youtube.com/" + name));
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.youtube.com/" + name)));
        }

    }
}