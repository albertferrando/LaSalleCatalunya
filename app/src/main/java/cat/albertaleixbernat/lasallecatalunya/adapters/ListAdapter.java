package cat.albertaleixbernat.lasallecatalunya.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

import cat.albertaleixbernat.lasallecatalunya.R;
import cat.albertaleixbernat.lasallecatalunya.model.School;

public class ListAdapter extends ArrayAdapter<School> {
    public ListAdapter(Context context, int layout, List<School> items) {
        super(context, layout, items);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            v = inflater.inflate(R.layout.simple_list_item, null);
        }

        School s = getItem(position);

        if (s != null) {
            TextView name = v.findViewById(R.id.nom_centre);
            TextView address = v.findViewById(R.id.adreca_centre);
            ImageView img = v.findViewById(R.id.imatge);
            TextView inf = v.findViewById(R.id.infantil);
            TextView pri = v.findViewById(R.id.primaria);
            TextView bat = v.findViewById(R.id.batxillerat);
            TextView fp = v.findViewById(R.id.fp);
            TextView uni = v.findViewById(R.id.uni);

            name.setText(s.getSchoolName());
            address.setText(s.getSchoolAddress());
            img.setBackgroundColor(getContext().getColor(R.color.red));

            if(s.getIsInfantil()) {
                inf.setVisibility(View.VISIBLE);
            }
            if(s.getIsPrimaria()) {
                pri.setVisibility(View.VISIBLE);
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


}
