package fact.it.www.controller;

import fact.it.www.beans.IngangTeller;
import fact.it.www.dao.BestellingFacade;
import fact.it.www.dao.GerechtFacade;
import fact.it.www.entity.ZaalPersoneel;
import fact.it.www.dao.PersoneelFacade;
import fact.it.www.entity.KeukenPersoneel;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;

/**
 *
 * @author Bram Van Bergen
 */
@Named(value = "personeelController")
@Dependent
public class PersoneelController {

    @EJB
    private GerechtFacade gerechtFacade;
    @EJB
    private BestellingFacade bestellingFacade;
    @EJB
    private PersoneelFacade personeelFacade;

    /**
     * Creates a new instance of PersoneelController
     */
    public PersoneelController() {
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
        ZaalPersoneel jan = new ZaalPersoneel("Jan");
        ZaalPersoneel piet = new ZaalPersoneel("Piet");
        KeukenPersoneel serge = new KeukenPersoneel("Serge");
        KeukenPersoneel jeroen = new KeukenPersoneel("Jeroen");

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
}
