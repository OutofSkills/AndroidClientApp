package com.intelligentcarmanagement.carmanagementclientapp.activities;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.DirectionsStep;
import com.google.maps.model.EncodedPolyline;
import com.intelligentcarmanagement.carmanagementclientapp.R;
import com.intelligentcarmanagement.carmanagementclientapp.databinding.ActivityHomeBinding;
import com.intelligentcarmanagement.carmanagementclientapp.models.ride.Ride;
import com.intelligentcarmanagement.carmanagementclientapp.utils.HaversineAlgorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeActivity extends DrawerBaseActivity implements OnMapReadyCallback {

    private static final String TAG = "HomeActivity";

    ActivityHomeBinding homeBinding;
    // Autocomplete
    private static int AUTOCOMPLETE_REQUEST_CODE = 1;
    private EditText pickUpEditText, destinationEditText;
    private boolean pickUpRequest, destinationRequest;

    // Ride request submit button
    Button submitRideRequest;

    // Google Maps
    private GoogleMap map;
    MarkerOptions pickUpMarker, destinationMarker;
    // Maps polyline route
    private Polyline polyline;

    // Ride data
    private Ride ride = new Ride();

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
        submitRideRequest = findViewById(R.id.submitRideRequest);

        // Setting events listeners
        setEventListeners();

        //Initialize section
        ride = new Ride();
        pickUpMarker = new MarkerOptions();
        pickUpMarker.title("Pick-up");
        destinationMarker = new MarkerOptions();
        destinationMarker.title("Destination");

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
            Places.initialize(this, getResources().getString(R.string.google_maps_key));
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
                    setPickUpInformation(place);
                    pickUpMarker.position(place.getLatLng());
                    map.addMarker(pickUpMarker);
                }
                else if(destinationRequest) {
                    setDestinationInformation(place);
                    destinationMarker.position(place.getLatLng());
                    map.addMarker(destinationMarker);
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
        map.moveCamera(CameraUpdateFactory.newLatLng(bucharest));
    }

    // Draw a route between the 2 given places
    private void drawRoute(LatLng pickUp, LatLng destination)
    {
        //Define list to get all lat and lng for the route
        List<LatLng> path = new ArrayList();

        //Setup Directions API context
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(getResources().getString(R.string.google_maps_key))
                .build();

        // Execute a direction request
        DirectionsApiRequest req = DirectionsApi.getDirections(context, pickUp.latitude + "," + pickUp.longitude,
                destination.latitude + "," + destination.longitude);
        try {
            DirectionsResult res = req.await();

            //Loop through legs and steps to get encoded polylines of each step
            if (res.routes != null && res.routes.length > 0) {
                DirectionsRoute route = res.routes[0];

                if (route.legs !=null) {
                    for(int i=0; i<route.legs.length; i++) {
                        DirectionsLeg leg = route.legs[i];
                        if (leg.steps != null) {
                            for (int j=0; j<leg.steps.length;j++){
                                DirectionsStep step = leg.steps[j];
                                if (step.steps != null && step.steps.length >0) {
                                    for (int k=0; k<step.steps.length;k++){
                                        DirectionsStep step1 = step.steps[k];
                                        EncodedPolyline points1 = step1.polyline;
                                        if (points1 != null) {
                                            //Decode polyline and add points to list of route coordinates
                                            List<com.google.maps.model.LatLng> coords1 = points1.decodePath();
                                            for (com.google.maps.model.LatLng coord1 : coords1) {
                                                path.add(new LatLng(coord1.lat, coord1.lng));
                                            }
                                        }
                                    }
                                } else {
                                    EncodedPolyline points = step.polyline;
                                    if (points != null) {
                                        //Decode polyline and add points to list of route coordinates
                                        List<com.google.maps.model.LatLng> coords = points.decodePath();
                                        for (com.google.maps.model.LatLng coord : coords) {
                                            path.add(new LatLng(coord.lat, coord.lng));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch(Exception ex) {
            Log.e(TAG, "Fail: " + ex.getMessage());
        }

        //Draw the polyline
        if (path.size() > 0) {
            // First clear the existing route
            if(polyline != null) polyline.remove();
            // Create a new route for the provided pick-up and destination
            PolylineOptions opts = new PolylineOptions().addAll(path).color(Color.BLUE).width(5);
            polyline = map.addPolyline(opts);

            // Fit the points
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(getCenterOfPoints(path), 7));
        }
    }

    // Define the event listeners here
    private void setEventListeners() {
        // Pick up edit text event listeners
//        pickUpEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean b) {
//                if (view.hasFocus()) {
//                    pickUpRequest = true;
//                    searchPlace();
//                }
//            }
//        });
//        pickUpEditText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                pickUpRequest = true;
//                searchPlace();
//            }
//        });
//
//        // Destination edit text event listeners
//        destinationEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean b) {
//                if (view.hasFocus()) {
//                    destinationRequest = true;
//                    searchPlace();
//                }
//            }
//        });
//        destinationEditText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                destinationRequest = true;
//                searchPlace();
//            }
//        });

        // Ride request submit button
        submitRideRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                 * Improvised section
                 * delete after solving google api problems
                 */
                ride.setPickUpPlaceName(pickUpEditText.getText().toString());
                ride.setPickUpPlaceAddress(pickUpEditText.getText().toString());
                ride.setPickUpLat(String.valueOf("45.9232"));
                ride.setPickUpPlaceLong(String.valueOf("24.9668"));

                ride.setDestinationPlaceName(destinationEditText.getText().toString());
                ride.setDestinationPlaceAddress(destinationEditText.getText().toString());
                ride.setDestinationPlaceLat(String.valueOf("45.9432"));
                ride.setDestinationPlaceLong(String.valueOf("24.9668"));

                double distance = HaversineAlgorithm.HaversineInKM(
                        Double.parseDouble(ride.getPickUpPlaceLat()),
                        Double.parseDouble(ride.getPickUpPlaceLong()),
                        Double.parseDouble(ride.getDestinationPlaceLat()),
                        Double.parseDouble(ride.getDestinationPlaceLong())
                );
                ride.setDistance(distance);

                /* End section*/

                Intent intent = new Intent(HomeActivity.this, AvailableDriversActivity.class);
                intent.putExtra("RideRequest", ride);
                startActivity(intent);
            }
        });
    }

    // Set ride pick-up information
    private void setPickUpInformation(Place place)
    {
        ride.setPickUpPlaceName(place.getName());
        ride.setPickUpPlaceAddress(place.getAddress());
        ride.setPickUpLat(String.valueOf(place.getLatLng().latitude));
        ride.setPickUpPlaceLong(String.valueOf(place.getLatLng().longitude));
        pickUpEditText.setText(place.getName());
        pickUpRequest = false;

        // If the ride information (pick-up, destination) is provided
        // then we can enable the submit button
        if(!destinationEditText.getText().toString().matches(""))
        {
            submitRideRequest.setEnabled(true);
            drawRoute(new LatLng(Double.valueOf(ride.getPickUpLat()), Double.valueOf(ride.getPickUpPlaceLong())),
                    new LatLng(Double.valueOf(ride.getDestinationPlaceLat()), Double.valueOf(ride.getDestinationPlaceLong())));

            double distance = HaversineAlgorithm.HaversineInKM(
                    Double.parseDouble(ride.getPickUpPlaceLat()),
                    Double.parseDouble(ride.getPickUpPlaceLong()),
                    Double.parseDouble(ride.getDestinationPlaceLat()),
                    Double.parseDouble(ride.getDestinationPlaceLong())
            );

            ride.setDistance(distance);
        }
        else
        {
            if(submitRideRequest.isEnabled())
                submitRideRequest.setEnabled(false);
        }
    }

    // Set ride destination information
    private void setDestinationInformation(Place place)
    {
        ride.setDestinationPlaceName(place.getName());
        ride.setDestinationPlaceAddress(place.getAddress());
        ride.setDestinationPlaceLat(String.valueOf(place.getLatLng().latitude));
        ride.setDestinationPlaceLong(String.valueOf(place.getLatLng().longitude));
        destinationEditText.setText(place.getName());
        destinationRequest = false;

        // If the ride information (pick-up, destination) is provided
        // then we can enable the submit button
        if(!pickUpEditText.getText().toString().matches(""))
        {
            submitRideRequest.setEnabled(true);
            drawRoute(new LatLng(Double.valueOf(ride.getPickUpLat()), Double.valueOf(ride.getPickUpPlaceLong())),
                    new LatLng(Double.valueOf(ride.getDestinationPlaceLat()), Double.valueOf(ride.getDestinationPlaceLong())));

            double distance = HaversineAlgorithm.HaversineInKM(
                    Double.parseDouble(ride.getPickUpPlaceLat()),
                    Double.parseDouble(ride.getPickUpPlaceLong()),
                    Double.parseDouble(ride.getDestinationPlaceLat()),
                    Double.parseDouble(ride.getDestinationPlaceLong())
            );

            ride.setDistance(distance);
        }
        else
        {
            if(submitRideRequest.isEnabled())
                submitRideRequest.setEnabled(false);
        }
    }

    // Get the center of 2 or more points
    private LatLng getCenterOfPoints(List<LatLng> points){
        LatLngBounds.Builder latLngBounds = LatLngBounds.builder();
        for (LatLng latLng : points) {
            latLngBounds.include(latLng);
        }

        return latLngBounds.build().getCenter();
    }
}