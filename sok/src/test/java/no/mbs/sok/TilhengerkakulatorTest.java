package no.mbs.sok;

import org.junit.Test;

import no.mbs.sok.vegvesen.Bil;
import no.mbs.sok.vegvesen.BilHengerFoererkort;
import no.mbs.sok.vegvesen.Tilhenger;

import static org.junit.Assert.assertEquals;

/**
 * Created by admin on 15.11.2017.
 */
public class TilhengerkakulatorTest {

    @Test
    public void hengerTotalvekt750_klasse_B() {
        String forventetForerkortKlasse = "B";
        Bil bil = new Bil();
        bil.setVogntogvekt(2050);
        bil.setMaksTilhengervektUtenBremser(750);
        bil.setMaksTilhengervektMedBremser(1300);

        Tilhenger tilhenger = new Tilhenger();
        tilhenger.setTotalvekt(750);

        BilHengerFoererkort bilHengerFoererkort = new BilHengerFoererkort();
        bilHengerFoererkort.setBil(bil);
        bilHengerFoererkort.setTilhenger(tilhenger);

        assertEquals(forventetForerkortKlasse, new Tilhengerkakulator().beregnFoererkortklasse(bilHengerFoererkort));
    }

    @Test
    public void hengerTotalvekt1300_klasse_B() {
        String forventetForerkortKlasse = "B";
        Bil bil = new Bil();
        bil.setVogntogvekt(2600);
        bil.setMaksTilhengervektUtenBremser(750);
        bil.setMaksTilhengervektMedBremser(1300);

        Tilhenger tilhenger = new Tilhenger();
        tilhenger.setTotalvekt(1300);

        BilHengerFoererkort bilHengerFoererkort = new BilHengerFoererkort();
        bilHengerFoererkort.setBil(bil);
        bilHengerFoererkort.setTilhenger(tilhenger);

        assertEquals(forventetForerkortKlasse, new Tilhengerkakulator().beregnFoererkortklasse(bilHengerFoererkort));
    }

    @Test
    public void hengerTotalvekt750_bilMaksHengervekt650_ikke_trekkbar_henger() {
        String forventetForerkortKlasse = "Bilen kan ikke trekke henger";
        Bil bil = new Bil();
        bil.setVogntogvekt(2600);
        bil.setMaksTilhengervektUtenBremser(650);
        bil.setMaksTilhengervektMedBremser(1300);

        Tilhenger tilhenger = new Tilhenger();
        tilhenger.setTotalvekt(750);

        BilHengerFoererkort bilHengerFoererkort = new BilHengerFoererkort();
        bilHengerFoererkort.setBil(bil);
        bilHengerFoererkort.setTilhenger(tilhenger);

        assertEquals(forventetForerkortKlasse, new Tilhengerkakulator().beregnFoererkortklasse(bilHengerFoererkort));
    }

    @Test
    public void B96() {
        String forventetForerkortKlasse = "B96";
        Bil bil = new Bil();
        bil.setVogntogvekt(3501);
        bil.setMaksTilhengervektUtenBremser(750);
        bil.setMaksTilhengervektMedBremser(1600);

        Tilhenger tilhenger = new Tilhenger();
        tilhenger.setTotalvekt(1300);

        BilHengerFoererkort bilHengerFoererkort = new BilHengerFoererkort();
        bilHengerFoererkort.setBil(bil);
        bilHengerFoererkort.setTilhenger(tilhenger);

        assertEquals(forventetForerkortKlasse, new Tilhengerkakulator().beregnFoererkortklasse(bilHengerFoererkort));
    }

    @Test
    public void BE() {
        String forventetForerkortKlasse = "BE";
        Bil bil = new Bil();
        bil.setVogntogvekt(4251);
        bil.setMaksTilhengervektUtenBremser(750);
        bil.setMaksTilhengervektMedBremser(2400);

        Tilhenger tilhenger = new Tilhenger();
        tilhenger.setTotalvekt(2400);

        BilHengerFoererkort bilHengerFoererkort = new BilHengerFoererkort();
        bilHengerFoererkort.setBil(bil);
        bilHengerFoererkort.setTilhenger(tilhenger);

        assertEquals(forventetForerkortKlasse, new Tilhengerkakulator().beregnFoererkortklasse(bilHengerFoererkort));
    }

    @Test
    public void test() {
        String l = "<a class=\"lightblue-link hit-company-features-link e-icon-checkcircle yext addax addax-cs_hl_yext_click\" href=\"//nettsjekk.gulesider.no/?core=hl&INP=0&companyName=Hiha AS&companyPhone=911 69 331&companyStreet=Nostreet9&postCode=0161&geoArea=Oslo\" target=\"_blank\" title=\"Ditt firma? Sjekk din digitale stedeværelse på internett.\">";

        int companynameStart = l.indexOf("companyName");
        int companynameEnd = l.indexOf("&", companynameStart);
        System.out.println(l.substring(companynameStart, companynameEnd ));

    }

}