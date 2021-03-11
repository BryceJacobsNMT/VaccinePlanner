/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nmt.model;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Represents a community of people needing to get vaccinated.
 * @author bryce
 */
@Entity
@Table( name="population")
public class Population {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    private float chronicMedicalConditionPercent;
    private float increasedRiskPercent;
    private float severeIllnessPercent;
    private final Map<RacialCategory,Float> racialMix;
    private final Map<AgeGroup,Float> ageMix;
    
    /**
     * Constructor.
     */
    public Population(){
        racialMix = new HashMap<>();
        ageMix = new HashMap<>();
    }
    
    /**
     * Returns a number in [0,1] representing the percentage of the population with a
     * chronic medical condition.
     * @return - the chronic medical condition percentage.
     */
    public float getChronicMedicalConditionPercent(){
        return chronicMedicalConditionPercent;
    }
    
    public long getId(){
        return this.id;
    }
    
    public float getIncreasedRiskPercent(){
        return increasedRiskPercent;
    }
    
    public float getSevereIllnessPercent(){
        return severeIllnessPercent;
    }
    
    public Map<AgeGroup,Float> getAgeMix(){
        return ageMix;
    }
    
    public Map<RacialCategory,Float> getRacialMix(){
        return this.racialMix;
    }
    
    public void setChronicMedicalConditionPercent( float percent ){
        chronicMedicalConditionPercent = percent;
    }
    
    public void setId( long id){
        this.id = id;
    }
    
    public void setIncreasedRiskPercent( float percent ){
        increasedRiskPercent = percent;
    }
    
    public void setSevereIllnessPercent( float percent ){
        severeIllnessPercent = percent;
    }
    
    public void setAgeMix( Map<AgeGroup,Float> ageMix ){
        this.ageMix.clear();
        this.ageMix.putAll( ageMix );
    }
    
    public void setRacialMix( Map<RacialCategory,Float> racialMix ){
         this.racialMix.clear();
        this.racialMix.putAll( racialMix );
    }
    
    
}
