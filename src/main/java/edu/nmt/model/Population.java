/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nmt.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

/**
 * Represents a community of people needing to get vaccinated.
 * @author bryce
 */
@Entity
@Table( name="population")
public class Population implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    @Column( name="chronic_medical_condition_percentage")
    private float chronicMedicalConditionPercent;
    @Column( name="increased_risk_percentage")
    private float increasedRiskPercent;
    @Column( name="severe_illness_percentage")
    private float severeIllnessPercent;
    @ElementCollection
    @CollectionTable(
        name = "RacialMixPopulation",
        joinColumns=@JoinColumn(name = "population_id", referencedColumnName = "id"))
    private final Map<RacialCategory,Float> racialMix;
    @ElementCollection
    @CollectionTable(
        name = "AgeGroupPopulation",
        joinColumns=@JoinColumn(name = "population_id", referencedColumnName = "id"))
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
    
    @Override
    public boolean equals( Object other ){
        boolean result = false;
        if ( other instanceof Population ){
            Population otherPop = (Population)other;
            final double ERR = 0.000001;
            if ( Math.abs(chronicMedicalConditionPercent - otherPop.chronicMedicalConditionPercent) < ERR ){
                if ( Math.abs( increasedRiskPercent - otherPop.increasedRiskPercent ) < ERR ){
                    if ( Math.abs( severeIllnessPercent - otherPop.severeIllnessPercent ) < ERR ){
                        if ( racialMix.equals( otherPop.racialMix)){
                            if (ageMix.equals( otherPop.ageMix)){
                                result = true;
                            }
                        }
                    }
                }
            }
        }
        return result;
    }
    
    @Override
    public int hashCode(){
        final int MULT = 5;
        int base = Float.hashCode(chronicMedicalConditionPercent );
        base = MULT * base + Float.hashCode( increasedRiskPercent );
        base = MULT * base + Float.hashCode( severeIllnessPercent );
        if ( racialMix != null ){
            base = MULT * base + racialMix.hashCode();
       }
        if ( ageMix != null ){
            base = MULT * base + ageMix.hashCode();
            
       }
       return base;       
    }
    
    @Override
    public String toString(){
        final String EOL = "\n";
        StringBuilder build = new StringBuilder();
        build.append( "Chronic Medical Condition Percent: ").append( chronicMedicalConditionPercent ).append(EOL);
        build.append( "Increased Risk Percent: ").append( increasedRiskPercent ).append( EOL );
        build.append( "Severe Illness Percent: ").append( severeIllnessPercent ).append( EOL );
        build.append( "Racial Mix: ").append( racialMix ).append( EOL );
        build.append( "Age Mix: ").append( ageMix ).append( EOL );
        return build.toString();
    }
    
    
}
