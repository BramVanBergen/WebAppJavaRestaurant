/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fact.it.www.controller;

import fact.it.www.beans.HappyHourBetaling;
import fact.it.www.beans.IngangTeller;
import fact.it.www.beans.NormaleBetaling;
import fact.it.www.dao.BestellingFacade;
import fact.it.www.dao.GerechtFacade;
import fact.it.www.dao.PersoneelFacade;
import fact.it.www.dao.TafelFacade;
import fact.it.www.entity.Gerecht;
import fact.it.www.entity.Bestelling;
import fact.it.www.entity.Keukenpersoneel;
import fact.it.www.entity.Tafel;
import fact.it.www.entity.Zaalpersoneel;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;

/**
 *
 * @author Bram Van Bergen
 */
@Named(value = "bestellingController")
@SessionScoped
public class BestellingController implements Serializable {

    @EJB
    private BestellingFacade bestellingFacade;
    @EJB
    private TafelFacade tafelFacade;
    @EJB
    private GerechtFacade gerechtFacade;
    @EJB
    private PersoneelFacade personeelFacade;
    
    private Bestelling bestelling = new Bestelling();
    private List<Bestelling> bestellingen;
    private Zaalpersoneel zaalpersoneel = new Zaalpersoneel();
    private int jaar = Calendar.getInstance().get(Calendar.YEAR);
    private final List<String> betaalStrategieën = Arrays.asList("Normaal", "Happy Hour");
    protected HappyHourBetaling happyHourBetaling = new HappyHourBetaling();
    protected NormaleBetaling normaleBetaling = new NormaleBetaling();

    /**
     * Creates a new instance of BestellingController
     */
    public BestellingController() {
    }

    public TafelFacade getTafelFacade() {
        return tafelFacade;
    }

    public void setTafelFacade(TafelFacade tafelFacade) {
        this.tafelFacade = tafelFacade;
    }

    public BestellingFacade getBestellingFacade() {
        return bestellingFacade;
    }

    public void setBestellingFacade(BestellingFacade bestellingFacade) {
        this.bestellingFacade = bestellingFacade;
    }

    public GerechtFacade getGerechtFacade() {
        return gerechtFacade;
    }

    public void setGerechtFacade(GerechtFacade gerechtFacade) {
        this.gerechtFacade = gerechtFacade;
    }

    public Bestelling getBestelling() {
        return bestelling;
    }

    public void setBestelling(Bestelling bestelling) {
        this.bestelling = bestelling;
    }

    public List<Bestelling> getBestellingen() {
        return bestellingen;
    }

    public void setBestellingen(List<Bestelling> bestellingen) {
        this.bestellingen = bestellingen;
    }

    public Zaalpersoneel getZaalpersoneel() {
        return zaalpersoneel;
    }

    public void setZaalpersoneel(Zaalpersoneel zaalpersoneel) {
        this.zaalpersoneel = zaalpersoneel;
    }

    public int getJaar() {
        return jaar;
    }

    public void setJaar(int jaar) {
        this.jaar = jaar;
    }

    public List<String> getBetaalStrategieën() {
        return betaalStrategieën;
    }

    /**
     * Lijst van alle tafels maken
     *
     * @return lijst met tafels
     */
    public List<Tafel> findAllTafels() {
        return this.tafelFacade.findAll();
    }

    /**
     * Lijst van alle gerechten maken
     *
     * @return lijst met gerehcten
     */
    public List<Gerecht> findAllGerechten() {
        return this.gerechtFacade.findAll();
    }

    /**
     * Lijst van alle bestellingen maken en weergeven
     *
     * @return naam van de view
     */
    public String findAll() {
        bestellingen = this.bestellingFacade.findAll();

        return "overzichtBestellingen";
    }

    /**
     * Lijst van bestellingen via query weergeven
     *
     * @param tafelId ==> id van de tafel van de bestelling
     * @return naam van de view
     */
    public String zoekOpTafel(String tafelId) {
        Long tafelIdLong = Long.parseLong(tafelId);

        bestellingen = bestellingFacade.zoekOpTafel(tafelIdLong);
        return "overzichtBestellingen";
    }

