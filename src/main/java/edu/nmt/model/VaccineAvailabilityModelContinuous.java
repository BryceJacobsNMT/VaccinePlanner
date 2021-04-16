/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nmt.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Vaccine availability model using a continuous function.
 * @author bryce
 */
@Entity
@Table( name="vaccineavailabilitymodelcontinuous")
public class VaccineAvailabilityModelContinuous implements Serializable {
  
    
    private VaccineContinuousModelType modelType;
    private int initialAmount;
    private float growthFactor;
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    
    public VaccineAvailabilityModelContinuous(){
        this.modelType = VaccineContinuousModelType.EXPONENTIAL;
        initialAmount = 100;
        this.growthFactor = .1f;
    }
    
    public VaccineAvailabilityModelContinuous(VaccineContinuousModelType modelType,  int initial, float growthFactor ){
        this.modelType = modelType;
        initialAmount = initial;
        this.growthFactor = growthFactor;
    }
    
    
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
    
    @Override
    public boolean equals( Object o ){
        boolean equalObj = false;
        if ( o instanceof VaccineAvailabilityModelContinuous ){
            VaccineAvailabilityModelContinuous vamcOther = (VaccineAvailabilityModelContinuous ) o;
            if ( initialAmount == vamcOther.initialAmount ){
                if ( modelType == vamcOther.modelType ){
                    if ( Math.abs( growthFactor - vamcOther.growthFactor) < 0.000001f ){
                        equalObj = true;
                    }
                   
                }
            }
        }
        return equalObj;
    }
    
    @Override
    public int hashCode(){
        final int MULT = 5;
        int base = Float.hashCode(growthFactor );
        base = MULT * base + Integer.hashCode( initialAmount );
        base = MULT * base + modelType.hashCode();
       return base;  
    }
    
    @Override
    public String toString(){
        StringBuilder build = new StringBuilder();
        build.append("Initial Amount:").append( initialAmount).append(";");
        build.append("Growth Factor:").append( growthFactor ).append( ";");
        build.append( "Model Type:").append( modelType ).append( ";");
        return build.toString();
    }
    
}
