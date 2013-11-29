/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author C. Levallois
 */
public class Affiliation {

    private String organizationLevel1;
    private String organizationLevel2;
    private String organizationLevel3;
    private int year;

    public Affiliation() {
    }

    public Affiliation(String organizationLevel1) {
        this.organizationLevel1 = organizationLevel1;
    }

    public Affiliation(String organizationLevel1, String organizationLevel2) {
        this.organizationLevel1 = organizationLevel1;
        this.organizationLevel2 = organizationLevel2;
    }

    public Affiliation(String organizationLevel1, String organizationLevel2, String organizationLevel3) {
        this.organizationLevel1 = organizationLevel1;
        this.organizationLevel2 = organizationLevel2;
        this.organizationLevel3 = organizationLevel3;
    }

    public String getOrganizationLevel1() {
        return organizationLevel1;
    }

    public void setOrganizationLevel1(String organizationLevel1) {
        this.organizationLevel1 = organizationLevel1;
    }

    public String getOrganizationLevel2() {
        return organizationLevel2;
    }

    public void setOrganizationLevel2(String organizationLevel2) {
        this.organizationLevel2 = organizationLevel2;
    }

    public String getOrganizationLevel3() {
        return organizationLevel3;
    }

    public void setOrganizationLevel3(String organizationLevel3) {
        this.organizationLevel3 = organizationLevel3;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (getOrganizationLevel1() != null) {
            sb.append(getOrganizationLevel1());
        }
        if (getOrganizationLevel2() != null) {
            sb.append(", ");
            sb.append(getOrganizationLevel2());
        }
        if (getOrganizationLevel3() != null) {
            sb.append(", ");
            sb.append(getOrganizationLevel3());
        }
        sb.append(".");

        return sb.toString();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + (this.organizationLevel1 != null ? this.organizationLevel1.hashCode() : 0);
        hash = 89 * hash + (this.organizationLevel2 != null ? this.organizationLevel2.hashCode() : 0);
        hash = 89 * hash + (this.organizationLevel3 != null ? this.organizationLevel3.hashCode() : 0);
        hash = 89 * hash + this.year;
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
        final Affiliation other = (Affiliation) obj;
        if ((this.organizationLevel1 == null) ? (other.organizationLevel1 != null) : !this.organizationLevel1.equals(other.organizationLevel1)) {
            return false;
        }
        if ((this.organizationLevel2 == null) ? (other.organizationLevel2 != null) : !this.organizationLevel2.equals(other.organizationLevel2)) {
            return false;
        }
        if ((this.organizationLevel3 == null) ? (other.organizationLevel3 != null) : !this.organizationLevel3.equals(other.organizationLevel3)) {
            return false;
        }
        if (this.year != other.year) {
            return false;
        }
        return true;
    }
    
    
}
