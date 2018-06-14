/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fact.it.www.entity;

import fact.it.www.beans.BetaalStrategie;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

/**
 *
 * @author Bram Van Bergen
 */
@NamedQueries ({
    @NamedQuery(
        name = "Bestelling.zoekOpTafel",
        query = "select b FROM Bestelling AS b where (b.tafel.id) = :tafelId"
    ),
    @NamedQuery(
        name = "Bestelling.zoekOpDag",
        query = "select b FROM Bestelling b where (b.datum) BETWEEN :datum AND :datum2"
    ),
    @NamedQuery(
        name = "Bestelling.zoekOpMaand",
        query = "select b FROM Bestelling b where extract(month from b.datum) = :maand"
    ),
    @NamedQuery(
        name = "Bestelling.zoekOpJaar",
        query = "select b FROM Bestelling b where extract(year from b.datum) = :jaar"
    )
})
@Entity
public class Bestelling implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private GregorianCalendar datum;
    private boolean betaald;
    
    @Transient
    private BetaalStrategie betaalStrategie;
    
    @ManyToOne
    private Zaalpersoneel zaalpersoneel;
    
    @ManyToOne
    private Tafel tafel;
    
    @OneToMany(mappedBy = "bestelling", cascade = CascadeType.PERSIST)
    private List<BesteldItem> besteldeItems = new ArrayList<BesteldItem>();
    
    public Bestelling(){
        
    }

    public Zaalpersoneel getZaalpersoneel() {
        return zaalpersoneel;
    }

    public void setZaalpersoneel(Zaalpersoneel zaalpersoneel) {
        this.zaalpersoneel = zaalpersoneel;
    }

    public Tafel getTafel() {
        return tafel;
    }

    public void setTafel(Tafel tafel) {
        this.tafel = tafel;
    }

    public List<BesteldItem> getBesteldeItems() {
        return besteldeItems;
    }

    public void setBesteldeItems(List<BesteldItem> besteldeItems) {
        this.besteldeItems = besteldeItems;
    }

    public GregorianCalendar getDatum() {
        return datum;
    }

    public void setDatum(GregorianCalendar datum) {
        this.datum = datum;
    }

    public boolean isBetaald() {
        return betaald;
    }

    public void setBetaald(boolean betaald) {
        this.betaald = betaald;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BetaalStrategie getBetaalStrategie() {
        return betaalStrategie;
    }

    public void setBetaalStrategie(BetaalStrategie betaalStrategie) {
        this.betaalStrategie = betaalStrategie;
    }
    
    public void addItem(Gerecht gerecht, int aantal){
        BesteldItem besteldItem = new BesteldItem();
        besteldItem.setAantal(aantal);
        besteldItem.setGerecht(gerecht);
        besteldItem.setBestelling(this);
        besteldItem.setToegepastePrijs(betaalStrategie.getToegepastePrijs(gerecht.getActuelePrijs()));
        besteldeItems.add(besteldItem);
    } 
    
    public void removeItem(Gerecht gerecht, int aantal, double prijs){
        List<BesteldItem> toRemove = new ArrayList<>();
        
        for(BesteldItem besteldItem : besteldeItems){
            System.out.println(besteldItem.getAantal() + " --> " + aantal);
            System.out.println(besteldItem.getToegepastePrijs()+ " --> " + prijs);
            System.out.println(besteldItem.getGerecht()+ " --> " + gerecht);
            
            if(besteldItem.getAantal() == aantal && besteldItem.getToegepastePrijs() == prijs && besteldItem.getGerecht().equals(gerecht)){
                toRemove.add(besteldItem);
            }
        }
        
        for(BesteldItem besteldItem : toRemove){
            besteldeItems.remove(besteldItem);
        }
    }
    
    public double getTotal(){
        double sum = 0;
        
        for(BesteldItem bi: besteldeItems){
            sum += bi.getAantal() * bi.getToegepastePrijs();
        }
        
        return (double) Math.round(sum * 100) / 100;
    }
    
    public void maakRekening(){
        double sum = 0;
        
        for(BesteldItem bi : besteldeItems){
            sum += bi.getAantal() * bi.getToegepastePrijs();
            System.out.println(bi.getAantal() + " " + bi.getGerecht().getNaam() + " prijs " + bi.getAantal() * bi.getToegepastePrijs());
        }
        
        System.out.println("---------------------");
        System.out.println("Totaal: " + (double) Math.round(sum * 100) / 100);
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bestelling)) {
            return false;
        }
        Bestelling other = (Bestelling) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fact.it.www.entity.Bestelling[ id=" + id + " ]";
    }
    
}