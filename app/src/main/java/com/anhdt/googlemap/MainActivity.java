package com.anhdt.googlemap;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.PermissionRequest;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnPolylineClickListener, GoogleMap.OnPolygonClickListener {
    private EditText edtSearch;
    private Button btnSearch;


    private GoogleMap mMap;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;
    int PROXIMITY_RADIUS = 10000;
    double latitude, longitude;
    double end_latitude, end_longitude;


    private static final int PERMISSON_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//
////        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
////            this.requestPermissions(new String[]{Manifest.permission.INTERNET}, PERMISSON_CODE);
////        }
//
//        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            checkLocationPermission();
//        }
//
//        //Check if Google Play Services Available or not
//        if (!CheckGooglePlayServices()) {
//            Log.d("onCreate", "Finishing test case since Google Play Services are not available");
//            finish();
//        } else {
//            Log.d("onCreate", "Google Play Services available.");
//        }
//
//        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);

        initViews();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.my_map);
        mapFragment.getMapAsync(this);
    }


    private void initViews() {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng sydney = new LatLng(-33.852, 151.211);
        mMap.addMarker(new MarkerOptions()
                .snippet("The most popular city in Australia")
                .position(sydney)
                .title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));


        Polyline polyline1 = googleMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .add(
                        new LatLng(-35.016, 143.321),
                        new LatLng(-34.747, 145.592),
                        new LatLng(-34.364, 147.891),
                        new LatLng(-33.501, 150.217),
                        new LatLng(-32.306, 149.248),
                        new LatLng(-32.491, 147.309)));

        // Position the map's camera near Alice Springs in the center of Australia,
        // and set the zoom factor so most of Australia shows on the screen.
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-23.684, 133.903), 4));

        // Set listeners for click events.
        googleMap.setOnPolylineClickListener(this);
        googleMap.setOnPolygonClickListener(this);
    }

    @Override
    public void onPolylineClick(Polyline polyline) {

    }

    @Override
    public void onPolygonClick(Polygon polygon) {

    }

