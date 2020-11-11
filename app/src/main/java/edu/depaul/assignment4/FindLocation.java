package edu.depaul.assignment4;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class FindLocation extends MainActivity {

    private final MainActivity mainActivity;
    private LocationManager locationManager;
    private Criteria criteria;
    private static final int MY_LOCATION_REQUEST_CODE_ID = 111;
    private static final String TAG = "FindLocation";

    public FindLocation(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        setUpLocation();
    }

    private void setUpLocation() {
        locationManager = (LocationManager) mainActivity.getSystemService(Context.LOCATION_SERVICE);

        criteria = new Criteria();

        // use gps for location
        criteria.setPowerRequirement(Criteria.POWER_HIGH);
        criteria.setAccuracy(Criteria.ACCURACY_FINE);

        // use network for location
        //criteria.setPowerRequirement(Criteria.POWER_LOW);
        //criteria.setAccuracy(Criteria.ACCURACY_MEDIUM);

        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setSpeedRequired(false);

        if (ContextCompat.checkSelfPermission(mainActivity, Manifest.permission.ACCESS_FINE_LOCATION)
                != PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    mainActivity,
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION
                    },
                    MY_LOCATION_REQUEST_CODE_ID);
        } else {
            Log.d(TAG, "setUpLocation: " + "else setLocation()");
            setLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull
            String[] permissions, @NonNull
                    int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_LOCATION_REQUEST_CODE_ID) {
            if (permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION) &&
                    grantResults[0] == PERMISSION_GRANTED) {
                setLocation();
                return;
            }
        }

    }

    @SuppressLint("MissingPermission")
    private void setLocation() {

        String bestProvider = locationManager.getBestProvider(criteria, true);
        Log.d(TAG, "setLocation: " + bestProvider);
        Location currentLocation = null;
        if (bestProvider != null) {
            currentLocation = locationManager.getLastKnownLocation(bestProvider);
        }
        if (currentLocation != null) {
            mainActivity.findPostalCode(currentLocation.getLatitude(), currentLocation.getLongitude());
        } else {
            Log.d(TAG, "setLocation: " + "No Location");
            //Toast.makeText(mainActivity, "No Location", Toast.LENGTH_LONG).show();
        }
    }
}
