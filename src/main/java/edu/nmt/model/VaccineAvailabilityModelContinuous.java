/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nmt.model;

/**
 * Vaccine availability model using a continuous function.
 * @author bryce
 */
public class VaccineAvailabilityModelContinuous implements VaccineAvailabilityModel {
  
    
    private VaccineContinuousModelType modelType;
    private int initialAmount;
    private float growthFactor;
    private long id;
    
    private VaccineAvailabilityModelContinuous(VaccineContinuousModelType modelType,  int initial, float growthFactor ){
        this.modelType = modelType;
        initialAmount = initial;
        this.growthFactor = growthFactor;
    }
    
    @Override
    public int getDoses( int dayCount ){
        return modelType.getDoses( dayCount, initialAmount, growthFactor);
    }
    
    public float getGrowthFactor(){
        return growthFactor;
    }
    
    public long getId(){
        return id;
    }
    
    public int getInitialAmount(){
        return initialAmount;
    }
    
    public VaccineContinuousModelType getVaccineContinuousModelType(){
        return modelType;
    }
    
    public void setGrowthFactor( float growthFactor ){
        this.growthFactor = growthFactor;
    }
    
    public void setId( long id ){
        this.id = id;
    }
    
    public void setInitialAmount( int amount ){
        this.initialAmount = amount;
    }
    
    public void setVaccineContinuousModelType( VaccineContinuousModelType modelType ){
        this.modelType = modelType;
    }
    
}