    /**
     * Lijst van bestellingen via query weergeven
     *
     * @param dag ==> dag van de bestellingen
     * @return naam van de view
     * @throws ParseException
     */
    public String zoekOpDag(String dag) throws ParseException {
        bestellingen = bestellingFacade.zoekOpDag(dag);

        return "overzichtBestellingen";
    }

    /**
     * Lijst van bestellingen via query weergeen
     *
     * @param maand ==> maand van de bestellingen
     * @return naam van de view
     * @throws ParseException
     */
    public String zoekOpMaand(String maand) throws ParseException {
        bestellingen = bestellingFacade.zoekOpMaand(maand);

        return "overzichtBestellingen";
    }

    /**
     * Lijst van bestellingen via query weergeven
     *
     * @param jaar ==> jaar van de bestellingen
     * @return naam van de view
     */
    public String zoekOpJaar(int jaar) {
        bestellingen = bestellingFacade.zoekOpJaar(jaar);

        return "overzichtBestellingen";
    }

    /**
     * Details over een bepaalde bestelling weergeven
     *
     * @param id ==> van de bestelling
     * @return naam van de view
     */
    public String zoekBestelling(Long id) {
        bestelling = this.bestellingFacade.find(id);

        System.out.println(bestelling.getId());

        return "detailsBestellingen";
    }

    /**
     * Naar de pagina gaan om een bestelling te maken
     *
     * @param zaalpersoneel ==> verantwoordelijke voor de bestelling
     * @return naam van de view
     */
    public String maakBestelling(Zaalpersoneel zaalpersoneel) {
        this.zaalpersoneel = zaalpersoneel;

        bestelling = new Bestelling();

        return "maakBestelling";
    }

    public String addGerecht(int gerechtId, int aantal, String betaalStrategie) {
        Gerecht gerecht = gerechtFacade.find((long) gerechtId);

        switch (betaalStrategie) {
            case "Normaal":
                bestelling.setBetaalStrategie(normaleBetaling);
                break;
            case "Happy Hour":
                bestelling.setBetaalStrategie(happyHourBetaling);
                break;
        }

        bestelling.addItem(gerecht, aantal);

        return "maakBestelling";
    }

    public String removeBesteldItem(int gerechtId, int aantal, double prijs) {
        Gerecht gerecht = gerechtFacade.find((long) gerechtId);

        bestelling.removeItem(gerecht, aantal, prijs);

        return "maakBestelling";
    }

    public String opslaanBestelling(int tafelId) {
        Tafel tafel = tafelFacade.find((long) tafelId);

        bestelling.setTafel(tafel);
        bestelling.setZaalpersoneel(zaalpersoneel);
        bestelling.setDatum(new GregorianCalendar());

        System.out.println(zaalpersoneel.getNaam());

        this.bestellingFacade.create(bestelling);

        return "homepage";
    }

