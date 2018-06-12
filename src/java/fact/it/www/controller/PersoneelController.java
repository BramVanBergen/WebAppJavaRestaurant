package fact.it.www.controller;

import fact.it.www.dao.PersoneelFacade;
import fact.it.www.entity.Personeel;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;

/**
 *
 * @author Bram Van Bergen
 */
@Named(value = "personeelController")
@Dependent
public class PersoneelController implements Serializable{
    
    @EJB
    private PersoneelFacade personeelFacade;
    
    public PersoneelFacade getPersoneelFacade() {
        return personeelFacade;
    }
    
    public void setPersoneelFacade(PersoneelFacade personeelFacade) {
        this.personeelFacade = personeelFacade;
    }
    /**
     * Creates a new instance of PersoneelController
     */
    public PersoneelController() {
    }
    
    public List<Personeel> findAll() {
        return this.personeelFacade.findAll();
    }
}
