package cat.albertaleixbernat.lasallecatalunya.model;

/**
 * Created by AleixDiaz on 17/06/2018.
 */

public class School {

    private String id;
    private String schoolName;
    private String schoolAddress;
    private String isInfantil;
    private String isPrimaria;
    private String isBatxillerat;
    private String isFP;
    private String isUniversitat;
    private String description;

    public School(String id) {
        this.id = id;
    }

    public School(String id, String schoolName, String schoolAddress, String isInfantil, String isPrimaria, String isBatxillerat, String isFP, String isUniversitat, String description) {
        this.id = id;
        this.schoolName = schoolName;
        this.schoolAddress = schoolAddress;
        this.isInfantil = isInfantil;
        this.isPrimaria = isPrimaria;
        this.isBatxillerat = isBatxillerat;
        this.isFP = isFP;
        this.isUniversitat = isUniversitat;
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

    public String getIsInfantil() {
        return isInfantil;
    }

    public void setIsInfantil(String isInfantil) {
        this.isInfantil = isInfantil;
    }

    public String getIsPrimaria() {
        return isPrimaria;
    }

    public void setIsPrimaria(String isPrimaria) {
        this.isPrimaria = isPrimaria;
    }

    public String getIsBatxillerat() {
        return isBatxillerat;
    }

    public void setIsBatxillerat(String isBatxillerat) {
        this.isBatxillerat = isBatxillerat;
    }

    public String getIsFP() {
        return isFP;
    }

    public void setIsFP(String isFP) {
        this.isFP = isFP;
    }

    public String getIsUniversitat() {
        return isUniversitat;
    }

    public void setIsUniversitat(String isUniversitat) {
        this.isUniversitat = isUniversitat;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
