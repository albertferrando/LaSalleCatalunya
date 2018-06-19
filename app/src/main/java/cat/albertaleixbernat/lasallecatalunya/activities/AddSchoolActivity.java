package cat.albertaleixbernat.lasallecatalunya.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;
import java.util.UUID;

import cat.albertaleixbernat.lasallecatalunya.Network.CallBack;
import cat.albertaleixbernat.lasallecatalunya.Network.NetworkManager;
import cat.albertaleixbernat.lasallecatalunya.R;
import cat.albertaleixbernat.lasallecatalunya.model.School;

public class AddSchoolActivity extends AppCompatActivity {
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_school);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setTitle(R.string.add_title);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar, menu);
        return true;
    }

    public void onAddSchoolClick(View view) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.show();
        NetworkManager networkManager = new NetworkManager();
        TextView nom = findViewById(R.id.nom_centre);
        TextView adreca = findViewById(R.id.adreca_centre);
        EditText descripcio = findViewById(R.id.descripcio);
        CheckBox inf = findViewById(R.id.infantil);
        CheckBox pri = findViewById(R.id.primaria);
        CheckBox eso = findViewById(R.id.eso);
        CheckBox bat = findViewById(R.id.bat);
        CheckBox fp = findViewById(R.id.fp);
        CheckBox uni = findViewById(R.id.uni);

        School s = new School(UUID.randomUUID().toString(), nom.getText().toString(),
                adreca.getText().toString(), inf.isChecked() ? "1" : "0", pri.isChecked() ? "1" : "0",
                eso.isChecked() ? "1" : "0", bat.isChecked() ? "1" : "0", fp.isChecked() ? "1" : "0",
                uni.isChecked() ? "1" : "0", descripcio.getText().toString());

        networkManager.addSchool(s, callBack);
        Intent intent = new Intent(this, CentresAdminActivity.class);
        startActivity(intent);
    }

    CallBack callBack = new CallBack<Boolean>() {
        @Override
        public void onResponse(Boolean response) {
            if(progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    };
}
