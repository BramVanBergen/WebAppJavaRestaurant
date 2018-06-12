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
@DiscriminatorValue("KeukenPersoneel")
public class Keukenpersoneel extends Personeel implements Serializable {

    public Keukenpersoneel() {
        
    }
    
    public Keukenpersoneel(String naam) {
        super(naam);
    }

    //doe hier iets als er klanten binnekomen
    @Override
    public void update() {
        String keukenstring = "Ik ben " + getNaam() + " en ik begin onmiddellijk met het maken van " + IngangTeller.getInstance().getAantal() + " amuse-gueules!";
        System.out.println(keukenstring);
    }
}
