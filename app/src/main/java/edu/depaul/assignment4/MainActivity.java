package edu.depaul.assignment4;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private OfficialAdapter officialAdapter;
    private RecyclerView recyclerView;
    private SocialMediaChannel socialMediaChannel;
    private final List<Official> officialList = new ArrayList<>();
    private LocationManager locationManager;
    private Criteria criteria;
    private static int MY_LOCATION_REQUEST_CODE_ID = 111;
    private static final String TAG = "MainActivity";
    private int position;
    private TextView locationText;
    private static final int Request_Code = 123;
    private String choice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locationText = findViewById(R.id.locationEditText);

//        for (int i = 0; i < 10; i++) {
//            Official official = new Official("Joe " + (i+1));
//                    "Joe " + (i+1),
//                    "President " + (i+1),
//                    "chicago",
//                    "Demo",
//                    "999999999",
//                    "jeo.com",
//                    "joe@demo.com",
//                    socialMediaChannel);
//            officialList.add(official);
//        }
        setUpRecyclerView();
        new FindLocation(this);
    }

    private void setUpRecyclerView(){
        recyclerView = findViewById(R.id.listNameTextview);
        officialAdapter = new OfficialAdapter(officialList, this);
        recyclerView.setAdapter(officialAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onClick(View view) {
        int position = recyclerView.getChildLayoutPosition(view);
        Intent intent = new Intent(this, OfficialActivity.class);
        intent.putExtra("location", locationText.getText().toString());
        intent.putExtra("official", officialList.get(position));
        startActivityForResult(intent, Request_Code);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menuIn){
        getMenuInflater().inflate(R.menu.official_menu, menuIn);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.about_menu) {
            openAboutActivity();
            return true;
        } else if (itemId == R.id.search_menu) {
            createSearchDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void createSearchDialog(){
        if(!checkNetworkConnection()){
            noConnectionDialog();
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final EditText et = new EditText(this);
        et.setInputType(InputType.TYPE_CLASS_TEXT);
        et.setGravity(Gravity.CENTER_HORIZONTAL);
        et.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);

        builder.setView(et);


        builder.setPositiveButton("OK", (dialog, id) -> {
            choice = et.getText().toString().trim();
            InformationDownloader informationDownloader = new InformationDownloader(this, choice);
            new Thread(informationDownloader).start();
        });
        builder.setNegativeButton("CANCEL", (dialog, id) -> {
            // User cancelled the dialog
        });

        builder.setMessage("Enter a City, State or a Zip Code:");
        //builder.setTitle("Stock Selection");

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private boolean checkNetworkConnection() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private void noConnectionDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("No Network Connection");
        builder.setMessage("Data cannot be accessed loaded without an internet connection.");
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void openAboutActivity(){
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

    public void findPostalCode(double latitude, double longitude){
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            String postalCode = addresses.get(0).getPostalCode();
            Toast.makeText(this, postalCode, Toast.LENGTH_SHORT).show();
            Log.d(TAG, "findPostalCode: " + postalCode);
            String demoCode = "60605";
            InformationDownloader informationDownloader = new InformationDownloader(this, demoCode);
            new Thread(informationDownloader).start();
        }catch (IOException e){
            Log.d(TAG, "findPostalCode: " + " address not found");
        }
    }

    public void setLocation(String city, String state, String zip){
        String location = city + "," + state + " " + zip;
        locationText.setText(location);
    }

    public void clearOfficialList(){
        officialList.clear();
    }

    public void updateOfficialList(Official official){
        officialList.add(official);
        officialAdapter.notifyDataSetChanged();
    }
}