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
import fact.it.www.dao.KeukenpersoneelFacade;
import fact.it.www.dao.TafelFacade;
import fact.it.www.dao.ZaalpersoneelFacade;
import fact.it.www.entity.Gerecht;
import fact.it.www.entity.Bestelling;
import fact.it.www.entity.Keukenpersoneel;
import fact.it.www.entity.Tafel;
import fact.it.www.entity.Zaalpersoneel;
import java.io.Serializable;
import java.util.GregorianCalendar;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;

/**
 *
 * @author Bram Van Bergen
 */
@Named(value = "bestellingController")
@Dependent
public class BestellingController implements Serializable {

    @EJB
    private ZaalpersoneelFacade zaalpersoneelFacade;
    @EJB
    private KeukenpersoneelFacade keukenpersoneelFacade;
    @EJB
    private TafelFacade tafelFacade;
    @EJB
    private BestellingFacade bestellingFacade;
    @EJB
    private GerechtFacade gerechtFacade;

    /**
     * Creates a new instance of BestellingController
     */
    public BestellingController() {

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

        //personeelsleden koppelen
        klantTeller.attachObserver(dieter);
        klantTeller.attachObserver(arno);
        klantTeller.attachObserver(wim);
        klantTeller.attachObserver(tijmen);
        klantTeller.attachObserver(tom);

        zaalpersoneelFacade.create(dieter);
        zaalpersoneelFacade.create(arno);
        zaalpersoneelFacade.create(wim);
        keukenpersoneelFacade.create(tijmen);
        keukenpersoneelFacade.create(tom);

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
        bestelling4.addItem(tomatensoep, 6);
        bestelling4.addItem(stoofvlees, 4);
        bestelling4.addItem(stoofpot, 2);
        bestelling4.addItem(pannenkoek, 1);
        bestelling4.addItem(crème, 5);

        //Bestelling verder op vullen
        bestelling1.setTafel(tafel1);
        bestelling2.setTafel(tafel2);
        bestelling3.setTafel(tafel3);
        bestelling4.setTafel(tafel4);
        bestelling5.setTafel(tafel5);

        bestelling1.setPersoneel(arno);
        bestelling2.setPersoneel(wim);
        bestelling3.setPersoneel(dieter);
        bestelling4.setPersoneel(arno);
        bestelling5.setPersoneel(dieter);

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

        return "Homepage";
    }
}
