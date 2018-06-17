package cat.albertaleixbernat.lasallecatalunya.activities;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import cat.albertaleixbernat.lasallecatalunya.R;
import cat.albertaleixbernat.lasallecatalunya.model.*;

public class LogInActivity extends AppCompatActivity {
    TextInputEditText nom_correu = findViewById(R.id.correu_nom);
    TextInputEditText contrasenya = findViewById(R.id.contrasenya);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
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
