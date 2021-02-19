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
public class VaccineAvailabilityModelDiscrete implements VaccineAvailabilityModel {
  
    
    private final Map<Integer,Integer> deliveredDoses;
    
    private VaccineAvailabilityModelDiscrete(){
        deliveredDoses = new HashMap<>();
    }
    
    public void addDelivery( int elapsedDays, int doseCount ){
        deliveredDoses.put(elapsedDays, doseCount );
    }
    
    @Override
    public int getDoses( int dayCount ){
        int doses = 0;
        if ( deliveredDoses.containsKey(dayCount )){
            doses = deliveredDoses.get(dayCount);
        }
        return doses;
    }
    
}
