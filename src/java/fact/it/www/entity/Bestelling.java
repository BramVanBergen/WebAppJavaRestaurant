/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fact.it.www.entity;

import fact.it.www.beans.BetaalStrategie;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

/**
 *
 * @author Bram Van Bergen
 */
@Entity
public class Bestelling implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private GregorianCalendar datum;
    private Boolean betaald;

    @Transient
    private BetaalStrategie betaalStrategie;

    @ManyToOne
    private Personeel personeel;

    public BetaalStrategie getBetaalStrategie() {
        return betaalStrategie;
    }

    public void setBetaalStrategie(BetaalStrategie betaalStrategie) {
        this.betaalStrategie = betaalStrategie;
    }

    @ManyToOne
    private Tafel tafel;

    @OneToMany(mappedBy = "bestelling", cascade = CascadeType.PERSIST)
    private List<BesteldItem> itemlijst = new ArrayList<BesteldItem>();

    public Bestelling() {
    }

    public void addItem(Gerecht gerecht, int aantal) {
        BesteldItem besteldItem = new BesteldItem();
        besteldItem.setAantal(aantal);
        besteldItem.setGerecht(gerecht);
        besteldItem.setBestelling(this);
        besteldItem.setToegepastePrijs(gerecht.getActuelePrijs());
        itemlijst.add(besteldItem);
    }

    public void maakRekening() {
        double sum = 0;
        for (BesteldItem bi : itemlijst) {
            sum += bi.getAantal() * bi.getToegepastePrijs();
            System.out.println(bi.getAantal() + " "
                    + bi.getGerecht().getNaam() + " prijs "
                    + bi.getAantal() * bi.getToegepastePrijs());
        }
        System.out.println("-----------------------------------");
        System.out.println("Totaal: " + sum);

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

    public Personeel getPersoneel() {
        return personeel;
    }

    public void setPersoneel(Personeel personeel) {
        this.personeel = personeel;
    }

    public Tafel getTafel() {
        return tafel;
    }

    public void setTafel(Tafel tafel) {
        this.tafel = tafel;
    }

    public Boolean getBetaald() {
        return betaald;
    }

    public void setBetaald(Boolean betaald) {
        this.betaald = betaald;
    }

    public List<BesteldItem> getItemlijst() {
        return itemlijst;
    }

    public void setItemlijst(List<BesteldItem> itemlijst) {
        this.itemlijst = itemlijst;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
