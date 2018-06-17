package cat.albertaleixbernat.lasallecatalunya.Utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import java.util.List;

import cat.albertaleixbernat.lasallecatalunya.model.School;

/**
 * Created by AleixDiaz on 17/06/2018.
 */

public class JSONDecoder {

    public JSONDecoder() {
    }

    public List<School> parseSchools (JsonArray schools) {
        Gson gson = new Gson();
        List<School> schoolsArray =  gson.fromJson(schools, new TypeToken<List<School>>(){}.getType());
        return schoolsArray;
    }

}
