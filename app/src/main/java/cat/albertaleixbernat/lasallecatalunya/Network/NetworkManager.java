package cat.albertaleixbernat.lasallecatalunya.Network;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Map;

/**
 * Created by AleixDiaz on 17/06/2018.
 */

public class NetworkManager {

    public NetworkManager () {
    }

    void addSchool () {

        StringRequest schoolPost = new StringRequest(Request.Method.POST, "", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {



                return super.getParams();
            }
        };

        AppController.getInstance().addToRequestQueue(schoolPost);
    }

}