//    private void initViews() {
//        edtSearch = (EditText) findViewById(R.id.edt_search);
//        btnSearch = (Button) findViewById(R.id.btn_search);
//
//        btnSearch.setOnClickListener(this);
//    }
////
////    @Override
////    public void onClick(View view) {
////        switch (view.getId()) {
////            case R.id.btn_search:
////                if (checkField()) {
////                    searchMaps();
////                }
////                break;
////            default:
////                break;
////        }
////    }
//
//    private void searchMaps() {
//
//    }
//
//    private boolean checkField() {
//        if (edtSearch.getText().toString().equals("")) {
//            Toast.makeText(this, "Fill this field before searching!", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//        return true;
//    }
//
//    private boolean CheckGooglePlayServices() {
//        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
//        int result = googleAPI.isGooglePlayServicesAvailable(this);
//        if (result != ConnectionResult.SUCCESS) {
//            if (googleAPI.isUserResolvableError(result)) {
//                googleAPI.getErrorDialog(this, result,
//                        0).show();
//            }
//            return false;
//        }
//        return true;
//    }
//
//
//    /**
//     * Manipulates the map once available.
//     * This callback is triggered when the map is ready to be used.
//     * This is where we can add markers or lines, add listeners or move the camera. In this case,
//     * we just add a marker near Sydney, Australia.
//     * If Google Play services is not installed on the device, the user will be prompted to install
//     * it inside the SupportMapFragment. This method will only be triggered once the user has
//     * installed Google Play services and returned to the app.
//     */
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//
//        //Initialize Google Play Services
//        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (ContextCompat.checkSelfPermission(this,
//                    Manifest.permission.ACCESS_FINE_LOCATION)
//                    == PackageManager.PERMISSION_GRANTED) {
//                buildGoogleApiClient();
//                mMap.setMyLocationEnabled(true);
//            }
//        } else {
//            buildGoogleApiClient();
//            mMap.setMyLocationEnabled(true);
//        }
//
//        mMap.setOnMarkerDragListener(this);
//        mMap.setOnMarkerClickListener(this);
//
//    }
//
//
//    protected synchronized void buildGoogleApiClient() {
//        mGoogleApiClient = new GoogleApiClient.Builder(this)
//                .addConnectionCallbacks(this)
//                .addOnConnectionFailedListener(this)
//                .addApi(LocationServices.API)
//                .build();
//        mGoogleApiClient.connect();
//    }
//
//    public void onClick(View v) {
//        Object dataTransfer[] = new Object[2];
//        GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
//
//
//        switch (v.getId()) {
//            case R.id.btn_search: {
//                EditText tf_location = (EditText) findViewById(R.id.edt_search);
//                String location = tf_location.getText().toString();
//                List<Address> addressList = null;
//                MarkerOptions markerOptions = new MarkerOptions();
//                Log.d("location = ", location);
//
//                if (!location.equals("")) {
//                    Geocoder geocoder = new Geocoder(this);
//                    try {
//                        addressList = geocoder.getFromLocationName(location, 5);
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//
//                    if (addressList != null) {
//                        for (int i = 0; i < addressList.size(); i++) {
//                            Address myAddress = addressList.get(i);
//                            LatLng latLng = new LatLng(myAddress.getLatitude(), myAddress.getLongitude());
//                            markerOptions.position(latLng);
//                            mMap.addMarker(markerOptions);
//                            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
//                        }
//                    }
//
//                }
//            }
//            break;
////            case R.id.B_hospital:
////                mMap.clear();
////                String hospital = "hospital";
////                String url = getUrl(latitude, longitude, hospital);
////
////                dataTransfer[0] = mMap;
////                dataTransfer[1] = url;
////
////                getNearbyPlacesData.execute(dataTransfer);
////                Toast.makeText(MapsActivity.this, "Showing Nearby Hospitals", Toast.LENGTH_LONG).show();
////                break;
////
////            case R.id.B_restaurant:
////                mMap.clear();
////                dataTransfer = new Object[2];
////                String restaurant = "restaurant";
////                url = getUrl(latitude, longitude, restaurant);
////                getNearbyPlacesData = new GetNearbyPlacesData();
////                dataTransfer[0] = mMap;
////                dataTransfer[1] = url;
////
////                getNearbyPlacesData.execute(dataTransfer);
////                Toast.makeText(MapsActivity.this, "Showing Nearby Hospitals", Toast.LENGTH_LONG).show();
////                break;
////            case R.id.B_school:
////                mMap.clear();
////                String school = "school";
////                dataTransfer = new Object[2];
////                url = getUrl(latitude, longitude, school);
////                getNearbyPlacesData = new GetNearbyPlacesData();
////                dataTransfer[0] = mMap;
////                dataTransfer[1] = url;
////
////                getNearbyPlacesData.execute(dataTransfer);
////                Toast.makeText(MapsActivity.this, "Showing Nearby Hospitals", Toast.LENGTH_LONG).show();
////                break;
////
////            case R.id.B_to:
////                dataTransfer = new Object[3];
////                url = getDirectionsUrl();
////                GetDirectionsData getDirectionsData = new GetDirectionsData();
////                dataTransfer[0] = mMap;
////                dataTransfer[1] = url;
////                dataTransfer[2] = new LatLng(end_latitude, end_longitude);
////                getDirectionsData.execute(dataTransfer);
////
////                break;
//
//            default:
//                break;
//        }
//    }
//
//    private String getDirectionsUrl() {
//        StringBuilder googleDirectionsUrl = new StringBuilder("https://maps.googleapis.com/maps/api/directions/json?");
//        googleDirectionsUrl.append("origin=" + latitude + "," + longitude);
//        googleDirectionsUrl.append("&destination=" + end_latitude + "," + end_longitude);
//        googleDirectionsUrl.append("&key=" + "AIzaSyCAcfy-02UHSu2F6WeQ1rhQhkCr51eBL9g");
//
//        return googleDirectionsUrl.toString();
//    }
//
//    private String getUrl(double latitude, double longitude, String nearbyPlace) {
//        StringBuilder googlePlacesUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
//        googlePlacesUrl.append("location=" + latitude + "," + longitude);
//        googlePlacesUrl.append("&radius=" + PROXIMITY_RADIUS);
//        googlePlacesUrl.append("&type=" + nearbyPlace);
//        googlePlacesUrl.append("&sensor=true");
//        googlePlacesUrl.append("&key=" + "AIzaSyBj-cnmMUY21M0vnIKz0k3tD3bRdyZea-Y");
//        Log.d("getUrl", googlePlacesUrl.toString());
//        return (googlePlacesUrl.toString());
//    }
//
//
//    @Override
//    public void onConnected(Bundle bundle) {
//        mLocationRequest = new LocationRequest();
//        mLocationRequest.setInterval(1000);
//        mLocationRequest.setFastestInterval(1000);
//        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
////        if (ContextCompat.checkSelfPermission(this,
////                Manifest.permission.ACCESS_FINE_LOCATION)
////                == PackageManager.PERMISSION_GRANTED) {
////            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, (com.google.android.gms.location.LocationListener) this);
////        }
//    }
//
//
//    @Override
//    public void onConnectionSuspended(int i) {
//
//    }
//
//    @Override
//    public void onLocationChanged(Location location) {
//        Log.d("onLocationChanged", "entered");
//
//        mLastLocation = location;
//        if (mCurrLocationMarker != null) {
//            mCurrLocationMarker.remove();
//        }
//
//        latitude = location.getLatitude();
//        longitude = location.getLongitude();
//
//
//        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
//        MarkerOptions markerOptions = new MarkerOptions();
//        markerOptions.position(latLng);
//        markerOptions.draggable(true);
//        markerOptions.title("Current Position");
//        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
//        mCurrLocationMarker = mMap.addMarker(markerOptions);
//
//        //move map camera
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));
//
//
//        Toast.makeText(this, "Your Current Location", Toast.LENGTH_LONG).show();
//
//
//        //stop location updates
//        if (mGoogleApiClient != null) {
//            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, (com.google.android.gms.location.LocationListener) this);
//            Log.d("onLocationChanged", "Removing Location Updates");
//        }
//
//    }
//
//    @Override
//    public void onStatusChanged(String s, int i, Bundle bundle) {
//
//    }
//
//    @Override
//    public void onProviderEnabled(String s) {
//
//    }
//
//    @Override
//    public void onProviderDisabled(String s) {
//
//    }
//
//    @Override
//    public void onConnectionFailed(ConnectionResult connectionResult) {
//
//    }
//
//    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
//
//    public boolean checkLocationPermission() {
//        if (ContextCompat.checkSelfPermission(this,
//                Manifest.permission.ACCESS_FINE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED) {
//
//            // Asking user if explanation is needed
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
//                    Manifest.permission.ACCESS_FINE_LOCATION)) {
//
//                // Show an explanation to the user *asynchronously* -- don't block
//                // this thread waiting for the user's response! After the user
//                // sees the explanation, try again to request the permission.
//
//                //Prompt the user once explanation has been shown
//                ActivityCompat.requestPermissions(this,
//                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                        MY_PERMISSIONS_REQUEST_LOCATION);
//
//
//            } else {
//                // No explanation needed, we can request the permission.
//                ActivityCompat.requestPermissions(this,
//                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                        MY_PERMISSIONS_REQUEST_LOCATION);
//            }
//            return false;
//        } else {
//            return true;
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode,
//                                           String permissions[], int[] grantResults) {
//        switch (requestCode) {
//            case MY_PERMISSIONS_REQUEST_LOCATION: {
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//                    // permission was granted. Do the
//                    // contacts-related task you need to do.
//                    if (ContextCompat.checkSelfPermission(this,
//                            Manifest.permission.ACCESS_FINE_LOCATION)
//                            == PackageManager.PERMISSION_GRANTED) {
//
//                        if (mGoogleApiClient == null) {
//                            buildGoogleApiClient();
//                        }
//                        mMap.setMyLocationEnabled(true);
//                    }
//
//                } else {
//
//                    // Permission denied, Disable the functionality that depends on this permission.
//                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
//                }
//                return;
//            }
//
//            // other 'case' lines to check for other permissions this app might request.
//            // You can add here other case statements according to your requirement.
//        }
//    }
//
//
//    @Override
//    public boolean onMarkerClick(Marker marker) {
//        marker.setDraggable(true);
//        return false;
//    }
//
//    @Override
//    public void onMarkerDragStart(Marker marker) {
//
//    }
//
//    @Override
//    public void onMarkerDrag(Marker marker) {
//
//    }
//
//    @Override
//    public void onMarkerDragEnd(Marker marker) {
//        end_latitude = marker.getPosition().latitude;
//        end_longitude = marker.getPosition().longitude;
//
//        Log.d("end_lat", "" + end_latitude);
//        Log.d("end_lng", "" + end_longitude);
//    }
}
