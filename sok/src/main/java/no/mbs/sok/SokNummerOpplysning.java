package no.mbs.sok;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by admin on 04.11.2017.
 */

class SokNummerOpplysning extends AsyncTask<String, Void, String> {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    private String navn;
    private String gateadresse;
    private String postNr;
    private String postSted;

    public String sok(String query) {
        String resultat = "";
        this.navn = null;
        this.gateadresse = null;
        this.postNr = null;
        this.postSted = null;

        try {
            List<String> httpResultat = lesHeleHttpResponsOgLukk(sokGuleSiderFirma(query));
            httpResultat = sokGuleSiderPerson(httpResultat, query);


            for (int i = 0; i < httpResultat.size(); i++) {
                String line =  httpResultat.get(i);
                logger.log(Level.INFO, line);
                if(line.contains("Nummeret brukes til telefonsalg eller lignende")) {
                    return "Nummeret brukes til telefonsalg eller lignende";
                }
                if(line.contains("Nummeret er reservert mot nummeropplysning")) {
                    return "Nummeret er reservert mot nummeropplysning";
                }
                if(line.contains("nummeret er hemmelig eller ikke er i bruk")) {
                    return "Nummeret er hemmelig eller ikke er i bruk";
                }
                parseGulesiderHtmlResponse(line);
                parseGulesiderHtmlResponseMultiline(line, i, httpResultat);
            }
        } catch (Throwable e) {
            logger.log(Level.WARNING, "Sok Huffda");
            logger.log(Level.WARNING, e.toString());
            e.printStackTrace();
        }

        if(this.navn != null) {
            resultat = navn + "\n";
            resultat += gateadresse + "\n";
            resultat += postNr + " " + postSted + "\n";

        }



        return resultat;
    }



    private List<String> lesHeleHttpResponsOgLukk(BufferedReader httpRespons) throws IOException {
        List<String> resultat = new ArrayList<>();
        String httpLine;
        while ((httpLine = httpRespons.readLine()) != null) {
            resultat.add(httpLine);
        }
        httpRespons.close();
        return resultat;
    }

    private BufferedReader sokGuleSiderFirma(String query) throws IOException {
        return sokGuleSider("https://www.gulesider.no/finn:" + query);
    }

    private List<String> sokGuleSiderPerson(List<String> httpResultat, String query) throws IOException {
        for (String line : httpResultat) {
            if(line.contains("ga treff i")) {
                return lesHeleHttpResponsOgLukk(sokGuleSider("https://www.gulesider.no/person/resultat/" + query));
            }
        }
        return httpResultat;
    }

    private BufferedReader sokGuleSider(String url) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        int responseCode = con.getResponseCode();

        logger.log(Level.WARNING, "\nSending 'GET' request to URL : " + url);
        logger.log(Level.WARNING, "Response Code : " + responseCode);

        final InputStream iostream;
        if(responseCode == HttpURLConnection.HTTP_NOT_FOUND) {
            iostream = con.getErrorStream();
        } else {
            iostream = con.getInputStream();
        }

