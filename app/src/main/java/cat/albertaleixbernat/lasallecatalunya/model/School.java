package cat.albertaleixbernat.lasallecatalunya.model;

import java.io.Serializable;
import java.util.Comparator;

public class School implements Serializable {

    private String id;
    private String schoolName;
    private String schoolAddress;
    private Boolean isInfantil;
    private Boolean isPrimaria;
    private Boolean isBatxillerat;
    private Boolean isFP;
    private Boolean isUniversitat;
    private String description;

    public School(String id) {
        this.id = id;
    }

    public School(String id, String schoolName, String schoolAddress, String isInfantil,
                  String isPrimaria, String isBatxillerat, String isFP, String isUniversitat,
                  String description) {
        this.id = id;
        this.schoolName = schoolName;
        this.schoolAddress = schoolAddress;
        this.isInfantil = isInfantil.equals("1");
        this.isPrimaria = isPrimaria.equals("1");
        this.isBatxillerat = isBatxillerat.equals("1");
        this.isFP = isFP.equals("1");
        this.isUniversitat = isUniversitat.equals("1");
        this.description = description;
    }

    public String getId() {
        return id;
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
        return isInfantil;
    }

    public void setIsInfantil(Boolean isInfantil) {
        this.isInfantil = isInfantil;
    }

    public Boolean getIsPrimaria() {
        return isPrimaria;
    }

    public void setIsPrimaria(Boolean isPrimaria) {
        this.isPrimaria = isPrimaria;
    }

    public Boolean getIsBatxillerat() {
        return isBatxillerat;
    }

    public void setIsBatxillerat(Boolean isBatxillerat) {
        this.isBatxillerat = isBatxillerat;
    }

    public Boolean getIsFP() {
        return isFP;
    }

    public void setIsFP(Boolean isFP) {
        this.isFP = isFP;
    }

    public Boolean getIsUniversitat() {
        return isUniversitat;
    }

    public void setIsUniversitat(Boolean isUniversitat) {
        this.isUniversitat = isUniversitat;
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
