package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class Quidam implements Comparable<Quidam>, Serializable {

    private String forename;
    private String surname;
    private String fullname;
    private String fullnameWithComma;
    private List<Quidam> ld;
    private String uuid;
    private Set<Affiliation> setAffiliations = new HashSet();
    private Integer birthYear;

    public Quidam() {
    }

    public Quidam(String forename, String surname) {
        this.forename = forename.trim();
        this.surname = surname.trim();
    }

    public Quidam(String forename, String surname, UUID uuid) {
        this.forename = forename.trim();
        this.surname = surname.trim();
        this.fullname = forename.trim() + " " + surname.trim();
        this.uuid = uuid.toString();
    }

    public Quidam(String fullname, UUID uuid) {
        this.fullname = fullname.trim();
        this.uuid = uuid.toString();
    }

    public Quidam(String fullname, boolean isFullName) {
        if (isFullName) {
            this.fullname = fullname.trim();
        }
    }

    public Quidam(String fullname, Affiliation affiliation, UUID uuid) {
        this.fullname = fullname.trim();
        this.uuid = uuid.toString();
        this.setAffiliations.add(affiliation);
    }

    public Quidam(String fullnameWithComma) {
        this.fullnameWithComma = fullnameWithComma.trim();
    }

    public void setForename(String forename) {
        this.forename = forename.trim();
    }

    public String getForename() {
        return forename;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname.trim();
    }

    public String getFullname() {

        String fullnameToReturn;
        if (fullname == null) {
            fullnameToReturn = forename + " " + surname;
        } else {
            fullnameToReturn = this.fullname.trim();
        }
        return fullnameToReturn;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname.trim();
    }

    public String getFullnameWithComma() {

        String fullnameWithCommaToReturn;
        if (fullnameWithComma == null) {
            fullnameWithCommaToReturn = surname + ", " + forename;
        } else {
            fullnameWithCommaToReturn = this.fullnameWithComma.trim();
        }
        if (fullnameWithCommaToReturn == null) {
            fullnameWithCommaToReturn = "no name returned by the fullNameWithComma method on Author";
        }
        return fullnameWithCommaToReturn;
    }

    public void setFullnameWithComma(String newFullnameWithComma) {
        String[] arrayTerms = newFullnameWithComma.split(",");
//        if (arrayTerms.length == 1) {
//            this.fullname = newFullnameWithComma;
//        } 
//        else {
        this.fullnameWithComma = newFullnameWithComma;
        if (!newFullnameWithComma.contains(",")) {
            System.out.println("newFullNameWithComma:" + newFullnameWithComma);
        }
        this.forename = arrayTerms[1].trim();
        this.surname = arrayTerms[0].trim();
        this.fullname = arrayTerms[1].trim() + " " + arrayTerms[0].trim();
//        }
    }

    public String getLd() {
        return ld.toArray().toString();
    }

    public void setLd(ArrayList<Quidam> ld) {
        this.ld = ld;
    }

    public Set<Affiliation> getSetAffiliations() {
        return setAffiliations;
    }

    public void setSetAffiliations(HashSet<Affiliation> setAffiliations) {
        this.setAffiliations = setAffiliations;
    }

    public String getMostRecentAffiliation() {
        Iterator<Affiliation> setAffiliationsIterator = setAffiliations.iterator();
        Affiliation currAffiliation;
        int maxYear = 0;
        String mostCurrentAffiliation = "";
        while (setAffiliationsIterator.hasNext()) {
            currAffiliation = setAffiliationsIterator.next();
            if (currAffiliation.getYear() > maxYear) {
                mostCurrentAffiliation = currAffiliation.toString();
            }
        }
        return mostCurrentAffiliation;
    }

    public Integer getBirthYear() {
        if (birthYear == null) {
            return 0;
        } else {
            return birthYear;
        }
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + (this.forename != null ? this.forename.hashCode() : 0);
        hash = 53 * hash + (this.surname != null ? this.surname.hashCode() : 0);
        hash = 53 * hash + (this.fullname != null ? this.fullname.hashCode() : 0);
        hash = 53 * hash + (this.setAffiliations != null ? this.setAffiliations.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Quidam other = (Quidam) obj;
        if ((this.forename == null) ? (other.forename != null) : !this.forename.equals(other.forename)) {
            return false;
        }
        if ((this.surname == null) ? (other.surname != null) : !this.surname.equals(other.surname)) {
            return false;
        }
        if ((this.fullname == null) ? (other.fullname != null) : !this.fullname.equals(other.fullname)) {
            return false;
        }
        if ((this.setAffiliations == null) ? (other.setAffiliations != null) : !this.setAffiliations.equals(other.setAffiliations)) {
            return false;
        }
        return true;
    }

    public String[] toArray() {
        String[] args = new String[2];
        args[0] = forename;
        args[1] = surname;
        return args;
    }

    @Override
    public int compareTo(Quidam other) {
        int result;
        if (getFullnameWithComma() == null || other.getFullnameWithComma() == null) {
            result = (getForename() + getSurname()).compareTo(other.getForename() + other.getSurname());
        } else {
            result = getFullnameWithComma().compareTo(other.getFullnameWithComma());
        }
        return result;
    }
}
