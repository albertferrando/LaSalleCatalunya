package cat.albertaleixbernat.lasallecatalunya.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import java.util.List;

import cat.albertaleixbernat.lasallecatalunya.Network.CallBack;
import cat.albertaleixbernat.lasallecatalunya.Network.NetworkManager;
import cat.albertaleixbernat.lasallecatalunya.R;
import cat.albertaleixbernat.lasallecatalunya.activities.DetailsActivity;
import cat.albertaleixbernat.lasallecatalunya.adapters.RecyclerAdapter;
import cat.albertaleixbernat.lasallecatalunya.adapters.RecyclerTouchListener;
import cat.albertaleixbernat.lasallecatalunya.model.DataManager;
import cat.albertaleixbernat.lasallecatalunya.model.School;

/**
 * Created by Berni on 20/6/2018.
 */

public class SchoolListFragment extends Fragment {
    private RecyclerView list;
    private List<School> schools;
    private RecyclerAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_list, container, false);

        final ProgressDialog progress = new ProgressDialog(getContext());
        progress.setMessage(getString(R.string.please_wait));

        final NetworkManager networkManager = new NetworkManager();

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
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());

//        if (savedInstanceState != null) {
//            int position = savedInstanceState.getInt("Spinner");
//            adapter = new RecyclerAdapter(dataManager.getLocationSchools(schools,
//                    School.PROVINCES[position]),getActivity());
//        } else {
            adapter = new RecyclerAdapter(dataManager.getLocationSchools(schools,
                    School.PROVINCES[((Spinner) getActivity().findViewById(R.id.location_spinner_centres))
                            .getSelectedItemPosition()]), getActivity());
//        }
        list.setAdapter(adapter);
        list.setLayoutManager(mLayoutManager);

        list.addOnItemTouchListener(new RecyclerTouchListener(getContext(), list, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(getContext(), DetailsActivity.class);
                DataManager dataManager = DataManager.getInstance();
                Spinner spinner = getActivity().findViewById(R.id.location_spinner_centres);
                intent.putExtra("school", dataManager.getLocationSchools(schools,
                        School.PROVINCES[spinner.getSelectedItemPosition()]).get(position));
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {}
        }));

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                RecyclerAdapter.MyViewHolder myViewHolder = (RecyclerAdapter.MyViewHolder) viewHolder;
                progress.show();
                networkManager.deleteSchools(new CallBack<String>() {
                    @Override
                    public void onResponse(String s) {
                        if (s == null) {
                            progress.dismiss();

                        } else {
                            progress.dismiss();

                        }
                    }
                }, schools.get(myViewHolder.i));
                schools.remove(myViewHolder.i);
                DataManager.getInstance().setSchools(schools);
                adapter.removeItem(viewHolder.getAdapterPosition());
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY,actionState,isCurrentlyActive);
                if (viewHolder != null) {
                    final View foregroundView = ((RecyclerAdapter.MyViewHolder) viewHolder).fView;
                    getDefaultUIUtil().onDraw(c, recyclerView, foregroundView, dX, dY, actionState, isCurrentlyActive);
                }
            }

            @Override
            public int convertToAbsoluteDirection(int flags, int layoutDirection) {
                return super.convertToAbsoluteDirection(flags, layoutDirection);
            }

            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                final View foregroundView = ((RecyclerAdapter.MyViewHolder) viewHolder).fView;
                getDefaultUIUtil().clearView(foregroundView);
            }

            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                if (viewHolder != null) {
                    final View foregroundView = ((RecyclerAdapter.MyViewHolder) viewHolder).fView;
                    getDefaultUIUtil().onSelected(foregroundView);
                }
            }

            @Override
            public void onChildDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                final View foregroundView = ((RecyclerAdapter.MyViewHolder) viewHolder).fView;
                getDefaultUIUtil().onDraw(c, recyclerView, foregroundView, dX, dY, actionState,isCurrentlyActive);
            }
        });

        list.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        list.setItemAnimator(new DefaultItemAnimator());

        itemTouchHelper.attachToRecyclerView(list);

        return view;
    }

    public void updateList(int i) {
        if (adapter != null && schools != null){
            DataManager dataManager = DataManager.getInstance();
            adapter.updateData(dataManager.getLocationSchools(schools, School.PROVINCES[i]));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        int position = ((Spinner) getActivity().findViewById(R.id.location_spinner_centres))
                .getSelectedItemPosition();
        outState.putInt("Spinner", position);
    }
}
