package no.mbs.sok;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import no.mbs.sok.vegvesen.BilHengerFoererkort;

/**
 * Created by admin on 29.10.2017.
 */

public class Tilhengerkakulator extends AsyncTask<String, Void, String> {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    /**
     * { "forerkortklasse":"B", "bil":{ "regnr":"pp86909", "tekniskkjoretoygruppe":"M1", "maksBelastningTilhengerKopling":85, "totalvekt":2335, "erAvregistrert":false, "vogntogvekt":3945, "nyttelast":555, "egenvekt":1705, "maksTilhengervektMedBremser":1600, "maksTilhengervektUtenBremser":750 } ,"tilhenger":{ "regnr":"rv3102", "tilhengergruppe":709, "egenvekt":345, "erAvregistrert":false, "maksBelastningTilhengerKopling":100, "totalvekt":2600, "nyttelast":2255 } }
     * @param kjoeretoy .
     * @param tilhenger .
     * @return .
     * @throws IOException .
     */
    private String tilhengerkalkulator(String kjoeretoy, String tilhenger) throws IOException {
        String url = "https://www.vegvesen.no/Kjoretoy/Eie+og+vedlikeholde/Tilhengerkalkulator/_window/tilhengerkalkulator+-+tmvop?registreringsnummer=" + kjoeretoy + "&registreringsnummerTilhenger=" + tilhenger + "&forerkortKlasse=B";
        logger.log(Level.WARNING, "URL GET: " + url);
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        int responseCode = 0;
        try {
            responseCode = con.getResponseCode();
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            System.out.println("Huffda");
        }

        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());

        BilHengerFoererkort bilOgHenger = new Gson().fromJson(response.toString(), BilHengerFoererkort.class);

        return beregn(bilOgHenger);


    }

    private String beregn(BilHengerFoererkort bilOgHenger) {
        return "Maks vogntogvekt: "  + bilOgHenger.getBil().getVogntogvekt()
                + "\nMaks tilhengervekt bremser: "  + bilOgHenger.getBil().getMaksTilhengervektMedBremser()
                + "\nHenger Totalvekt " + bilOgHenger.getTilhenger().getTotalvekt()
                + "\nFørerkortklasse: " + beregnFoererkortklasse(bilOgHenger);
    }

    /**
     * B:
     * Med klasse B kan du etter hovedregelen for bilførerkort trekke tilhenger med tillatt totalvekt høyst 750 kg.
     * Er bilens tillatte totalvekt 3500 kg, kan vogntogets (bil + tilhenger) tillatte totalvekt være inntil 4250 kg.
     *
     * Det kan også være tillatt å trekke tilhenger med tillatt totalvekt over 750 kg med klasse B. Etter den
     * såkalte «campingvogn-regelen» kan man med klasse B trekke en tilhenger med tillatt totalvekt over 750 kg
     * hvis summen av tilhengerens tillatte totalvekt + bilens tillatte totalvekt er høyst 3500 kg. Trekker man
     * bilens tillatte totalvekt ifra 3500 kg, finner man tilhengerens maksimum tillatte totalvekt.
     * «Tillatt totalvekt» finner du i vognkortene til bilen og tilhengeren.
     *
     * B96:
     * Fra 19. januar 2013 kan du dessuten få nytt førerkort påført kode 96 (klasse B 96) etter å ha gjennomført
     * minst 7 timers obligatorisk opplæring på en trafikkskole eller hos en kursarrangør. Det kreves ingen førerprøve.
     * Når opplæringen er registrert i vårt datasystem (3 dager etter fullført opplæring), kan du møte på en
     * trafikkstasjon og bestille nytt førerkort. Klasse B 96 gir rett til å føre vogntog (bil + tilhenger)
     * med samlet tillatt totalvekt som er 750 kg høyere enn vogntogvekten på 3500 kg for ren klasse B, dvs.
     * vogntogvekt inntil 4250 kg for klasse B 96. Se vår tilhengerkalkulator. Du kan også regne dette ut selv ved å følge anvisningene for trekking av mindre tilhenger.
     *
     * @param bilOgHenger .
     * @return .
     */
    protected String beregnFoererkortklasse(BilHengerFoererkort bilOgHenger) {
        String foererkortklasse = "BE";

        if(harHengerTotalvekt750(bilOgHenger)) {
            if(bilOgHenger.getBil().getMaksTilhengervektUtenBremser() < bilOgHenger.getTilhenger().getTotalvekt()) {
                foererkortklasse = "Bilen kan ikke trekke henger";
            }
            else {
                foererkortklasse = "B";
            }
        } else {
            if(bilOgHenger.getBil().getMaksTilhengervektMedBremser() < bilOgHenger.getTilhenger().getTotalvekt()) {
                foererkortklasse = "Bilen kan ikke trekke henger";
            }
            else {
                if (bilOgHenger.getBil().getVogntogvekt() <= 3500) {
                    foererkortklasse = "B";
                }
                else if (bilOgHenger.getBil().getVogntogvekt() <= 4250) {
                    foererkortklasse = "B96";
                }
            }
        }
        return foererkortklasse;
    }

    private boolean harHengerTotalvekt750(BilHengerFoererkort bilOgHenger) {
        return bilOgHenger.getTilhenger().getTotalvekt() <= 750;
    }


    @Override
    protected String doInBackground(String... params) {
        try {
            return this.tilhengerkalkulator(params[0], params[1]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
