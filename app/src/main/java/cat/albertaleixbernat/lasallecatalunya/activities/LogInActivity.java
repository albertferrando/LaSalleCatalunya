package cat.albertaleixbernat.lasallecatalunya.activities;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TextView;

import cat.albertaleixbernat.lasallecatalunya.R;
import cat.albertaleixbernat.lasallecatalunya.model.*;

public class LogInActivity extends AppCompatActivity {
    private TextView error;
    private TextInputEditText nom_correu;
    private TextInputEditText contrasenya;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        nom_correu = findViewById(R.id.correu_nom);
        contrasenya = findViewById(R.id.contrasenya);
        error = findViewById(R.id.error);
        getSupportActionBar().setTitle(R.string.login_title);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar, menu);
        return true;
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
            Intent intent = new Intent(this, CentresAdminActivity.class);
            startActivity(intent);
            nom_correu.setText("");
            contrasenya.setText("");
        } else {
            error.setText(R.string.error5);
            error.setVisibility(View.VISIBLE);
        }
    }
}
