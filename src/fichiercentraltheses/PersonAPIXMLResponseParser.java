/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fichiercentraltheses;

import Model.Academic;
import Model.Doctor;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/*
 Copyright 2008-2013 Clement Levallois
 Authors : Clement Levallois <clementlevallois@gmail.com>
 Website : http://www.clementlevallois.net


 DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.

 Copyright 2013 Clement Levallois. All rights reserved.

 The contents of this file are subject to the terms of either the GNU
 General Public License Version 3 only ("GPL") or the Common
 Development and Distribution License("CDDL") (collectively, the
 "License"). You may not use this file except in compliance with the
 License. You can obtain a copy of the License at
 http://gephi.org/about/legal/license-notice/
 or /cddl-1.0.txt and /gpl-3.0.txt. See the License for the
 specific language governing permissions and limitations under the
 License.  When distributing the software, include this License Header
 Notice in each file and include the License files at
 /cddl-1.0.txt and /gpl-3.0.txt. If applicable, add the following below the
 License Header, with the fields enclosed by brackets [] replaced by
 your own identifying information:
 "Portions Copyrighted [year] [name of copyright owner]"

 If you wish your version of this file to be governed by only the CDDL
 or only the GPL Version 3, indicate your decision by adding
 "[Contributor] elects to include this software in this distribution
 under the [CDDL or GPL Version 3] license." If you do not indicate a
 single choice of license, a recipient has the option to distribute
 your version of this file under either the CDDL, the GPL Version 3 or
 to extend the choice of license to its licensees as provided above.
 However, if you add GPL Version 3 code and therefore, elected the GPL
 Version 3 license, then the option applies only if the new code is
 made subject to such option by the copyright holder.

 Contributor(s): Clement Levallois

 */
public class PersonAPIXMLResponseParser extends DefaultHandler {

    private boolean newPersonne;
    private boolean newTitle;
    private StringBuilder titleBuilder;
    private StringBuilder personneBuilder;
    private Doctor author;
    private String theseAuthor;
    private String theseDirector;

    private Set<Doctor> authors;

    private InputSource is;
    private final String string;

    private boolean newNumTheseAutheur;
    private StringBuilder numTheseBuilder;
    private boolean newNumTheseDirecteur;
    private String personne;
    private boolean newActif;
    private StringBuilder actifBuilder;
    private String actif;
    private boolean newPpn;
    private StringBuilder ppnBuilder;
    private String ppn;

    public PersonAPIXMLResponseParser(String string) {
        this.string = string;
    }

    public Set<Doctor> parse() throws IOException {
        try {

            authors = new HashSet();
            is = new InputSource(new StringReader(string));
            //get a factory
            SAXParserFactory spf = SAXParserFactory.newInstance();

            //get a new instance of parser
            SAXParser sp = spf.newSAXParser();

            //parse the file and also register this class for call backs
            sp.parse(is, this);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(PersonAPIXMLResponseParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(PersonAPIXMLResponseParser.class.getName()).log(Level.SEVERE, null, ex);
        }

        return authors;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        System.out.println("qname: " + qName);

        if (qName.matches("doc")) {
            author = new Doctor();
        }

        if (qName.matches("str") && attributes.getValue("name") != null) {
            if (attributes.getValue("name").equals("personne")) {
                newPersonne = true;
                personneBuilder = new StringBuilder();
            }
        }

        if (qName.matches("str") && attributes.getValue("name") != null) {
            if (attributes.getValue("name").equals("actif")) {
                newActif = true;
                actifBuilder = new StringBuilder();
            }
        }

        if (qName.matches("str") && attributes.getValue("name") != null) {
            if (attributes.getValue("name").equals("ppn")) {
                newPpn = true;
                ppnBuilder = new StringBuilder();
            }
        }

        if (qName.matches("arr") && attributes.getValue("name") != null) {
            if (attributes.getValue("name").equals("numThesesEnTantQueAuteur")) {
                newNumTheseAutheur = true;
                numTheseBuilder = new StringBuilder();
            }
        }

        if (qName.matches("arr") && attributes.getValue("name") != null) {
            if (attributes.getValue("name").equals("numThesesEnTantQueDirecteur")) {
                newNumTheseDirecteur = true;
                numTheseBuilder = new StringBuilder();
            }
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {

        if (newTitle) {
            titleBuilder.append(ch, start, length);
        }

        if (newActif) {
            actifBuilder.append(ch, start, length);
        }

        if (newPpn) {
            actifBuilder.append(ch, start, length);
        }

        if (newPersonne) {
            personneBuilder.append(ch, start, length);
        }

        if (newNumTheseDirecteur || newNumTheseAutheur) {
            numTheseBuilder.append(ch, start, length);
        }

    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {

        if (qName.equalsIgnoreCase("doc")) {
            authors.add(author);
            System.out.println("author added: " + author.getFullname());
            newPersonne = false;
        }

        if (qName.equalsIgnoreCase("str") & newNumTheseAutheur) {
            newNumTheseAutheur = false;
            theseAuthor = numTheseBuilder.toString();
            numTheseBuilder = null;
            author.addTheseAsAuthor(theseAuthor);
        }

        if (qName.equalsIgnoreCase("str") & newActif) {
            newActif = false;
            actif = actifBuilder.toString();
            numTheseBuilder = null;
            author.setActif(actif);
        }

        if (qName.equalsIgnoreCase("str") & newPpn) {
            newPpn = false;
            ppn = ppnBuilder.toString();
            numTheseBuilder = null;
            author.setPpn(ppn);
        }

        if (qName.equalsIgnoreCase("str") & newNumTheseDirecteur) {
            newNumTheseDirecteur = false;
            theseDirector = numTheseBuilder.toString();
            numTheseBuilder = null;
//            author.addTheseAsDirector(theseDirector);
        }

        if (qName.equalsIgnoreCase("str") & newPersonne) {
            newPersonne = false;
            personne = personneBuilder.toString();
            author.setFullname(personne);
            personneBuilder = null;
        }

    }

}
