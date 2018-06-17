package cat.albertaleixbernat.lasallecatalunya.activities;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cat.albertaleixbernat.lasallecatalunya.R;

public class RegisterActivity extends AppCompatActivity {
    TextInputEditText username;
    TextInputEditText firstname;
    TextInputEditText lastname;
    TextInputEditText password;
    TextInputEditText confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }
}
