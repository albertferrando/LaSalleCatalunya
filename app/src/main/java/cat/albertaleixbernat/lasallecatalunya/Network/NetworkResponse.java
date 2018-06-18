package cat.albertaleixbernat.lasallecatalunya.Network;

import org.json.JSONException;
import org.json.JSONObject;

public class NetworkResponse {

    private int res;
    private String msg;

    public NetworkResponse(JSONObject object) {
        try {
            this.res = object.getInt("res");
            this.msg = object.getString("msg");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public NetworkResponse(int res, String msg) {
        this.res = res;
        this.msg = msg;
    }

    public int getRes() {
        return res;
    }

    public void setRes(int res) {
        this.res = res;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
