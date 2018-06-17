package cat.albertaleixbernat.lasallecatalunya.model;

import android.text.Editable;

import java.util.ArrayList;

public class DataManager {
    private static final DataManager ourInstance = new DataManager();
    private ArrayList<User> users;

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
}