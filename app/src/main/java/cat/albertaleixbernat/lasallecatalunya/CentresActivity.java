package cat.albertaleixbernat.lasallecatalunya;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

import cat.albertaleixbernat.lasallecatalunya.Network.NetworkResponse;

public class CentresActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_centres);
    }
}
