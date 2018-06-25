package cat.albertaleixbernat.lasallecatalunya.model;

import android.text.Editable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import cat.albertaleixbernat.lasallecatalunya.R;

public class DataManager {
    private static final DataManager ourInstance = new DataManager();
    private ArrayList<User> users;
    private List<School> schools;
    private int[] fotos = {R.drawable.f1, R.drawable.f2, R.drawable.f3, R.drawable.f4, R.drawable.f5};
    private int actual;

    public static DataManager getInstance() {
        return ourInstance;
    }

    private DataManager() {
        users = new ArrayList<>();
    }

    public boolean logIn(String nom_correu, String contrasenya) {
        for(User u: users) {
            if((nom_correu.equals(u.getNomUsuari()) || nom_correu.equals(u.getCorreu()))
                    && contrasenya.equals(u.getPassword())) {
                return true;
            }
        }
        return false;
    }

    public boolean existsName(String s) {
        for (User u: users) {
            if(u.getNomUsuari().equals(s)){
                return true;
            }
        }
        return false;
    }

    public boolean existsMail(String s) {
        for (User u: users) {
            if(u.getCorreu().equals(s)){
                return true;
            }
        }
        return false;
    }

    public void addUser(User u) {
        users.add(u);
    }

    public int getPhoto() {
        actual++;
        if(actual == 5) {
            actual = 0;
        }
        return fotos[actual];
    }

    public void setSchools(List<School> schools) {
        this.schools = schools;
    }

    public List<School> getAllSchools() {
        return schools;
    }

    public List<School> getSchools() {
        List<School> list = new LinkedList<>();

        for (School s :  schools) {
            if (s.getIsInfantil() || s.getIsPrimaria() || s.getIsEso()) {
                list.add(s);
            }
        }

        return list;

    }

    public List<School> getOtherSchools() {
        List<School> list = new LinkedList<>();

        for (School s :  schools) {
            if (!s.getIsInfantil() && !s.getIsPrimaria() && !s.getIsEso()) {
                list.add(s);
            }
        }

        return list;

    }

    public List<School> getLocationSchools(List<School> schools, String location) {
        List<School> list = new LinkedList<>();

        for (School s : schools) {
            if (s.getProvince().compareToIgnoreCase(location) == 0) {
                list.add(s);
            }
        }

        return list;

    }

    public void removeSchool (School school) {
        for (School s: schools) {
            if (s.getId().equals(school.getId())) {
                schools.remove(school);
                return;
            }
        }
    }

    public School getCenter(String address) {
        for (School s : schools) {
            if (s.getSchoolAddress().equals(address)) return s;
        }
        return null;
    }

}
