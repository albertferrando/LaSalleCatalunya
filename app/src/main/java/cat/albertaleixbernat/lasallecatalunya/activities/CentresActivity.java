package cat.albertaleixbernat.lasallecatalunya.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Response;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_centres);

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
            for(School s: schools) {
                s.setFoto(DataManager.getInstance().getPhoto());
            }
            DataManager.getInstance().setSchools(schools);
            initializeTabs();
        }
    };

    private void initializeTabs() {
        TabLayout tabLayout = findViewById(R.id.tabbar_center);
        ViewPager viewPager = findViewById(R.id.viewpager_center);

        String[] tabTitles = getResources().getStringArray(R.array.type_values);

        ArrayList<TabAdapter.TabEntry> entries = new ArrayList<>();
        entries.add(new TabAdapter.TabEntry(new SchoolListFragment(), tabTitles[0]));
        entries.add(new TabAdapter.TabEntry(new SchoolListFragment(), tabTitles[1]));
        entries.add(new TabAdapter.TabEntry(new SchoolListFragment(), tabTitles[2]));

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
//        switch (item.getItemId()) {
//            case R.id.:
//                Intent intent = new Intent(this, LogInActivity.class);
//                startActivity(intent);
//                break;
//        }
        return false;
    }
}