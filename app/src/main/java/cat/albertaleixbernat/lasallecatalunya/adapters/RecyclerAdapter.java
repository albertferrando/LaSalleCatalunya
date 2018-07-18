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

        private TextView name, address, inf, pri, eso, bat, fp, uni;
        private ImageView img;
        private RelativeLayout bView;
        private LinearLayout fView;
        private School school;

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

        public TextView getName() {
            return name;
        }

        public void setName(TextView name) {
            this.name = name;
        }

        public TextView getAddress() {
            return address;
        }

        public void setAddress(TextView address) {
            this.address = address;
        }

        public TextView getInf() {
            return inf;
        }

        public void setInf(TextView inf) {
            this.inf = inf;
        }

        public TextView getPri() {
            return pri;
        }

        public void setPri(TextView pri) {
            this.pri = pri;
        }

        public TextView getEso() {
            return eso;
        }

        public void setEso(TextView eso) {
            this.eso = eso;
        }

        public TextView getBat() {
            return bat;
        }

        public void setBat(TextView bat) {
            this.bat = bat;
        }

        public TextView getFp() {
            return fp;
        }

        public void setFp(TextView fp) {
            this.fp = fp;
        }

        public TextView getUni() {
            return uni;
        }

        public void setUni(TextView uni) {
            this.uni = uni;
        }

        public ImageView getImg() {
            return img;
        }

        public void setImg(ImageView img) {
            this.img = img;
        }

        public RelativeLayout getbView() {
            return bView;
        }

        public void setbView(RelativeLayout bView) {
            this.bView = bView;
        }

        public LinearLayout getfView() {
            return fView;
        }

        public void setfView(LinearLayout fView) {
            this.fView = fView;
        }

        public School getSchool() {
            return school;
        }

        public void setSchool(School school) {
            this.school = school;
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
        h.setIsRecyclable(false);
        h.getName().setText(s.getSchoolName());
        h.getAddress().setText(s.getSchoolAddress());
        h.getImg().setBackgroundColor(context.getColor(R.color.red));
        h.getImg().setImageDrawable(ContextCompat.getDrawable(context, s.getFoto()));
        h.setSchool(s);

        if(s.getIsInfantil()) {
            h.getInf().setVisibility(View.VISIBLE);
        }
        if(s.getIsPrimaria()) {
            h.getPri().setVisibility(View.VISIBLE);
        }
        if(s.getIsEso()) {
            h.getEso().setVisibility(View.VISIBLE);
        }
        if(s.getIsBatxillerat()) {
            h.getBat().setVisibility(View.VISIBLE);
        }
        if(s.getIsFP()) {
            h.getFp().setVisibility(View.VISIBLE);
        }
        if(s.getIsUniversitat()) {
            h.getUni().setVisibility(View.VISIBLE);
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
