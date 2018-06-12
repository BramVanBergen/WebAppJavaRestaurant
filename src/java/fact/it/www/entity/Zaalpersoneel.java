/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fact.it.www.entity;

import fact.it.www.beans.IngangTeller;
import java.io.Serializable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 *
 * @author Bram Van Bergen
 */
@Entity
@DiscriminatorValue("ZaalPersoneel")
public class Zaalpersoneel extends Personeel implements Serializable {

    public Zaalpersoneel() {
        
    }
    
    public Zaalpersoneel(String naam) {
        super(naam);
    }
    
    //doe hier iets als er klanten binnenkomen
    @Override
    public void update() {
        String zaalstring = "Ik ben " + getNaam() + " en ga het nodige doen om voor " + IngangTeller.getInstance().getAantal() + " klanten een tafel klaar te maken.";
     System.out.println(zaalstring);
    }

}
