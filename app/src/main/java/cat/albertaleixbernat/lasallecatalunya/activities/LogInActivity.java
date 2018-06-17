package cat.albertaleixbernat.lasallecatalunya.activities;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import cat.albertaleixbernat.lasallecatalunya.R;
import cat.albertaleixbernat.lasallecatalunya.model.*;

public class LogInActivity extends AppCompatActivity {
    TextInputEditText nom_correu;
    TextInputEditText contrasenya;
    TextView error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        nom_correu = findViewById(R.id.correu_nom);
        contrasenya = findViewById(R.id.contrasenya);
        error = findViewById(R.id.error);
        setTitle(R.string.login_title);
    }

    public void onRegisterButtonClick(View view) {
        nom_correu.setText("");
        contrasenya.setText("");
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void onLogInButtonClick(View view) {
        error.setVisibility(View.GONE);
        if(DataManager.getInstance().logIn(nom_correu.getText().toString(), contrasenya.getText().toString())) {
            nom_correu.setText("");
            contrasenya.setText("");
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        } else {
            error.setText(R.string.error5);
            error.setVisibility(View.VISIBLE);
        }
    }
}
