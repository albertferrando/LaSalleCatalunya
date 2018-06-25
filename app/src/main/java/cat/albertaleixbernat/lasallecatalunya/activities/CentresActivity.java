package cat.albertaleixbernat.lasallecatalunya.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;

import com.android.volley.Response;
import com.google.android.gms.maps.MapFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cat.albertaleixbernat.lasallecatalunya.Network.CallBack;
import cat.albertaleixbernat.lasallecatalunya.Network.NetworkManager;
import cat.albertaleixbernat.lasallecatalunya.R;
import cat.albertaleixbernat.lasallecatalunya.adapters.ListAdapter;
import cat.albertaleixbernat.lasallecatalunya.adapters.TabAdapter;
import cat.albertaleixbernat.lasallecatalunya.fragments.SchoolListFragment;
import cat.albertaleixbernat.lasallecatalunya.model.DataManager;
import cat.albertaleixbernat.lasallecatalunya.model.School;

public class CentresActivity extends AppCompatActivity {
    List<School> schools;
    private ProgressDialog progressDialog;
    SchoolListFragment listAllFragment;
    SchoolListFragment listSchoolFragment;
    SchoolListFragment listOtherFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_centres);

        final Spinner spinner = findViewById(R.id.location_spinner_centres);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (listAllFragment != null) listAllFragment.updateList(i);
                if (listSchoolFragment != null) listSchoolFragment.updateList(i);
                if (listOtherFragment != null) listOtherFragment.updateList(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        schools = new ArrayList<>();

        setSupportActionBar((Toolbar) findViewById(R.id.center_toolbar));
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        NetworkManager nm = new NetworkManager();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.show();
        nm.getSchools(callBack);
    }

    CallBack callBack = new CallBack<List<School>>() {
        @Override
        public void onResponse(List<School> response) {
            if(progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            schools = response;
            if (schools != null) {
                for (School s : schools) {
                    s.setFoto(DataManager.getInstance().getPhoto());
                }
                DataManager.getInstance().setSchools(schools);
                initializeTabs();
            } else {
                openDialog();
            }
        }
    };

    private void openDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setTitle(R.string.error_center);
        dialog.show();
    }

    private void initializeTabs() {
        TabLayout tabLayout = findViewById(R.id.tabbar_center);
        ViewPager viewPager = findViewById(R.id.viewpager_center);

        String[] tabTitles = getResources().getStringArray(R.array.type_values);

        ArrayList<TabAdapter.TabEntry> entries = new ArrayList<>();

        listAllFragment = new SchoolListFragment();
        Bundle bundleAll = new Bundle();
        bundleAll.putInt("list", 0);
        listAllFragment.setArguments(bundleAll);
        entries.add(new TabAdapter.TabEntry(listAllFragment, tabTitles[0]));

        listSchoolFragment = new SchoolListFragment();
        Bundle bundleSc = new Bundle();
        bundleSc.putInt("list", 1);
        listSchoolFragment.setArguments(bundleSc);
        entries.add(new TabAdapter.TabEntry(listSchoolFragment, tabTitles[1]));

        listOtherFragment = new SchoolListFragment();
        Bundle bundleOther = new Bundle();
        bundleOther.putInt("list", 2);
        listOtherFragment.setArguments(bundleOther);
        entries.add(new TabAdapter.TabEntry(listOtherFragment, tabTitles[2]));

        TabAdapter adapter = new TabAdapter(getSupportFragmentManager(), entries);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_center, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.login_button_center:
                Intent intentLogin = new Intent(this, LogInActivity.class);
                startActivity(intentLogin);
                break;

            case R.id.map_button_center:
                Intent intentMap = new Intent(this, MapActivity.class);
                startActivity(intentMap);
                break;

        }
        return false;
    }
}