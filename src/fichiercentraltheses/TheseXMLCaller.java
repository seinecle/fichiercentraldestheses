/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fichiercentraltheses;

import Controller.App;
import Model.Academic;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Calendar;
import static java.util.Calendar.DAY_OF_YEAR;
import java.util.Set;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author C. Levallois
 */
public class TheseXMLCaller {

    public static int index;

    public void callForXMLResponse(int year) throws URISyntaxException, IOException {
        HttpClient httpclient = new DefaultHttpClient();
        TheseAPIXMLResponseParser xmlParser;
        Set<Academic> doctors;
        index = 1;

//        URIBuilder builder = new URIBuilder();
//        builder.setScheme("http").setHost("www.theses.fr").setPath("/")
//                .setParameter("q", "")
//                .setParameter("fq", "dateSoutenance:[2013-01-01T23:59:59Z%2BTO%2B2013-12-31T23:59:59Z]")
//                .setParameter("sort", "dateSoutenance+desc")
//                .setParameter("format", "xml");
//        URI uri = builder.build();
//        HttpGet httpget = new HttpGet(uri);
        boolean finished = false;
        while (!finished) {

            int indexBefore = index;
            HttpGet httpget = new HttpGet("http://www.theses.fr/?q=&fq=dateSoutenance:[" + year + "-" + "01" + "-01T23:59:59Z%2BTO%2B" + year + "-" + "12" + "-" + "31" + "T23:59:59Z]&sort=dateSoutenance+desc&start=" + index + "&format=xml");

            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            if (entity == null) {
                System.out.println("empty response to API call");
            }

            String responseString = EntityUtils.toString(entity);

            if (!responseString.contains("<doc>")) {
                finished = true;
            } else {
                xmlParser = new TheseAPIXMLResponseParser(responseString);
                doctors = xmlParser.parse();
                System.out.println("just finished year " + year + ", docs " + indexBefore + " to " + (index-1));

                for (Academic academic : doctors) {
//                    res.put(academic.getPpn(), academic.getFullname());
                    App.resNoId.add(academic.getFullname());
                }

            }
        }
//        System.out.println(responseString);
    }

}
