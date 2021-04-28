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
    
    private String name;
    
    private static final String NAME_LABEL = "Name: ";
    private static final String VAC_LABEL = "Vaccines: ";
    
    
    public VaccineDelivery(  ){
       vaccineDeliveryAvailability = new HashMap<>();
       name = ObjectUtility.DEFAULT_NAME;
    }
    
    public long getId(){
        return id;
    }
    
    public String getName(){
        return name;
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
    
    public void setName( String name ){
        this.name = name;
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
                if ( ObjectUtility.objectsAreEqual( this.name, vc.name)){
                    equalObs = true;
                }
            }
        }
        return equalObs;
    }
    
    @Override
    public int hashCode(){
        int base = ObjectUtility.hashCode( vaccineDeliveryAvailability);
        base = base * 17 + ObjectUtility.hashCode( name );
       return base;  
    }
    
    @Override
    public String toString(){
        //Set<Vaccine> vaccines = vaccineDeliveryAvailability.keySet();
        StringBuilder build = new StringBuilder();
        build.append( NAME_LABEL ).append( name ).append( "\n");
        /*for ( Vaccine vac : vaccines ){
            build.append( VAC_LABEL ).append(vac).append(", availability=").append(vaccineDeliveryAvailability.get(vac));
        }*/
        build.append( VAC_LABEL ).append( vaccineDeliveryAvailability.toString()).append("\n");
        return build.toString();
    }
    
    /**
     * Constructs a vaccine delivery model from a textual representation.
     * @param devString - a textual description of a vaccine delivery model.
     * @return - the corresponding VaccineDelivery.
     */
    public static VaccineDelivery fromString( String devString ){
        VaccineDelivery del = new VaccineDelivery();
        String[] lines = devString.split( "\n");
        if ( lines.length == 2){
            del.setName( lines[0].substring(NAME_LABEL.length(), lines[0].length()));
          
            try {
                lines[1] = lines[1].replaceAll( VAC_LABEL, "");
                String[] pairs = ObjectUtility.mapParse( lines[1] );
                Map<Vaccine, VaccineAvailabilityModelContinuous> avail = new HashMap<>();
                for (String pair : pairs) {
                    if ( pair.contains( "=")){
                        String[] parts = pair.split("=");
                        avail.put(Vaccine.valueOf(parts[0].trim()), VaccineAvailabilityModelContinuous.fromString(parts[1].trim()));
                    }
                }
                del.setVaccineDeliveryAvailability(avail);
            } 
            catch (IllegalArgumentException iae) {
                System.out.println("Error parsing vaccine availability: " + lines[1] + iae);
            }
        }
        else {
            System.out.println( "Could not parse vaccine availability model for "+devString);
        }
        return del;
        
    }
}
