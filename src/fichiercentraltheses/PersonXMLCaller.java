/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fichiercentraltheses;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
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
public class PersonXMLCaller {

    public String callForXMLResponse () throws URISyntaxException, IOException {
        HttpClient httpclient = new DefaultHttpClient();

        URIBuilder builder = new URIBuilder();
        builder.setScheme("http").setHost("www.theses.fr").setPath("/personnes/")
                .setParameter("q", "levallois")
                .setParameter("format", "xml");
                
        URI uri = builder.build();
        HttpGet httpget = new HttpGet(uri);
//        HttpGet httpget = new HttpGet("http://ws.seloger.com/search.xml?ci=690381,690382,690383,690387&idqfix=1&idtt=2&idtypebien=1&tri=d_dt_crea");

        HttpResponse response = httpclient.execute(httpget);
        HttpEntity entity = response.getEntity();
        if (entity == null) {
            System.out.println("empty response to API call");
        }
        String responseString = EntityUtils.toString(entity);
        System.out.println(responseString);
        return responseString;
    }

}
