package cat.albertaleixbernat.lasallecatalunya.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Canvas;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cat.albertaleixbernat.lasallecatalunya.Network.CallBack;
import cat.albertaleixbernat.lasallecatalunya.Network.NetworkManager;
import cat.albertaleixbernat.lasallecatalunya.R;
import cat.albertaleixbernat.lasallecatalunya.adapters.RecyclerAdapter;
import cat.albertaleixbernat.lasallecatalunya.adapters.RecyclerTouchListener;
import cat.albertaleixbernat.lasallecatalunya.model.DataManager;
import cat.albertaleixbernat.lasallecatalunya.model.School;

public class CentresAdminActivity extends AppCompatActivity {
    private RecyclerView list;
    private List<School> schools;
    private RecyclerAdapter adapter;
    private NetworkManager nm;
    private boolean isSort = false;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_centres_admin);
        schools = DataManager.getInstance().getSchools();
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        adapter = new RecyclerAdapter(schools, this);

        final ProgressDialog progress = new ProgressDialog(this);
        final NetworkManager networkManager = new NetworkManager();
        progress.setMessage(getString(R.string.please_wait));

        list = findViewById(R.id.list);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        list.setLayoutManager(mLayoutManager);
        list = findViewById(R.id.list);
        list.setAdapter(adapter);

        list.addOnItemTouchListener(new RecyclerTouchListener(this, list, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
                intent.putExtra("school", schools.get(position));
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
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {
                progress.show();
                final RecyclerAdapter.MyViewHolder myViewHolder = (RecyclerAdapter.MyViewHolder) viewHolder;
                networkManager.deleteSchools(new CallBack<String>() {
                    @Override
                    public void onResponse(String s) {
                        if (s == null) {
                            adapter.removeItem(viewHolder.getAdapterPosition());
                            DataManager.getInstance().setSchools(schools);
                        }
                        progress.dismiss();
                    }
                }, schools.get(myViewHolder.i));

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

        list.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        list.setItemAnimator(new DefaultItemAnimator());
        itemTouchHelper.attachToRecyclerView(list);

        nm = new NetworkManager();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.show();
        nm.getSchools(callBack);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.updateData(DataManager.getInstance().getAllSchools());
    }

    CallBack callBack = new CallBack<List<School>>() {
        @Override
        public void onResponse(List<School> response) {
            if(progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            schools = response;
            DataManager.getInstance().setSchools(schools);
            for(School s: schools) {
                s.setFoto(DataManager.getInstance().getPhoto());
            }
            adapter.updateData(response);
        }
    };

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
                onBackPressed();
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

    @Override
    protected void onRestart() {
        super.onRestart();
        nm = new NetworkManager();
        nm.getSchools(callBack);
    }
}
