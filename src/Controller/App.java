/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Academic;
import Persistence.MongoMorphia;
import com.google.code.morphia.Datastore;
import com.google.code.morphia.Key;
import com.mongodb.WriteConcern;
import fichiercentraltheses.PersonAPIXMLResponseParser;
import fichiercentraltheses.TheseAPIXMLResponseParser;
import fichiercentraltheses.PersonXMLCaller;
import fichiercentraltheses.TheseXMLCaller;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author C. Levallois
 */
public class App {

    static Datastore dsDoctors;
    static Map<String, String> res = new HashMap();
    public static Set<String> resNoId = new HashSet();

    public static void main(String[] args) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("D:/resWithoutIdentifiers.txt"));
//            MongoMorphia mm = new MongoMorphia();
//            mm.initialize();
//            dsDoctors = mm.getDsDoctor();
            TheseXMLCaller caller = new TheseXMLCaller();

            for (int year = 1965; year < 2014; year++) {
                caller.callForXMLResponse(year);
//                    dsDoctors.save(doctors);
                System.out.println("year: " + year + " saved");
            }
            for (String string : resNoId) {
                bw.write(string + "\n");
            }
            bw.close();

        } catch (URISyntaxException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
