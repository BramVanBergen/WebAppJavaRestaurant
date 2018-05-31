package fact.it.www.beans;

import fact.it.www.entity.Personeel;
import java.util.ArrayList;

/**
 * @author Bram Van Bergen
 */
public abstract class Subject {
    private ArrayList<Personeel> observers = new ArrayList<Personeel>();

    public ArrayList<Personeel> getObservers() {
        return observers;
    }

    public void setObservers(ArrayList<Personeel> observers) {
        this.observers = observers;
    }
    
    public Subject(){}
    
    public void attachObserver(Personeel obs){
	observers.add(obs);
    }
    
    public void detachObserver(Personeel obs){
          observers.remove(obs);
    }
    
    public void notifyObservers(){
          for(Personeel o:observers){
                  o.update();
          }
    }

}