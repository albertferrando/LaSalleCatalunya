package cat.albertaleixbernat.lasallecatalunya.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cat.albertaleixbernat.lasallecatalunya.Network.CallBack;
import cat.albertaleixbernat.lasallecatalunya.Network.NetworkManager;
import cat.albertaleixbernat.lasallecatalunya.R;
import cat.albertaleixbernat.lasallecatalunya.adapters.ListAdapter;
import cat.albertaleixbernat.lasallecatalunya.model.School;

public class CentresAdminActivity extends AppCompatActivity {
    ListView list;
    List<School> schools;
    ListAdapter adapter;
    boolean isSort = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_centres_admin);
        schools = new ArrayList<>();
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setTitle("");
        adapter = new ListAdapter(this, R.layout.simple_list_item, schools);
        list = findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getParent(), DetailsActivity.class);
                intent.putExtra("school", schools.get(i));
                startActivity(intent);
            }
        });
        CallBack callBack = new CallBack<List<School>>() {
            @Override
            public void onResponse(List<School> response) {
                schools = response;
            }
        };
        NetworkManager nm = new NetworkManager();
        nm.getSchools(callBack);
        adapter.notifyDataSetChanged();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sort_button:
                if(!isSort) {
                    Collections.sort(schools, School.COMPARATOR_UP);
                    isSort = true;
                } else {
                    Collections.sort(schools, School.COMPARATOR_DOWN);
                    isSort = false;
                }
                adapter.notifyDataSetChanged();
                break;

            case R.id.logout_button:
                Intent intent = new Intent(this, LogInActivity.class);
                startActivity(intent);
                break;
        }
        return false;
    }

    public void onAddSchoolClick(View view) {
        Intent intent = new Intent(this, AddSchoolActivity.class);
        startActivity(intent);
    }
}