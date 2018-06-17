package cat.albertaleixbernat.lasallecatalunya.Network;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import cat.albertaleixbernat.lasallecatalunya.Utils.JSONDecoder;
import cat.albertaleixbernat.lasallecatalunya.model.School;

/**
 * Created by AleixDiaz on 17/06/2018.
 */

public class NetworkManager {

    private final static String baseURL = "https://testapi-pprog2.azurewebsites.net/api/schools.php";
    private final static String getSchools = "?method=getSchools";
    private final static String deleteSchools = "?method=deleteSchool";
    private final static String schoolID = "&schoolId=";

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

    public void getSchools (final CallBack<List<School>> callBack) {

        String url = createURL(new String[]{getSchools});

        JsonRequest getSchools = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JsonObject jsonObject = new JsonParser().parse(response.toString()).getAsJsonObject();
                List<School> schools = new JSONDecoder().parseSchools(jsonObject.getAsJsonArray("msg"));
                callBack.onResponse(schools);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBack.onResponse(null);
            }
        });

        AppController.getInstance().addToRequestQueue(getSchools);
    }

    public void deleteSchools (final CallBack<String> callBack, School school) {
        String url = createURL(new String[]{deleteSchools,schoolID, school.getId()});

        JsonObjectRequest deleteRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                NetworkResponse networkResponse = new NetworkResponse(response);
                if (networkResponse.getRes() == 1) {
                    callBack.onResponse(null);
                } else {
                    callBack.onResponse("Error al esborrar escola");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBack.onResponse("Error al esborrar escola");
            }
        });

        AppController.getInstance().addToRequestQueue(deleteRequest);
    }

    private String createURL (String [] parameters) {
        StringBuilder stringBuilder = new StringBuilder(baseURL);
        for (String parameter:parameters) {
            stringBuilder.append(parameter);
        }
        return stringBuilder.toString();
    }

}
