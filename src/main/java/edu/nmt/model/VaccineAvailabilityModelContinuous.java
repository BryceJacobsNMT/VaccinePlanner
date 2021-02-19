/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nmt.model;

/**
 *
 * @author bryce
 */
public class VaccineAvailabilityModelContinuous implements VaccineAvailabilityModel {
  
    
    private final VaccineContinuousModelType modelType;
    private final int initialAmount;
    private final float growthFactor;
    
    private VaccineAvailabilityModelContinuous(VaccineContinuousModelType modelType,  int initial, float growthFactor ){
        this.modelType = modelType;
        initialAmount = initial;
        this.growthFactor = growthFactor;
    }
    
    @Override
    public int getDoses( int dayCount ){
        return modelType.getDoses( dayCount, initialAmount, growthFactor);
    }
    
}
