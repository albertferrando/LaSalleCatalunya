package cat.albertaleixbernat.lasallecatalunya.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import cat.albertaleixbernat.lasallecatalunya.R;
import cat.albertaleixbernat.lasallecatalunya.activities.DetailsActivity;
import cat.albertaleixbernat.lasallecatalunya.adapters.ListAdapter;
import cat.albertaleixbernat.lasallecatalunya.adapters.TabAdapter;
import cat.albertaleixbernat.lasallecatalunya.model.DataManager;
import cat.albertaleixbernat.lasallecatalunya.model.School;

/**
 * Created by Berni on 20/6/2018.
 */

public class SchoolListFragment extends Fragment {
    private ListView list;
    private List<School> schools;
    private ListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_list, container, false);

        list = view.findViewById(R.id.list_fragment);
        final DataManager dataManager = DataManager.getInstance();

        switch (getArguments().getInt("list")) {
            case 0:
                schools = dataManager.getAllSchools();
                break;

            case 1:
                schools = dataManager.getSchools();
                break;

            case 2:
                schools = dataManager.getOtherSchools();
                break;
        }

        adapter = new ListAdapter(dataManager.getLocationSchools(schools,
                School.PROVINCES[((Spinner)getActivity().findViewById(R.id.location_spinner_centres))
                        .getSelectedItemPosition()]),
                getActivity());
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getContext(), DetailsActivity.class);
                intent.putExtra("school", schools.get(i));
                startActivity(intent);
            }
        });

        return view;
    }

    public void updateList(int i) {
        if (adapter != null && schools != null){
            DataManager dataManager = DataManager.getInstance();
            adapter.updateData(dataManager.getLocationSchools(schools, School.PROVINCES[i]));
        }
    }

}
