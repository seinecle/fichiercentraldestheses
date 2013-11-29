/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fichiercentraltheses;

import Model.Academic;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
public class TheseAPIXMLResponseParser extends DefaultHandler {

    private boolean newPersonne;
    private StringBuilder personneBuilder;
    private Academic academic;

    private Set<Academic> academics;

    private InputSource is;
    private final String string;

    private boolean newAuteur;
    private StringBuilder auteurBuilder;
    private boolean newArrayDirecteurs;
    private boolean newDirecteur;
    private boolean newAuteurPpn;
    private StringBuilder auteurPpnBuilder;
    private boolean newDirecteurPpn;
    private StringBuilder directeurPpnBuilder;
    private StringBuilder directeurBuilder;
    private List<String> directors;
    private List<String> directorsPpn;
    private boolean newArrayDirecteurPpn;
    private String directeur;
    private String auteur;
    private String auteurPpn;
    private String directeurPpn;

    public TheseAPIXMLResponseParser(String string) {
        this.string = string;
    }

    public Set<Academic> parse() throws IOException {
        try {

            academics = new HashSet();
            is = new InputSource(new StringReader(string));
            //get a factory
            SAXParserFactory spf = SAXParserFactory.newInstance();

            //get a new instance of parser
            SAXParser sp = spf.newSAXParser();

            //parse the file and also register this class for call backs
            sp.parse(is, this);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(TheseAPIXMLResponseParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(TheseAPIXMLResponseParser.class.getName()).log(Level.SEVERE, null, ex);
        }

        return academics;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        if (qName.matches("doc")) {
            academic = new Academic();
            TheseXMLCaller.index++;
        }

        if (qName.matches("str") && attributes.getValue("name") != null) {
            if (attributes.getValue("name").equals("auteur")) {
                newAuteur = true;
                auteurBuilder = new StringBuilder();
            }
        }

        if (qName.matches("arr") && attributes.getValue("name") != null) {
            if (attributes.getValue("name").equals("directeurThese")) {
                newArrayDirecteurs = true;
                directors = new ArrayList();
            }
        }

        if (newArrayDirecteurs && qName.matches("str")) {
            newDirecteur = true;
            directeurBuilder = new StringBuilder();
        }

        if (newArrayDirecteurPpn && qName.matches("str")) {
            newDirecteurPpn = true;
            directeurPpnBuilder = new StringBuilder();
        }

        if (qName.matches("str") && attributes.getValue("name") != null) {
            if (attributes.getValue("name").equals("auteurPpn")) {
                newAuteurPpn = true;
                auteurPpnBuilder = new StringBuilder();
            }
        }

        if (qName.matches("arr") && attributes.getValue("name") != null) {
            if (attributes.getValue("name").equals("directeurThesePpn")) {
                newArrayDirecteurPpn = true;
                directorsPpn = new ArrayList();
            }
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {

        if (newAuteur) {
            auteurBuilder.append(ch, start, length);
        }

        if (newDirecteur) {
            directeurBuilder.append(ch, start, length);
        }

        if (newAuteurPpn) {
            auteurPpnBuilder.append(ch, start, length);
        }

        if (newDirecteurPpn) {
            directeurPpnBuilder.append(ch, start, length);
        }

    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {

        if (qName.equalsIgnoreCase("str") & newDirecteur) {
            newDirecteur = false;
            directeur = directeurBuilder.toString();
            directors.add(directeur);
        }

        if (qName.equalsIgnoreCase("str") & newDirecteurPpn) {
            newDirecteurPpn = false;
            directeurPpn = directeurPpnBuilder.toString();
            directorsPpn.add(directeurPpn);
        }

        if (qName.equalsIgnoreCase("arr") & newArrayDirecteurPpn) {
            newArrayDirecteurPpn = false;
            List<Academic> currDirectors = new ArrayList();
            for (String director : directors) {
                Academic academicD = new Academic();
                academicD.setFullname(director);
                currDirectors.add(academicD);
            }

            for (String directorPpn : directorsPpn) {
                currDirectors.get(directorsPpn.indexOf(directorPpn)).setPpn(directorPpn);
            }

            for (Academic director : currDirectors) {
//                if (director.getPpn() != null && !director.getPpn().isEmpty()) {
                    academics.add(director);
//                    System.out.println("director added: " + director.getFullname());
//                    System.out.println("ppn: " + director.getPpn());

//                }
            }

        }

        if (qName.equalsIgnoreCase("arr") & newArrayDirecteurs) {
            newArrayDirecteurs = false;
        }

        if (qName.equalsIgnoreCase("str") & newAuteur) {
            newAuteur = false;
            auteur = auteurBuilder.toString();
        }

        if (qName.equalsIgnoreCase("str") & newAuteurPpn) {
            newAuteurPpn = false;
            auteurPpn = auteurPpnBuilder.toString();
//            if (!auteurPpn.isEmpty()) {
                academic = new Academic();
                academic.setFullname(auteur);
                academic.setPpn(auteurPpn);
                academics.add(academic);
//                System.out.println("academic added: " + academic.getFullname());
//                System.out.println("ppn: " + academic.getPpn());
//                System.out.println();

//            }
        }

    }

}
