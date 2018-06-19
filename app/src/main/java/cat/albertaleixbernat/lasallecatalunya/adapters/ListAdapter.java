package cat.albertaleixbernat.lasallecatalunya.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

import cat.albertaleixbernat.lasallecatalunya.R;
import cat.albertaleixbernat.lasallecatalunya.model.DataManager;
import cat.albertaleixbernat.lasallecatalunya.model.School;

public class ListAdapter extends BaseAdapter {
    private List<School> list;
    private Context context;

    public ListAdapter(List<School> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        if (list == null) return 0;
        return list.size();
    }

    @Override
    public School getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            v = inflater.inflate(R.layout.simple_list_item, parent, false);
        }

        School s = getItem(position);

        if (s != null) {
            TextView name = v.findViewById(R.id.nom_centre);
            TextView address = v.findViewById(R.id.adreca_centre);
            TextView inf = v.findViewById(R .id.infantil);
            TextView pri = v.findViewById(R.id.primaria);
            TextView eso = v.findViewById(R.id.eso);
            TextView bat = v.findViewById(R.id.batxillerat);
            TextView fp = v.findViewById(R.id.fp);
            TextView uni = v.findViewById(R.id.uni);
            ImageView img = v.findViewById(R.id.img);
            img.setImageDrawable(ContextCompat.getDrawable(context, DataManager.getInstance().getPhoto()));
            s.setFoto(DataManager.getInstance().getActual());

            name.setText(s.getSchoolName());
            address.setText(s.getSchoolAddress());
            img.setBackgroundColor(context.getColor(R.color.red));

            if(s.getIsInfantil()) {
                inf.setVisibility(View.VISIBLE);
            }
            if(s.getIsPrimaria()) {
                pri.setVisibility(View.VISIBLE);
            }
            if(s.getIsEso()) {
                eso.setVisibility(View.VISIBLE);
            }
            if(s.getIsBatxillerat()) {
                bat.setVisibility(View.VISIBLE);
            }
            if(s.getIsFP()) {
                fp.setVisibility(View.VISIBLE);
            }
            if(s.getIsUniversitat()) {
                uni.setVisibility(View.VISIBLE);
            }
        }
        return v;
    }

    public void updateData(List<School> response) {
        notifyDataSetInvalidated();
        this.list = response;
        notifyDataSetChanged();
    }
}
