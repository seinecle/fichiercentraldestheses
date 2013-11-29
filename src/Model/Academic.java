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
public class Academic extends Quidam {

    @Id
    private ObjectId id;
    @Indexed(unique = true)
    private String ppn;

    public Academic() {
    }

    public Academic(String forename, String surname) {
        super(forename.trim(), surname.trim());
    }

    public Academic(String forename, String surname, UUID uuid) {
        super(forename.trim(), surname.trim(), uuid);
    }

    public Academic(String fullname, UUID uuid) {
        super(fullname.trim(), uuid);
    }

    public Academic(String fullname, boolean isFullName) {
        super(fullname.trim(), isFullName);
    }

    public Academic(String fullnameWithComma) {
        super(fullnameWithComma.trim());
    }

    public Academic(String fullname, Affiliation affiliation, UUID uuid) {
        super(fullname, affiliation, uuid);
    }

    public String getPpn() {
        return ppn;
    }

    public void setPpn(String ppn) {
        this.ppn = ppn;
    }


}
