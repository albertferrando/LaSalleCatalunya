package cat.albertaleixbernat.lasallecatalunya.model;

public class User {
    private String nomUsuari;
    private String nom;
    private String cognom;
    private String correu;
    private String password;

    public String getNomUsuari() {
        return nomUsuari;
    }

    public String getCorreu() {
        return correu;
    }

    public String getPassword() {
        return password;
    }

    public void setNomUsuari(String nomUsuari) {
        this.nomUsuari = nomUsuari;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setCognom(String cognom) {
        this.cognom = cognom;
    }

    public void setCorreu(String correu) {
        this.correu = correu;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
