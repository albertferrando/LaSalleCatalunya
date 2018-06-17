package cat.albertaleixbernat.lasallecatalunya.activities;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.List;

import cat.albertaleixbernat.lasallecatalunya.Network.CallBack;
import cat.albertaleixbernat.lasallecatalunya.Network.NetworkManager;
import cat.albertaleixbernat.lasallecatalunya.R;
import cat.albertaleixbernat.lasallecatalunya.model.*;

public class LogInActivity extends AppCompatActivity {
    private TextInputEditText nom_correu;
    private TextInputEditText contrasenya;
    private CallBack<String> callBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        nom_correu = findViewById(R.id.correu_nom);
        contrasenya = findViewById(R.id.contrasenya);

        NetworkManager nm = new NetworkManager();

        callBack = new CallBack<String>() {
            @Override
            public void onResponse(String schools) {

            }
        };

        nm.deleteSchools(callBack, new School("296"));

    }

    public void onRegisterButtonClick(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void onLogInButtonClick(View view) {
        if(DataManager.getInstance().logIn(nom_correu.getText().toString(), contrasenya.getText().toString())) {

        } else {
            //Generar error
        }
    }
}
