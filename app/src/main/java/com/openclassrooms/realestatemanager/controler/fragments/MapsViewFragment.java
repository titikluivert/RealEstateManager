package com.openclassrooms.realestatemanager.controler.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.controler.activities.MainActivity;
import com.openclassrooms.realestatemanager.controler.activities.SecondActivity;
import com.openclassrooms.realestatemanager.model.CurrentLocation;
import com.openclassrooms.realestatemanager.model.EstateModelLocation;
import com.openclassrooms.realestatemanager.model.RealEstateModel;
import com.openclassrooms.realestatemanager.utils.mainUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.openclassrooms.realestatemanager.utils.mainUtils.SET_INTERVAL;
import static com.openclassrooms.realestatemanager.utils.mainUtils.getLocationFromAddress;


public class MapsViewFragment extends Fragment implements OnMapReadyCallback {

    private static final int MY_PERMISSION_REQUEST_CODE = 11;
    private FusedLocationProviderClient mFusedLocationClient;
    private double latitude;
    private double longitude;
    private CurrentLocation currentLocation = new CurrentLocation();
    private GoogleMap mMap;
    private View mView;
    private Marker m;
    private MarkerOptions markerOptions = new MarkerOptions();
    private List<String> reselected = new ArrayList<>();

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM = "param";
    private static final String ARG_PARAM_2 = "param2";

    //private List<RealEstateModel> AllRealEstates;
    private List<EstateModelLocation> AllRealEstatesAndLocation;
    private List<RealEstateModel> AllRealEstates;
    private boolean isTabletModeIsActive;

    private LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            for (Location location : locationResult.getLocations()) {
                if (m != null) {
                    m.remove();
                }
                latitude = location.getLatitude();
                longitude = location.getLongitude();

                try {
                    AllRealEstatesAndLocation = getRealEstatesAroundMyPosition(AllRealEstates);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                buildRetrofitAndGetResponse(location);
            }
        }
    };

    public MapsViewFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.AllRealEstates = restoreAllRealEstateModels(getArguments().getString(ARG_PARAM));
            this.isTabletModeIsActive = getArguments().getBoolean(ARG_PARAM_2);
        }
    }

    public static MapsViewFragment newInstance(String param1, Boolean isTabletModeOn) {
        MapsViewFragment fragment = new MapsViewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM, param1);
        args.putBoolean(ARG_PARAM_2, isTabletModeOn);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_maps, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MapView mapView = mView.findViewById(R.id.map);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(mView.getContext());
        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(
                Objects.requireNonNull(getContext()), R.raw.style_google));
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(SET_INTERVAL); // 5 secondes interval
        locationRequest.setFastestInterval(SET_INTERVAL);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(mView.getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                mFusedLocationClient.requestLocationUpdates(locationRequest, mLocationCallback, Looper.myLooper());
                mMap.setMyLocationEnabled(true);
            } else {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_REQUEST_CODE);
            }
        } else {
            mFusedLocationClient.requestLocationUpdates(locationRequest, mLocationCallback, Looper.myLooper());
            mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_PERMISSION_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(mView.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    mMap.setMyLocationEnabled(true);
                }
            } else {
                Toast.makeText(mView.getContext(), "This app requires location permissions to be granted", Toast.LENGTH_LONG).show();
                //mView.finish();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        currentLocation.saveLatLng(mView.getContext(), (float) latitude, (float) longitude);

    }

    private void placeRealEstateToMap() {
        mMap.setOnMarkerClickListener(marker -> {
            Integer clickCount = (Integer) marker.getTag();
            // Check if a click count was set, then display the click count.
            String[] indexForRealEstate;
            if (clickCount != null) {
                clickCount = clickCount + 1;
                marker.setTag(clickCount);
                indexForRealEstate = marker.getTitle().split(":");

                if (!this.isTabletModeIsActive) {
                    Intent myIntent = new Intent(getActivity(), SecondActivity.class);
                    myIntent.putExtra(mainUtils.EXTRA_MAP_TO_SECOND, new Gson().toJson(AllRealEstatesAndLocation.get(Integer.parseInt(indexForRealEstate[2])).getRealEstateModelList()));
                    startActivity(myIntent);
                } else {
                    Intent myIntent = new Intent(getActivity(), MainActivity.class);
                    myIntent.putExtra(mainUtils.EXTRA_MAP_TO_MAIN, new Gson().toJson(AllRealEstatesAndLocation.get(Integer.parseInt(indexForRealEstate[2])).getRealEstateModelList()));
                    startActivity(myIntent);
                }
            }
            return true;
        });
    }

    private void preparePlaceAndMarker(List<EstateModelLocation> results, Location mLocation, int i) {

        double lat = results.get(i).getLat();
        double lng = results.get(i).getLon();

        LatLng latLng = new LatLng(lat, lng);
        // Position of Marker on Map
        markerOptions.position(latLng);
        // Adding Title to the Marker
        markerOptions.title("Type" + " : " + results.get(i).getRealEstateModelList().getType() + ":" + i);
        markerOptions.snippet(results.get(i).getRealEstateModelList().getAddress());
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

      /*  if (i == results.size() - 1) {
            //Place current location marker
            latLng = new LatLng(mLocation.getLatitude(), mLocation.getLongitude());
            //MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title("current location");
            // markerOptions.snippet()
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        }*/
        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, mainUtils.ZOOM_LEVEL));
    }

    private List<RealEstateModel> restoreAllRealEstateModels(String s) {
        return new Gson().fromJson(s, new TypeToken<List<RealEstateModel>>() {
        }.getType());
    }

    private List<EstateModelLocation> getRealEstatesAroundMyPosition(List<RealEstateModel> realEstateModelList) throws IOException {
        List<EstateModelLocation> retValue = new ArrayList<>();

        for (int i = 0; i < realEstateModelList.size(); i++) {
            double[] locationResult = getLocationFromAddress(getContext(), realEstateModelList.get(i).getAddress());
            assert locationResult != null;
            double distance = mainUtils.getDistanceInMeters(this.latitude, this.longitude, locationResult[0], locationResult[1]);
            if (distance <= mainUtils.PROXIMITY_RADIUS) {
                EstateModelLocation temp = new EstateModelLocation(realEstateModelList.get(i), locationResult[0], locationResult[1]);
                retValue.add(temp);
            }
        }
        return retValue;
    }

    private void buildRetrofitAndGetResponse(final Location mLocation) {

        mMap.clear();
        // This loop will go through all the results and add marker on each location.
        for (int i = 0; i < AllRealEstatesAndLocation.size(); i++) {

            preparePlaceAndMarker(this.AllRealEstatesAndLocation, mLocation, i);
            m = mMap.addMarker(markerOptions);
            m.setTag(i);
            placeRealEstateToMap();
        }

    }
}
