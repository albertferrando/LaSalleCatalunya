package cat.albertaleixbernat.lasallecatalunya.activities;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import cat.albertaleixbernat.lasallecatalunya.R;
import cat.albertaleixbernat.lasallecatalunya.model.DataManager;
import cat.albertaleixbernat.lasallecatalunya.model.User;

public class RegisterActivity extends AppCompatActivity {
    TextInputEditText username;
    TextInputEditText firstname;
    TextInputEditText lastname;
    TextInputEditText mail;
    TextInputEditText password;
    TextInputEditText confirm;
    TextView error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        username = findViewById(R.id.username);
        firstname = findViewById(R.id.firstname);
        lastname = findViewById(R.id.lastname);
        mail = findViewById(R.id.mail);
        password = findViewById(R.id.password);
        confirm = findViewById(R.id.confirm);
        error = findViewById(R.id.error);
        setTitle(R.string.register_title);
    }

    public void onRegisterButtonClick(View view) {
        error.setVisibility(View.GONE);
        if(username.getText().toString().equals("") || firstname.getText().toString().equals("")
                || lastname.getText().toString().equals("") || password.getText().toString().equals("")
                || confirm.getText().toString().equals("")) {
            error.setText(R.string.error1);
            error.setVisibility(View.VISIBLE);
            return;
        }
        if(DataManager.getInstance().existsName(username.getText().toString())) {
            error.setText(R.string.error2);
            error.setVisibility(View.VISIBLE);
            return;
        }
        if(DataManager.getInstance().existsMail(mail.getText().toString())) {
            error.setText(R.string.error3);
            error.setVisibility(View.VISIBLE);
            return;
        }
        if(!password.getText().toString().equals(confirm.getText().toString())) {
            error.setText(R.string.error4);
            error.setVisibility(View.VISIBLE);
            return;
        }
        User u = new User();
        u.setNomUsuari(username.getText().toString());
        u.setNom(firstname.getText().toString());
        u.setCognom(lastname.getText().toString());
        u.setCorreu(mail.getText().toString());
        u.setPassword(password.getText().toString());
        DataManager.getInstance().addUser(u);
        username.setText("");
        firstname.setText("");
        lastname.setText("");
        mail.setText("");
        password.setText("");
        confirm.setText("");
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