    public String opvullen() {
        IngangTeller klantTeller = IngangTeller.getInstance();

        //Betalingstrategieën aanmaken
        HappyHourBetaling happyHourBetaling = new HappyHourBetaling();
        NormaleBetaling normaleBetaling = new NormaleBetaling();

        //Gerechten aanmaken
        Gerecht stoofpot = new Gerecht();
        stoofpot.setNaam("Stoofpot van wild met kroketjes");
        stoofpot.setActuelePrijs(15.0);

        Gerecht stoofvlees = new Gerecht();
        stoofvlees.setNaam("Stoofvlees met frietjes");
        stoofvlees.setActuelePrijs(10.0);

        Gerecht pannenkoek = new Gerecht();
        pannenkoek.setNaam("Pannenkoek");
        pannenkoek.setActuelePrijs(6.0);

        Gerecht tomatensoep = new Gerecht();
        tomatensoep.setNaam("Tomatensoep");
        tomatensoep.setActuelePrijs(8.0);

        Gerecht crème = new Gerecht();
        crème.setNaam("Crème Brûlée");
        crème.setActuelePrijs(12.0);

        gerechtFacade.create(stoofpot);
        gerechtFacade.create(stoofvlees);
        gerechtFacade.create(pannenkoek);
        gerechtFacade.create(tomatensoep);
        gerechtFacade.create(crème);

        //Tafels aanmaken
        Tafel tafel1 = new Tafel();
        Tafel tafel2 = new Tafel();
        Tafel tafel3 = new Tafel();
        Tafel tafel4 = new Tafel();
        Tafel tafel5 = new Tafel();

        tafel1.setCode("tafel1");
        tafel2.setCode("tafel2");
        tafel3.setCode("tafel3");
        tafel4.setCode("tafel4");
        tafel5.setCode("tafel5");

        tafelFacade.create(tafel1);
        tafelFacade.create(tafel2);
        tafelFacade.create(tafel3);
        tafelFacade.create(tafel4);
        tafelFacade.create(tafel5);

        //Personeel aanmaken
        Zaalpersoneel dieter = new Zaalpersoneel("Dieter Verboven");
        Zaalpersoneel arno = new Zaalpersoneel("Arno Stoop");
        Zaalpersoneel wim = new Zaalpersoneel("Wim Naudts");
        Keukenpersoneel tijmen = new Keukenpersoneel("Tijmen Elseviers");
        Keukenpersoneel tom = new Keukenpersoneel("Tom Nuyts");

        klantTeller.attachObserver(dieter);
        klantTeller.attachObserver(arno);
        klantTeller.attachObserver(wim);
        klantTeller.attachObserver(tijmen);
        klantTeller.attachObserver(tom);

        this.personeelFacade.create(dieter);
        this.personeelFacade.create(arno);
        this.personeelFacade.create(wim);
        this.personeelFacade.create(tijmen);
        this.personeelFacade.create(tom);

        //Bestellingen aanmaken        
        Bestelling bestelling1 = new Bestelling();
        Bestelling bestelling2 = new Bestelling();
        Bestelling bestelling3 = new Bestelling();
        Bestelling bestelling4 = new Bestelling();
        Bestelling bestelling5 = new Bestelling();

        //Normale betalingen
        bestelling1.setBetaalStrategie(normaleBetaling);
        bestelling1.setDatum(new GregorianCalendar());
        bestelling1.addItem(pannenkoek, 3);
        bestelling1.addItem(crème, 2);

        bestelling2.setBetaalStrategie(normaleBetaling);
        bestelling2.setDatum(new GregorianCalendar());
        bestelling2.addItem(stoofvlees, 2);
        bestelling2.addItem(stoofpot, 1);
        bestelling2.addItem(tomatensoep, 3);

        bestelling3.setBetaalStrategie(normaleBetaling);
        bestelling3.setDatum(new GregorianCalendar());
        bestelling3.addItem(stoofpot, 4);
        bestelling3.addItem(crème, 2);
        bestelling3.addItem(pannenkoek, 2);

        //Happy hour betalingen
        bestelling4.setBetaalStrategie(happyHourBetaling);
        bestelling4.setDatum(new GregorianCalendar());
        bestelling4.addItem(tomatensoep, 5);
        bestelling4.addItem(stoofvlees, 5);
        bestelling4.addItem(crème, 5);

        bestelling5.setBetaalStrategie(happyHourBetaling);
        bestelling5.setDatum(new GregorianCalendar());
        bestelling5.addItem(tomatensoep, 6);
        bestelling5.addItem(stoofvlees, 4);
        bestelling5.addItem(stoofpot, 2);
        bestelling5.addItem(pannenkoek, 1);
        bestelling5.addItem(crème, 5);

        //Tafel aan bestelling toevoegen
        bestelling1.setTafel(tafel1);
        bestelling2.setTafel(tafel2);
        bestelling3.setTafel(tafel3);
        bestelling4.setTafel(tafel4);
        bestelling5.setTafel(tafel5);
        
        //Zaalpersoneel aan bestelling toevoegen
        bestelling1.setZaalpersoneel(dieter);
        bestelling2.setZaalpersoneel(arno);
        bestelling3.setZaalpersoneel(wim);
        bestelling4.setZaalpersoneel(wim);
        bestelling5.setZaalpersoneel(arno);

        //Rekening aanmaken
        bestelling1.maakRekening();
        bestelling2.maakRekening();
        bestelling3.maakRekening();
        bestelling4.maakRekening();
        bestelling5.maakRekening();

        //Toevoegen aan DB
        bestellingFacade.create(bestelling1);
        bestellingFacade.create(bestelling2);
        bestellingFacade.create(bestelling3);
        bestellingFacade.create(bestelling4);
        bestellingFacade.create(bestelling5);

        return "paginas/homepage";
    }
}
