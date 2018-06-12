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
import fact.it.www.entity.Administrator;
import fact.it.www.entity.Bestelling;
import fact.it.www.entity.Gerecht;
import fact.it.www.entity.Keukenpersoneel;
import fact.it.www.entity.Poetspersoon;
import fact.it.www.entity.Zaalpersoneel;
import java.io.Serializable;
import java.util.GregorianCalendar;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.inject.Named;

/**
 *
 * @author Bram
 */
@Named(value = "testController")
@Dependent
public class TestController implements Serializable {

    @EJB
    private PersoneelFacade personeelFacade;    
    @EJB
    private BestellingFacade bestellingFacade;
    @EJB
    private GerechtFacade gerechtFacade;
    private Bestelling bestelling = new Bestelling();
    private List<Bestelling> bestellingen;

    /**
     * Creates a new instance of BestellingController
     */
    public TestController() {

    }

    public String testSingletonPatroon() {
        System.out.println("####################################################################");
        IngangTeller it1 = IngangTeller.getInstance();
        IngangTeller it2;
        it2 = IngangTeller.getInstance();
        if (it1 == it2) {
            System.out.println("De twee singletonvariabelen verwijzen naar hetzelfde object.");
        } else {
            System.out.println("Dit kan in principe niet.");
        }
        System.out.println("####################################################################");
        return "index";
    }
    
    public String testObserverPatroon() {
        IngangTeller klantTeller = IngangTeller.getInstance();

        //een paar personeelsleden
        Zaalpersoneel jan = new Zaalpersoneel("Jan");
        Zaalpersoneel piet = new Zaalpersoneel("Piet");
        Keukenpersoneel serge = new Keukenpersoneel("Serge");
        Keukenpersoneel jeroen = new Keukenpersoneel("Jeroen");

        //we koppelen de spelers en scheidsrechter als observer aan de bal
        klantTeller.attachObserver(jan);
        klantTeller.attachObserver(piet);
        klantTeller.attachObserver(serge);
        klantTeller.attachObserver(jeroen);
        this.personeelFacade.create(jan);
        this.personeelFacade.create(piet);
        this.personeelFacade.create(serge);
        this.personeelFacade.create(jeroen);

        System.out.println("####################################################################");
        System.out.println("Na het toevoegen van de observers...");
        //bal van positie veranderen
        klantTeller.setAantal(5);
        //lege lijn
        System.out.println();
        //we doen enkele observers weg
        klantTeller.detachObserver(piet);
        klantTeller.detachObserver(serge);

        System.out.println("Na het ontkoppelen van Piet en Serge ...");
        //we veranderen de bal weer van positie
        klantTeller.setAantal(3);
        System.out.println("####################################################################");
        return "index";
    }
    
    public String testStrategyPatroon() {
        System.out.println("####################################################################");
        //Betalingstrategieën aanmaken
        HappyHourBetaling happyHourBetaling = new HappyHourBetaling();
        NormaleBetaling normaleBetaling = new NormaleBetaling();
        //gerechten aanmaken
        Gerecht videe = new Gerecht();
        videe.setNaam("Vidée met frietjes");
        videe.setActuelePrijs(15.0);
        Gerecht croque = new Gerecht();
        croque.setNaam("Croque Monsieur");
        croque.setActuelePrijs(10.0);
        gerechtFacade.create(croque);
        gerechtFacade.create(videe);

        //maak bestelling met bestelitems
        Bestelling bestelling = new Bestelling();
        //NORMAAL
        bestelling.setBetaalStrategie(normaleBetaling);
        bestelling.setDatum(new GregorianCalendar());
        bestelling.addItem(videe, 2);
        bestelling.addItem(croque, 2);

        //HAPPYHOUR
        bestelling.setBetaalStrategie(happyHourBetaling);
        bestelling.addItem(videe, 2);
        bestelling.addItem(croque, 2);

        //NORMAAL
        bestelling.setBetaalStrategie(normaleBetaling);
        bestelling.addItem(videe, 2);
        bestelling.addItem(croque, 2);

        bestelling.maakRekening();
        System.out.println("####################################################################");

        //persistent maken
        this.bestellingFacade.create(bestelling);

        return "index";
    }
    
    public String testDecoratorPatroon() {
        IngangTeller ingangTeller = IngangTeller.getInstance();
        // een nieuw zaalpersoneelslid toevoegen   
        System.out.println("####################################################################");
        Zaalpersoneel manu = new Zaalpersoneel("Manu");
        ingangTeller.attachObserver(manu);
        ingangTeller.setAantal(7);
        // we gaan manu detachen en hem als poetspersoon attachen zodat hij nog altijd kan reageren op de klantenteller maar daarbij ook kan schoonmaken
        System.out.println("####################################################################");
        ingangTeller.detachObserver(manu);
        ingangTeller.setAantal(10);
        Poetspersoon poetsPersoon = new Poetspersoon();
        poetsPersoon.setPersoneel(manu);
        poetsPersoon.schoonMaken();
        // Manu moet nu ook nog de administratie erbij nemen als iemand binnenkomt
        System.out.println("####################################################################");
        Administrator administrator = new Administrator();
        administrator.setPersoneel(manu);
        ingangTeller.attachObserver(administrator);
        ingangTeller.setAantal(5);
        System.out.println("####################################################################");

        return "index";
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
}
