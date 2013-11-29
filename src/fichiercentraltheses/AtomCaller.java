package fichiercentraltheses;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.abdera.Abdera;
import org.apache.abdera.model.Document;
import org.apache.abdera.model.Element;
import org.apache.abdera.protocol.Response.ResponseType;
import org.apache.abdera.protocol.client.AbderaClient;
import org.apache.abdera.protocol.client.ClientResponse;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.util.URIUtil;

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
public class AtomCaller {

    public void call () throws URIException {

        Abdera abdera = new Abdera();
        AbderaClient client = new AbderaClient(abdera);
        String searchString = "http://www.theses.fr/?q=&fq=dateSoutenance:[1965-01-01T23:59:59Z%2BTO%2B2013-12-31T23:59:59Z]&checkedfacets=role=auteur&format=atom";
        searchString = URIUtil.encodeQuery(searchString);
        ClientResponse resp = client.get(searchString);
        System.out.println("resp: "+resp.getStatusText());

        if (resp.getType() == ResponseType.SUCCESS) {
            Document docAbdera = resp.getDocument();
            Element elementAbdera = docAbdera.getRoot();

            System.out.println("response: " + elementAbdera.toString());

            Iterator<Element> elementIterator = elementAbdera.getElements().get(0).getElements().iterator();
            System.out.println("number of docs found: " + elementAbdera.getElements().get(0).getElements().size());

            //looping through the 19 items
//            while (elementIterator.hasNext()) {
//                NationaalArchiefDoc doc = new NationaalArchiefDoc();
//                Element element1 = elementIterator.next();
//
//                //looping through all elements of each item
//                for (Element element2 : element1.getElements()) {
//                    doc.getKeyValues().put(element2.getQName().getLocalPart(), element2.getText());
//                    if (element2.getQName().getLocalPart().equals("subject")) {
//                        System.out.println("element key: " + element2.getQName().getLocalPart());
//                        System.out.println("element value: " + element2.getText());
//                        System.out.println("");
//                    }
//                }
//                docs.add(doc);
//            }
        }
//        return docs;
    }
}
