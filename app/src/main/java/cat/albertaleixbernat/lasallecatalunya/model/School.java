package cat.albertaleixbernat.lasallecatalunya.model;

import java.io.Serializable;
import java.io.Serializable;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Comparator;

public class School implements Serializable {

    public static final String [] PROVINCES =  {"barcelona", "tarragona", "girona","lleida"};

    private String id;
    private String schoolName;
    private String schoolAddress;
    private String isInfantil;
    private String isPrimaria;
    private String isBatxillerat;
    private String isFP;
    private String isUniversitat;
    private String isEso;
    private String description;
    private int foto;

    public School(String id) {
        this.id = id;
    }

    public School(String id, String schoolName, String schoolAddress, String isInfantil,
                  String isPrimaria, String isEso, String isBatxillerat, String isFP,
                  String isUniversitat, String description) {
        this.id = id;
        this.isEso = isEso;
        this.schoolName = schoolName;
        this.schoolAddress = schoolAddress;
        this.isInfantil = isInfantil;
        this.isPrimaria = isPrimaria;
        this.isBatxillerat = isBatxillerat;
        this.isFP = isFP;
        this.isUniversitat = isUniversitat;
        this.description = description;
        this.foto = 0;
    }

    public Map<String, String> encode () {
        Map<String, String> params = new HashMap<>();
        params.put("method","addSchool");
        params.put("name",this.schoolName);
        params.put("address", this.schoolAddress);
        params.put("province", this.getProvince());
        params.put("type", this.getType());
        params.put("description", this.description);
        return params;
    }

    /*public String encode () {
        StringBuilder stringBuilder = new StringBuilder("{");
        stringBuilder.append(encodeVar("method", "addSchool")).append(",")
                .append(encodeVar("name", schoolName)).append(",")
                .append(encodeVar("address",schoolAddress)).append(",")
                .append(encodeVar("province", getProvince())).append(",")
                .append(encodeVar("type", getType())).append(",")
                .append(encodeVar("description", description)).append("}");
        return stringBuilder.toString();
    }

    private String encodeVar (String name, String content) {
        return "\"" + name + "\":\"" + content + "\"";
    }
*/
    public String getId() {
        return id;
    }

    private String getType() {

        String a = "0";

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(isInfantil).append(isPrimaria).append(isEso).append(isBatxillerat).append(isFP).append(isUniversitat);
        return stringBuilder.toString();
    }

    public String getProvince () {
        for (String province:PROVINCES) {
            if (schoolAddress.toLowerCase().contains(province)) {
                return province;
            }
        }
        return "NO_PROVINCE";
    }

    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getSchoolAddress() {
        return schoolAddress;
    }

    public void setSchoolAddress(String schoolAddress) {
        this.schoolAddress = schoolAddress;
    }

    public Boolean getIsInfantil() {
        return isInfantil.equals("1");
    }

    public void setIsInfantil(String isInfantil) {
        this.isInfantil = isInfantil;
    }

    public Boolean getIsPrimaria() {
        return isPrimaria.equals("1");
    }

    public void setIsPrimaria(String isPrimaria) {
        this.isPrimaria = isPrimaria;
    }

    public Boolean getIsBatxillerat() {
        return isBatxillerat.equals("1");
    }

    public void setIsBatxillerat(String isBatxillerat) {
        this.isBatxillerat = isBatxillerat;
    }

    public Boolean getIsFP() {
        return isFP.equals("1");
    }

    public void setIsFP(String isFP) {
        this.isFP = isFP;
    }

    public Boolean getIsUniversitat() {
        return isUniversitat.equals("1");
    }

    public void setIsUniversitat(String isUniversitat) {
        this.isUniversitat = isUniversitat;
    }

    public Boolean getIsEso() {
        return isEso.equals("1");
    }

    public void setIsEso(String isEso) {
        this.isEso = isEso;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static final Comparator<School> COMPARATOR_UP =
            new Comparator<School>() {
                @Override
                public int compare(School s1, School s2) {
                    return s1.getSchoolName().compareToIgnoreCase(s2.getSchoolName());
                }
            };

    public static final Comparator<School> COMPARATOR_DOWN =
            new Comparator<School>() {
                @Override
                public int compare(School s1, School s2) {
                    return s2.getSchoolName().compareToIgnoreCase(s1.getSchoolName());
                }
            };
}
