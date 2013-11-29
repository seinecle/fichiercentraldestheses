/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.bson.types.ObjectId;

/**
 *
 * @author C. Levallois
 */
public class Doctor extends Quidam {

    @Id
    private ObjectId id;
    private List<String> thesesDirector;
    private List<String> thesesAuthor;
    private String actif;
    @Indexed(unique = true)
    private String ppn;

    public Doctor() {
    }

    public Doctor(String forename, String surname) {
        super(forename.trim(), surname.trim());
    }

    public Doctor(String forename, String surname, UUID uuid) {
        super(forename.trim(), surname.trim(), uuid);
    }

    public Doctor(String fullname, UUID uuid) {
        super(fullname.trim(), uuid);
    }

    public Doctor(String fullname, boolean isFullName) {
        super(fullname.trim(), isFullName);
    }

    public Doctor(String fullnameWithComma) {
        super(fullnameWithComma.trim());
    }

    public Doctor(String fullname, Affiliation affiliation, UUID uuid) {
        super(fullname, affiliation, uuid);
    }

    public List<String> getThesesAuthor() {
        return thesesAuthor;
    }

    public List<String> getThesesDirector() {
        return thesesDirector;
    }

    public void setThesesDirector(List<String> thesesDirector) {
        this.thesesDirector = thesesDirector;
    }

    public void addTheseAsDirector(String theseAsDirector) {
        if (thesesDirector == null) {
            thesesDirector = new ArrayList();
        }
        this.thesesDirector.add(theseAsDirector);
    }

    public void addTheseAsAuthor(String theseAsAuthor) {
        if (thesesAuthor == null) {
            thesesAuthor = new ArrayList();
        }
        this.thesesAuthor.add(theseAsAuthor);
    }

    public void setActif(String actif) {
        this.actif = actif;
    }

    public void setPpn(String ppn) {
        this.ppn = ppn;
    }

    public String getActif() {
        return actif;
    }

    public String getPpn() {
        return ppn;
    }

}
