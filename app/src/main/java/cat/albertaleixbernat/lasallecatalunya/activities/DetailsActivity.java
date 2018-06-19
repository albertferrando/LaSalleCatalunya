package cat.albertaleixbernat.lasallecatalunya.activities;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import cat.albertaleixbernat.lasallecatalunya.R;
import cat.albertaleixbernat.lasallecatalunya.model.School;

public class DetailsActivity extends AppCompatActivity {
    ImageView img;
    EditText descripcio;
    TextView nom;
    TextView adreca;
    TextView inf;
    TextView pri;
    TextView eso;
    TextView bat;
    TextView fp;
    TextView uni;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ImageView img = findViewById(R.id.img);
        img.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_action_name));

        descripcio = findViewById(R.id.description);
        descripcio.setKeyListener(null);
        nom = findViewById(R.id.nom_centre);
        adreca = findViewById(R.id.adreca_centre);
        inf = findViewById(R.id.infantil);
        pri = findViewById(R.id.primaria);
        eso = findViewById(R.id.eso);
        bat = findViewById(R.id.bat);
        fp = findViewById(R.id.fp);
        uni = findViewById(R.id.uni);
        School s = (School)getIntent().getSerializableExtra("school");
        nom.setText(s.getSchoolName());
        adreca.setText(s.getSchoolAddress());
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

    public void onMapButtonClicked(View view) {
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
    }
}
