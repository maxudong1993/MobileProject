package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.project.data.model.InfoWindowData;
import com.example.project.data.model.Product;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private Gson gson;
    private SearchView mSearchView;
    private GoogleMap mMap;
    private InfoFragment iFragment;
    private ProfileFragment pFragment;
    private Bundle bundle;

    private InfoWindowData info;
    private LatLng latLng;
    private String productInfo;
    private Product product;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_info:
                    mSearchView.setVisibility(View.INVISIBLE);
                    Fragment map = getSupportFragmentManager().findFragmentById(R.id.map);
                    if (map != null) {
                        getSupportFragmentManager().beginTransaction().hide(map).commitAllowingStateLoss();
                    }
                    if (pFragment != null && pFragment.isAdded()) {
                        getSupportFragmentManager().beginTransaction().remove(pFragment).commitAllowingStateLoss();
                    }
                    if (iFragment == null) {
                        iFragment = new InfoFragment();
                    }
                    if (!iFragment.isAdded()) {
                        getSupportFragmentManager().beginTransaction().add(R.id.fl_container, iFragment).commitNow();
                    }
                    return true;
                case R.id.navigation_main:
                    mSearchView.setVisibility(View.VISIBLE);
                    Fragment map1 = getSupportFragmentManager().findFragmentById(R.id.map);
                    if (map1 != null) {
                        getSupportFragmentManager().beginTransaction().show(map1).commitAllowingStateLoss();
                    }
                    if (iFragment != null && iFragment.isAdded()) {
                        getSupportFragmentManager().beginTransaction().remove(iFragment).commitAllowingStateLoss();
                    }
                    if (pFragment != null && pFragment.isAdded()) {
                        getSupportFragmentManager().beginTransaction().remove(pFragment).commitAllowingStateLoss();
                    }
                    return true;
                case R.id.navigation_profile:
                    mSearchView.setVisibility(View.INVISIBLE);
                    Fragment map2 = getSupportFragmentManager().findFragmentById(R.id.map);
                    if (map2 != null) {
                        getSupportFragmentManager().beginTransaction().hide(map2).commitAllowingStateLoss();
                    }
                    if (iFragment != null && iFragment.isAdded()) {
                        getSupportFragmentManager().beginTransaction().remove(iFragment).commitAllowingStateLoss();
                    }
                    if (pFragment == null) {
                        pFragment = new ProfileFragment();
                    }
                    if (!pFragment.isAdded()) {
                        getSupportFragmentManager().beginTransaction().add(R.id.fl_container, pFragment).commitNow();
                    }
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        if (gson == null) {
            gson = new GsonBuilder().create();
        }

        if (getIntent().getExtras() != null){
            bundle = getIntent().getExtras();
            productInfo = bundle.getString("productInfo");
            product = gson.fromJson(productInfo, new TypeToken<Product>() {
            }.getType());
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // NavBar
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setSelectedItemId(navView.getMenu().getItem(1).getItemId());
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // Search
        mSearchView = findViewById(R.id.serachview);
        mSearchView.setOnClickListener((View view) -> {
            Intent intent = new Intent(MapsActivity.this, SearchActivity.class);
            startActivity(intent);
        });
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
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setRotateGesturesEnabled(false);
        mMap.getUiSettings().setTiltGesturesEnabled(false);

        CustomInfoWindowGoogleMap customInfoWindow = new CustomInfoWindowGoogleMap(this);
        mMap.setInfoWindowAdapter(customInfoWindow);

        if (product != null) {
            String[] location = product.getLocation().split(" ");
            latLng = new LatLng(Float.parseFloat(location[0]), Float.parseFloat(location[1]));

            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng)
                    .title(product.getName())
                    .snippet(product.getDescription())
                    .icon(BitmapDescriptorFactory.defaultMarker( BitmapDescriptorFactory.HUE_AZURE));

            info = (info == null ? new InfoWindowData() : info);
            info.setPrice("Price : $" + product.getPrice());
            info.setType("Type : " + (product.getIs_personal() == 1 ? "sold by person" : "sold by shop"));

            Marker m = mMap.addMarker(markerOptions);
            m.setTag(info);
            m.showInfoWindow();
        } else {
            latLng = new LatLng(-37.798, 144.960);
        }

        // Move the camera instantly to location with a zoom of 15.
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));

        mMap.setOnInfoWindowClickListener((Marker marker) -> {
                Intent intent = new Intent(MapsActivity.this, DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("productInfo", productInfo);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        );
    }
}
