/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fact.it.www.dao;

import fact.it.www.entity.Bestelling;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Bram Van Bergen
 */
@Stateless
public class BestellingFacade extends AbstractFacade<Bestelling> {

    @PersistenceContext(unitName = "2APP-BIT1_Van_Bergen_Bram_restaurant2018PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BestellingFacade() {
        super(Bestelling.class);
    }
    
    /**
     * lijst van bestellingen oproepen
     * @param tafelId ==> tafel waarop we zoeken
     * @return lijst van bestellingen
     */
    public List<Bestelling> zoekOpTafel(Long tafelId){
        Query q = em.createNamedQuery("Bestelling.zoekOpTafel");
        q.setParameter("tafelId", tafelId);
        return q.getResultList();
    }
    
    /**
     * lijst van bestellingen oproepen
     * @param datum ==> datum waarop we zoeken
     * @return lijst van bestellingen
     * @throws ParseException 
     */
    public List<Bestelling> zoekOpDag(String datum) throws ParseException{
        DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(df.parse(datum));
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        String datum2 = df.format(calendar.getTime());
        
        Query q = em.createNamedQuery("Bestelling.zoekOpDag");
        q.setParameter("datum", datum);
        q.setParameter("datum2", datum2);
        return q.getResultList();
    }
    
    /**
     * lijst van bestellingen oproepen
     * @param maand ==> maand waarop we zoeken
     * @return lijst van bestellingen
     * @throws ParseException 
     */
    public List<Bestelling> zoekOpMaand(String maand) throws ParseException{
        DateFormat df = new SimpleDateFormat("yyyy-M");
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(df.parse(maand));
        int datum = calendar.get(Calendar.MONTH) + 1;
        System.out.println(datum);
        
        Query q = em.createNamedQuery("Bestelling.zoekOpMaand");
        q.setParameter("maand", datum);
        return q.getResultList();
    }
    
    /**
     * lijst van bestellingen oproepen
     * @param jaar ==> jaar waarop we zoeken
     * @return lijst van bestellingen
     */
    public List<Bestelling> zoekOpJaar(int jaar){
        Query q = em.createNamedQuery("Bestelling.zoekOpJaar");
        q.setParameter("jaar", jaar);
        return q.getResultList();
}
    
}
