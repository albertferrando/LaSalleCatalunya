package cat.albertaleixbernat.lasallecatalunya.Network;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.security.auth.callback.Callback;

import cat.albertaleixbernat.lasallecatalunya.Utils.JSONDecoder;
import cat.albertaleixbernat.lasallecatalunya.model.DataManager;
import cat.albertaleixbernat.lasallecatalunya.model.School;

public class NetworkManager {

    private final static String baseURL = "https://testapi-pprog2.azurewebsites.net/api/schools.php";
    private final static String getSchools = "?method=getSchools";
    private final static String deleteSchools = "?method=deleteSchool";
    private final static String schoolID = "&schoolId=";
    private final static String addSchool = "?method=addSchool";

    public NetworkManager () {
    }

    public void addSchool (final School school, final CallBack<Boolean> callBack) {

        StringRequest schoolPost = new StringRequest(Request.Method.POST, baseURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int code = jsonObject.getInt("res");
                    callBack.onResponse(code != 1);
                } catch (JSONException e) {
                    callBack.onResponse(true);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBack.onResponse(true);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return school.encode();
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

    public void getMarkers (Context context, List<MarkerOptions> schoolMarkers,
                            List<MarkerOptions> otherMarkers) {

        for(School s : DataManager.getInstance().getAllSchools()) {
            LatLng latLng = getLocationFromAddress(s.getSchoolAddress(), context);

            if (latLng != null) {

                MarkerOptions aux = new MarkerOptions()
                        .position(latLng)
                        .title(s.getSchoolName())
                        .snippet(s.getSchoolAddress());

                if (s.getIsUniversitat()) {
                    aux.icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

                } else if (s.getIsFP()) {
                    aux.icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

                } else if (s.getIsBatxillerat()) {
                    aux.icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_RED));

                } else if (s.getIsEso()) {
                    aux.icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));

                } else if (s.getIsPrimaria()) {
                    aux.icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));

                } else {
                    aux.icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));

                }

                if (s.getIsUniversitat() || s.getIsFP() || s.getIsBatxillerat()) {
                    otherMarkers.add(aux);
                }

                if (s.getIsEso() || s.getIsPrimaria() || s.getIsInfantil()) {
                    schoolMarkers.add(aux);
                }
            }
        }
    }

    public LatLng getLocationFromAddress(String strAddress, Context context) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null || address.size() == 0) {
                return null;
            }

            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }

}
