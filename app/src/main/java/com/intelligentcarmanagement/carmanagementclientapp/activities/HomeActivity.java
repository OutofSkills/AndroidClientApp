package com.intelligentcarmanagement.carmanagementclientapp.activities;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.intelligentcarmanagement.carmanagementclientapp.R;
import com.intelligentcarmanagement.carmanagementclientapp.databinding.ActivityHomeBinding;
import com.intelligentcarmanagement.carmanagementclientapp.models.Ride;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeActivity extends DrawerBaseActivity implements OnMapReadyCallback {

    private static final String TAG = "HomeActivity";
    private static int AUTOCOMPLETE_REQUEST_CODE = 1;

    ActivityHomeBinding homeBinding;
    // Autocomplete
    private EditText pickUpEditText, destinationEditText;
    private boolean pickUpRequest, destinationRequest;

    // Google Maps
    private GoogleMap map;

    // Ride data
    private Ride ride;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Setting up the drawer
        homeBinding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(homeBinding.getRoot());
        allocateActivityTitle("Home");

        // View bindings
        pickUpEditText = findViewById(R.id.pickUpEditText);
        destinationEditText = findViewById(R.id.destinationEditText);

        // Setting events listeners
        setEventListeners();

        //Initialize section
        ride = new Ride();

        // Setup google maps,
        // obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    // Initiate an autocomplete activity
    // and set a callback to obtain results
    private void searchPlace() {
        if (!Places.isInitialized()) {
            Places.initialize(this, getApplication().getString(R.string.google_maps_key));
        }
        // Set the fields to specify which types of place data to
        // return after the user has made a selection.
        List<Place.Field> fields = Arrays.asList(Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS);
        // Filter places vy country
        List<String> filterCountry = Arrays.asList("RO", "MD");
        // Start the autocomplete intent.
        Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                .setCountries(filterCountry).build(this);
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
    }

    // Obtain the autocomplete activity results
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getLatLng() + ", " + place.getAddress());
                if(pickUpRequest) {
                    pickUpEditText.setText(place.getName());
                    pickUpRequest = false;
                }
                else if(destinationRequest) {
                    destinationEditText.setText(place.getName());
                    destinationRequest = false;
                }
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i(TAG, status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
                Log.i(TAG, "Autocomplete canceled.");
            }
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        map.getUiSettings().setZoomGesturesEnabled(true);
        try{
            boolean success = googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(HomeActivity.this, R.raw.maps_style));
            if(!success)
                Log.e("MapsError", "Style parsing error.");
        }catch (Resources.NotFoundException e)
        {
            Log.e("MapsError", e.getMessage());
        }

        // Add a marker in Sydney and move the camera
        LatLng bucharest = new LatLng(44.439663, 26.096306);
        map.addMarker(new MarkerOptions().position(bucharest).title("Marker in Bucharest"));
        map.moveCamera(CameraUpdateFactory.newLatLng(bucharest));
    }

    // Define the event listeners here
    private void setEventListeners() {
        // Pick up edit text event listeners
        pickUpEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (view.hasFocus()) {
                    pickUpRequest = true;
                    searchPlace();
                }
            }
        });
        pickUpEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickUpRequest = true;
                searchPlace();
            }
        });

        // Destination edit text event listeners
        destinationEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (view.hasFocus()) {
                    destinationRequest = true;
                    searchPlace();
                }
            }
        });
        destinationEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                destinationRequest = true;
                searchPlace();
            }
        });
    }
}