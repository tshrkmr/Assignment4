package edu.depaul.assignment4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int i = 0; i < 10; i++) {
            Official official = new Official(
                    "Joe " + (i+1),
                    "President " + (i+1),
                    "chicago",
                    "Demo",
                    "999999999",
                    "jeo.com",
                    "joe@demo.com",
                    socialMediaChannel);
            officialList.add(official);
        }
        setUpRecyclerView();
        FindLocation findLocation = new FindLocation(this);
    }

    private void setUpRecyclerView(){
        recyclerView = findViewById(R.id.recycler);
        officialAdapter = new OfficialAdapter(officialList, this);
        recyclerView.setAdapter(officialAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void findPostalCode(double latitude, double longitude){
        Log.d(TAG, "findPostalCode: " + "1");
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            String postalCode = addresses.get(0).getPostalCode();
            Log.d(TAG, "findPostalCode: " + "find");
            Toast.makeText(this, postalCode, Toast.LENGTH_SHORT).show();
            Log.d(TAG, "findPostalCode: " + postalCode);
        }catch (IOException e){
            Log.d(TAG, "findPostalCode: " + " address not found");
        }
    }

    @Override
    public void onClick(View view) {

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
            openAboutActivity();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openAboutActivity(){
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

}