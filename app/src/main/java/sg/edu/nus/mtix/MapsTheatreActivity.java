package sg.edu.nus.mtix;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;

import static android.content.ContentValues.TAG;

public class MapsTheatreActivity extends FragmentActivity implements OnMapReadyCallback,
        com.google.android.gms.location.LocationListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    double lat = 0;
    double lon = 0;
    Location mCurrentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_theatre);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000); //the unit here is every ten sec will have an update.
        mLocationRequest.setFastestInterval(5000); //max rate that you want to get updated
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onPause() {
        super.onPause();
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mGoogleApiClient.isConnected()) {
            //put android.Manifest.permission ... if Manifest.permision is red in colour
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                PendingResult<Status> pendingResult = LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            }
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            PendingResult<Status> pendingResult = LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG, "Connection failed: " + connectionResult.toString());
    }

    @Override
    public void onLocationChanged(Location location) {

        mCurrentLocation = location;
        if (mCurrentLocation != null) {
            double latitude = mCurrentLocation.getLatitude();
            double longtitude = mCurrentLocation.getLongitude();

            lat = latitude;
            lon = longtitude;

            onMapReady(mMap);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng myLocation = new LatLng(lat, lon); //current location
        //LatLng myLocation = new LatLng(1.295053, 103.773846); //soc location. use this to test out at home
        LatLng starTheatre = new LatLng(1.296705, 103.773150); //clb location

        //find distance between two latlng
        Location locationA = new Location("LocationA");
        Location locationB = new Location("LocationB");
        locationA.setLatitude(lat);
        locationA.setLongitude(lon);
        locationB.setLatitude(1.296705);
        locationB.setLongitude(103.773150);
        double distance = locationA.distanceTo(locationB);

        Marker markerA = mMap.addMarker(new MarkerOptions().position(myLocation).title("My Location").snippet("hi"));
        Marker markerB = mMap.addMarker(new MarkerOptions().position(starTheatre).title("Star Theatre").snippet("hello"));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation,10));

        //draw route between two latlng
        PolygonOptions polygonOptions = new PolygonOptions().strokeColor(Color.BLUE).fillColor(Color.CYAN).strokeWidth(7);
        polygonOptions.add(myLocation);
        polygonOptions.add(starTheatre);
        mMap.addPolygon(polygonOptions);

        Toast.makeText(this, "Distance from your location to Star Theatre is: " + String.format("%.2f", distance) + "m", Toast.LENGTH_LONG).show();
    }
}