        return new BufferedReader(new InputStreamReader(iostream));
    }

    @Override
    protected String doInBackground(String... params) {
        return new SokNummerOpplysning().sok(params[0]);
    }


    private void parseGulesiderHtmlResponse(String inputLine) {

        ////nettsjekk.gulesider.no/?core=hl&INP=0&companyName=Garuda Oslo AS&companyPhone=415 69 331&companyStreet=Haakon VII's gate9&postCode=0161&geoArea=Oslo
        if(inputLine.contains("companyName")) {
            logger.log(Level.WARNING, "* * * * companyName " + inputLine);
            int companyNameStart = inputLine.indexOf("companyName") + 12;
            int companyNameEnd = inputLine.indexOf("&", companyNameStart);
            this.navn = inputLine.substring(companyNameStart, companyNameEnd );
        }
        if(inputLine.contains("hitTitle")) {
            logger.log(Level.WARNING, "* * * * hitTitle " + inputLine);
            int companyNameStart = inputLine.indexOf("hitTitle") + 11;
            int companyNameEnd = inputLine.indexOf("\"", companyNameStart);
            this.navn = inputLine.substring(companyNameStart, companyNameEnd );
        }
        // <a class="stripped-link lightblue-link profile-page-link addax addax-ps_hl_name_click" href="/p/didrik+villanger/137481902">Didrik Villanger</a>
        if(inputLine.contains("addax-ps_hl_name_click")) {
            logger.log(Level.WARNING, "* * * * addax-ps_hl_name_click " + inputLine);
            int personNameStart = inputLine.indexOf("\">") + 2;
            int personNameEnd = inputLine.indexOf("</a>", personNameStart);
            this.navn = inputLine.substring(personNameStart, personNameEnd );
        }

        // nettsjekk.gulesider.no/?core=hl&INP=0&companyName=Garuda Oslo AS&companyPhone=415 69 331&companyStreet=Haakon VII's gate9&postCode=0161&geoArea=Oslo
        if(inputLine.contains("companyStreet")) {
            logger.log(Level.WARNING, "* * * * companyStreet " + inputLine);
            int companyStreetStart = inputLine.indexOf("companyStreet") + 14;
            int companyStreetEnd = inputLine.indexOf("&", companyStreetStart);
            this.gateadresse = inputLine.substring(companyStreetStart, companyStreetEnd );
        }
        // <span class="street-address">Fridtjof Nansens plass 5</span>
        if(inputLine.contains("\"street-address")) {
            logger.log(Level.WARNING, "* * * * street-address " + inputLine);
            int companyStreetStart = inputLine.indexOf("street-address") + 16;
            int companyStreetEnd = inputLine.indexOf("<", companyStreetStart);
            this.gateadresse = inputLine.substring(companyStreetStart, companyStreetEnd );
        }
        if(inputLine.contains("postCode")) {
            logger.log(Level.WARNING, "* * * * postCode " + inputLine);
            int postCodeStart = inputLine.indexOf("postCode") + 9;
            int postCodeEnd = inputLine.indexOf("&", postCodeStart);
            this.postNr = inputLine.substring(postCodeStart, postCodeEnd );
        }
        //<span class="postal-code">0160</span>
        if(inputLine.contains("\"postal-code")) {
            logger.log(Level.WARNING, "* * * * postal-code " + inputLine);
            int postCodeStart = inputLine.indexOf("\"postal-code") + 14;
            int postCodeEnd = inputLine.indexOf("<", postCodeStart);
            this.postNr = inputLine.substring(postCodeStart, postCodeEnd );
        }
        // <span class="hit-postal-code">1458</span>
        if(inputLine.contains("hit-postal-code")) {
            logger.log(Level.WARNING, "* * * * hit-postal-code " + inputLine);
            int postCodeStart = inputLine.indexOf("hit-postal-code") + 17;
            int postCodeEnd = inputLine.indexOf("<", postCodeStart);
            this.postNr = inputLine.substring(postCodeStart, postCodeEnd );
        }
        if(inputLine.contains("geoArea")) {
            logger.log(Level.WARNING, "* * * * geoArea " + inputLine);
            int geoAreaStart = inputLine.indexOf("geoArea") + 8;
            int geoAreaEnd = inputLine.indexOf("\"", geoAreaStart);
            String postSted = inputLine.substring(geoAreaStart, geoAreaEnd );
            if(!"empty".equals(postSted)) {
                this.postSted = postSted;
            }
        }
        //<span class="locality">Oslo</span>
        if(inputLine.contains("\"locality")) {
            logger.log(Level.WARNING, "* * * * locality " + inputLine);
            int geoAreaStart = inputLine.indexOf("\"locality") + 11;
            int geoAreaEnd = inputLine.indexOf("<", geoAreaStart);
            String postSted = inputLine.substring(geoAreaStart, geoAreaEnd );
            if(!"empty".equals(postSted)) {
                this.postSted = postSted;
            }
        }
        // <span class="hit-address-locality">Fjellstrand</span>
        if(inputLine.contains("hit-address-locality")) {
            logger.log(Level.WARNING, "* * * * hit-address-locality " + inputLine);
            int geoAreaStart = inputLine.indexOf("hit-address-locality") + 22;
            int geoAreaEnd = inputLine.indexOf("<", geoAreaStart);
            String postSted = inputLine.substring(geoAreaStart, geoAreaEnd );
            if(!"empty".equals(postSted)) {
                this.postSted = postSted;
            }
        }
    }

    private void parseGulesiderHtmlResponseMultiline(String line, int i, List<String> httpResultat) {
        // <span class="hit-street-address">
        // Hellaveien 29
        // </span>
        if(line.contains("hit-street-address")) {
            String gateAdresse = httpResultat.get(i+1);
            logger.log(Level.WARNING, "* * * * hit-street-address " + gateAdresse);
            this.gateadresse = gateAdresse;
        }
    }
}
