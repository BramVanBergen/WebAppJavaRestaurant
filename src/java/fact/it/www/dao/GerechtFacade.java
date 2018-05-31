/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fact.it.www.dao;

import fact.it.www.entity.Gerecht;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Bram Van Bergen
 */
@Stateless
public class GerechtFacade extends AbstractFacade<Gerecht> {

    @PersistenceContext(unitName = "2APP-BIT1_Van_Bergen_Bram_restaurant2018PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GerechtFacade() {
        super(Gerecht.class);
    }
    
}
