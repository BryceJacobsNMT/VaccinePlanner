/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nmt.model;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author bryce
 */
public class VaccineDelivery {
    private Map<Vaccine, VaccineAvailabilityModel> vaccineAvailability;
    
    public VaccineDelivery(  ){
       vaccineAvailability = new HashMap<>();
    }
    
    public void addVaccineSource( Vaccine vaccine, VaccineAvailabilityModel availabilityModel ){
        assert( vaccine != null );
        assert( availabilityModel != null );
        vaccineAvailability.put( vaccine, availabilityModel);
    }
    
    public int getDoses( int elapsedDays, Vaccine vac ){
        int doses = 0;
        if ( vaccineAvailability.containsKey(vac)){
            VaccineAvailabilityModel availModel = vaccineAvailability.get(vac);
            doses = availModel.getDoses( elapsedDays );
        }
        return doses;
    }
}
