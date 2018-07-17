package cat.albertaleixbernat.lasallecatalunya.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import cat.albertaleixbernat.lasallecatalunya.R;
import cat.albertaleixbernat.lasallecatalunya.model.School;

/**
 * Created by AleixDiaz on 23/06/2018.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView name, address, inf, pri, eso, bat, fp, uni;
        public ImageView img;
        public RelativeLayout bView;
        public LinearLayout fView;
        public School school;

        public MyViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.nom_centre);
            address = v.findViewById(R.id.adreca_centre);
            inf = v.findViewById(R .id.infantil);
            pri = v.findViewById(R.id.primaria);
            eso = v.findViewById(R.id.eso);
            bat = v.findViewById(R.id.batxillerat);
            fp = v.findViewById(R.id.fp);
            uni = v.findViewById(R.id.uni);
            img = v.findViewById(R.id.img);
            fView = v.findViewById(R.id.fView);
            bView = v.findViewById(R.id.bView);
        }
    }

    private List<School> list;
    private Context context;

    public RecyclerAdapter(List<School> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.simple_list_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder h, int position) {

        School s = list.get(position);

        h.name.setText(s.getSchoolName());
        h.address.setText(s.getSchoolAddress());
        h.img.setBackgroundColor(context.getColor(R.color.red));
        h.img.setImageDrawable(ContextCompat.getDrawable(context, s.getFoto()));
        h.school = list.get(position);

        if(s.getIsInfantil()) {
            h.inf.setVisibility(View.VISIBLE);
        }
        if(s.getIsPrimaria()) {
            h.pri.setVisibility(View.VISIBLE);
        }
        if(s.getIsEso()) {
            h.eso.setVisibility(View.VISIBLE);
        }
        if(s.getIsBatxillerat()) {
            h.bat.setVisibility(View.VISIBLE);
        }
        if(s.getIsFP()) {
            h.fp.setVisibility(View.VISIBLE);
        }
        if(s.getIsUniversitat()) {
            h.uni.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void removeItem (int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void updateData(List<School> response) {
        this.list = response;
        notifyDataSetChanged();
    }

}
