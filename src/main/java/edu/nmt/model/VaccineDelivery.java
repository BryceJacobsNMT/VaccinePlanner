/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nmt.model;

import edu.nmt.util.ObjectUtility;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author bryce
 */
@Entity
@Table( name="vaccinedelivery")
public class VaccineDelivery implements Serializable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    @ElementCollection
    private Map<Vaccine,VaccineAvailabilityModelContinuous> vaccineDeliveryAvailability;
    
    
    public VaccineDelivery(  ){
       vaccineDeliveryAvailability = new HashMap<>();
    }
    
    public long getId(){
        return id;
    }
    
    public Map<Vaccine, VaccineAvailabilityModelContinuous> getVaccineDeliveryAvailability(){
        return vaccineDeliveryAvailability;
    }
    
    
    public void setId( long id ){
        this.id = id;
    }
    
    public void addVaccineSource( Vaccine vaccine, VaccineAvailabilityModelContinuous availabilityModel ){
        assert( vaccine != null );
        assert( availabilityModel != null );
        vaccineDeliveryAvailability.put( vaccine, availabilityModel);
    }
    
    public int getDoses( int elapsedDays, Vaccine vac ){
        int doses = 0;
        if ( vaccineDeliveryAvailability.containsKey(vac)){
            VaccineAvailabilityModelContinuous availModel = vaccineDeliveryAvailability.get(vac);
            doses = availModel.getDoses( elapsedDays );
        }
        return doses;
    }
    
    public void setVaccineDeliveryAvailability( Map<Vaccine, VaccineAvailabilityModelContinuous> model ){
        this.vaccineDeliveryAvailability.clear();
        this.vaccineDeliveryAvailability.putAll( model );
    }
    
   
    @Override
    public boolean equals( Object o ){
        boolean equalObs = false;
        if ( o instanceof VaccineDelivery ){
            VaccineDelivery vc = (VaccineDelivery)o;
            if ( ObjectUtility.objectsAreEqual( this.vaccineDeliveryAvailability, vc.vaccineDeliveryAvailability )){
                equalObs = true;
            }
        }
        return equalObs;
    }
    
    @Override
    public int hashCode(){
        int base = ObjectUtility.hashCode( vaccineDeliveryAvailability);
       return base;  
    }
    
    @Override
    public String toString(){
        Set<Vaccine> vaccines = vaccineDeliveryAvailability.keySet();
        StringBuilder build = new StringBuilder();
        for ( Vaccine vac : vaccines ){
            build.append( "Vaccine: ").append(vac).append(", availability=").append(vaccineDeliveryAvailability.get(vac));
        }
        return build.toString();
    }
}
