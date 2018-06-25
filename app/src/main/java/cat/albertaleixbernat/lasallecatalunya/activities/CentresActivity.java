package cat.albertaleixbernat.lasallecatalunya.activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.google.android.gms.maps.MapFragment;

import java.util.ArrayList;
import java.util.List;

import cat.albertaleixbernat.lasallecatalunya.Network.CallBack;
import cat.albertaleixbernat.lasallecatalunya.Network.NetworkManager;
import cat.albertaleixbernat.lasallecatalunya.R;
import cat.albertaleixbernat.lasallecatalunya.adapters.TabAdapter;
import cat.albertaleixbernat.lasallecatalunya.fragments.SchoolListFragment;
import cat.albertaleixbernat.lasallecatalunya.model.DataManager;
import cat.albertaleixbernat.lasallecatalunya.model.School;

public class CentresActivity extends AppCompatActivity {
    private List<School> schools;
    private ProgressDialog progressDialog;
    private SchoolListFragment listAllFragment;
    private SchoolListFragment listSchoolFragment;
    private SchoolListFragment listOtherFragment;
    private Bundle savedInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_centres);

        final Spinner spinner = findViewById(R.id.location_spinner_centres);
        if (savedInstanceState != null) {
            int position = savedInstanceState.getInt("Spinner");
            spinner.setSelection(position);
            savedInstance = savedInstanceState;
        }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                for (Fragment f : getSupportFragmentManager().getFragments()) {
                    ((SchoolListFragment)f).updateList(i);
                }

//                if (listAllFragment != null) listAllFragment.updateList(i);
//                if (listSchoolFragment != null) listSchoolFragment.updateList(i);
//                if (listOtherFragment != null) listOtherFragment.updateList(i);
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

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        listAllFragment.fragmentChange(0);
                        break;
                    case 1:listSchoolFragment.fragmentChange(1);
                        break;
                    case 2:
                        listOtherFragment.fragmentChange(2);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        String[] tabTitles = getResources().getStringArray(R.array.type_values);

        ArrayList<TabAdapter.TabEntry> entries = new ArrayList<>();

        if (savedInstance == null || savedInstance.getInt("all") == 0) {
            listAllFragment = new SchoolListFragment();
            Bundle bundleAll = new Bundle();
            bundleAll.putInt("list", 0);
            listAllFragment.setArguments(bundleAll);
        } else {
            listAllFragment = (SchoolListFragment) getSupportFragmentManager()
                    .findFragmentById(savedInstance.getInt("all"));
        }
        entries.add(new TabAdapter.TabEntry(listAllFragment, tabTitles[0]));

        if (savedInstance == null) {
            listSchoolFragment = new SchoolListFragment();
            Bundle bundleSc = new Bundle();
            bundleSc.putInt("list", 1);
            listSchoolFragment.setArguments(bundleSc);
        } else {
            listSchoolFragment = (SchoolListFragment) getSupportFragmentManager()
                    .findFragmentById(savedInstance.getInt("school"));
        }
        entries.add(new TabAdapter.TabEntry(listSchoolFragment, tabTitles[1]));

        if (savedInstance == null || savedInstance.getInt("other") == 0) {
            listOtherFragment = new SchoolListFragment();
            Bundle bundleOther = new Bundle();
            bundleOther.putInt("list", 2);
            listOtherFragment.setArguments(bundleOther);
        } else {
            listOtherFragment = (SchoolListFragment) getSupportFragmentManager()
                    .findFragmentById(savedInstance.getInt("other"));
        }
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        int position = ((Spinner) findViewById(R.id.location_spinner_centres))
                .getSelectedItemPosition();
        outState.putInt("Spinner", position);

        Log.d("HEY", String.valueOf(listOtherFragment.getId()));

        outState.putInt("all", listAllFragment.getId());
        outState.putInt("school", listSchoolFragment.getId());
        outState.putInt("other", listOtherFragment.getId());
    }
}