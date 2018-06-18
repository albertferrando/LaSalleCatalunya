package cat.albertaleixbernat.lasallecatalunya.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Response;

import java.util.ArrayList;
import java.util.List;

import cat.albertaleixbernat.lasallecatalunya.Network.CallBack;
import cat.albertaleixbernat.lasallecatalunya.Network.NetworkManager;
import cat.albertaleixbernat.lasallecatalunya.R;
import cat.albertaleixbernat.lasallecatalunya.adapters.ListAdapter;
import cat.albertaleixbernat.lasallecatalunya.model.School;

public class CentresActivity extends AppCompatActivity {
    ListView list;
    List<School> schools;
    ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_centres);
        adapter = new ListAdapter(schools,this);
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
                adapter.updateData(response);
            }
        };
        NetworkManager nm = new NetworkManager();
        nm.getSchools(callBack);
    }
}