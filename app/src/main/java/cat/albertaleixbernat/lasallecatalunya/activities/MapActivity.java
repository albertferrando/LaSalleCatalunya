package cat.albertaleixbernat.lasallecatalunya.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.TileOverlay;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import cat.albertaleixbernat.lasallecatalunya.Network.CallBack;
import cat.albertaleixbernat.lasallecatalunya.Network.NetworkManager;
import cat.albertaleixbernat.lasallecatalunya.R;
import cat.albertaleixbernat.lasallecatalunya.model.DataManager;
import cat.albertaleixbernat.lasallecatalunya.model.School;

public class MapActivity extends AppCompatActivity implements
        OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener{
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 6337;
    private static final LatLng CATALUNYA = new LatLng(41.82046, 1.86768);

    private GoogleMap googleMap;
    private MapFragment mapFragment;
    private List<MarkerOptions> schoolMarkers;
    private List<MarkerOptions> otherMarkers;
    private School selected;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        setSupportActionBar((Toolbar) findViewById(R.id.map_toolbar));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    protected void onStart() {
        super.onStart();

        mapFragment = MapFragment.newInstance();

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.map_fragment, mapFragment).commit();

        mapFragment.getMapAsync(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_map, menu);
        return true;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        if (doWeHavePermissions()) {
            googleMap.getUiSettings().setMyLocationButtonEnabled(true);
            googleMap.setMyLocationEnabled(true);

            String address = getIntent().getStringExtra("school");
            NetworkManager nm = new NetworkManager();

            LatLng position = null;
            if (address != null) {
                position = nm.getLocationFromAddress(address, this);
            }

            if (position != null) {
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 12));
            } else {
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(CATALUNYA, 7.4f));
            }

            googleMap.setOnMarkerClickListener(this);

            schoolMarkers = new LinkedList<>();
            otherMarkers = new LinkedList<>();
            setSpinner();

            new AsyncRequest(this).execute();

        }

    }

    private void setSpinner() {
        final Spinner spinner = findViewById(R.id.type_spinner_map);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                googleMap.clear();

                if (i != 2) {
                    for (MarkerOptions m : schoolMarkers) {
                        googleMap.addMarker(m);
                    }
                }

                if (i != 1) {
                    for (MarkerOptions m : otherMarkers) {
                        googleMap.addMarker(m);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.list_button_map:
                Intent intentList = new Intent(this, LogInActivity.class);
                startActivity(intentList);

                break;

            case R.id.login_button_map:
                Intent intent = new Intent(this, LogInActivity.class);
                startActivity(intent);
                break;
        }
        return false;
    }

    private boolean doWeHavePermissions() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.ACCESS_COARSE_LOCATION)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.location_permission_title);
                builder.setMessage(R.string.location_permission_msg);
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(
                                MapActivity.this,
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                        Manifest.permission.ACCESS_COARSE_LOCATION},
                                LOCATION_PERMISSION_REQUEST_CODE);
                    }
                });
                builder.create().show();
            } else {
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION},
                        LOCATION_PERMISSION_REQUEST_CODE);
            }

            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 1 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED ||
                        grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    mapFragment.getMapAsync(this);
                }
                break;
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        TextView centerName = findViewById(R.id.center_name_map);
        TextView centerAddress = findViewById(R.id.center_address_map);
        ImageView centerImage = findViewById(R.id.center_image_map);

        DataManager dataManager = DataManager.getInstance();
        selected = dataManager.getCenter(marker.getSnippet());

        centerName.setText(marker.getTitle());
        centerAddress.setText(marker.getSnippet());

        centerImage.setImageDrawable(ContextCompat.getDrawable(this,
                dataManager.getCenter(marker.getSnippet()).getFoto()));

        FrameLayout informationFrame = findViewById(R.id.center_information_map);
        informationFrame.setVisibility(View.VISIBLE);

        return false;
    }

    public void informationClick(View view) {
        Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
        intent.putExtra("school", selected);
        startActivity(intent);
    }

    private class AsyncRequest extends AsyncTask<GoogleMap, Void, Void> {

        private Context context;
        private ProgressDialog progressDialog;

        protected AsyncRequest(Context context) {
            this.context = context;
            progressDialog = new ProgressDialog(context);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage(getString(R.string.please_wait));
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(GoogleMap... params) {
            (new NetworkManager()).getMarkers(context, schoolMarkers, otherMarkers);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

            for (MarkerOptions m : schoolMarkers) {
                googleMap.addMarker(m);
            }

            for (MarkerOptions m : otherMarkers) {
                googleMap.addMarker(m);
            }
            
        }

    }
}
