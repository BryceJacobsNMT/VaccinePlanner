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
@Table( name="disease")
public class Disease implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    @ElementCollection
    private final Map<Occupation,DiseaseStatistic> occupationDisease;
    @ElementCollection
    private final Map<IncreasedRisk,DiseaseStatistic> increasedRiskDisease;
    @ElementCollection
    private final Map<SevereIllness,DiseaseStatistic> severeIllnessDisease;
    @ElementCollection
    private final Map<RacialCategory,DiseaseStatistic> racialDisease;
    @ElementCollection
    private final Map<AgeGroup,DiseaseStatistic> ageDisease;
    
    public Disease(){
        
        occupationDisease = new HashMap<>();
        increasedRiskDisease = new HashMap<>();
        severeIllnessDisease = new HashMap<>();
        racialDisease = new HashMap<>();
        ageDisease = new HashMap<>();
    }
    
    public Map<AgeGroup,DiseaseStatistic> getAgeDisease(){
        return ageDisease;
    }
    
    public long getId(){
        return id;
    }
    
    public Map<IncreasedRisk,DiseaseStatistic> getIncreasedRiskDisease(){
        return increasedRiskDisease;
    }
    
    public Map<Occupation,DiseaseStatistic> getOccupationDisease(){
        return occupationDisease;
    }
    
    public Map<SevereIllness,DiseaseStatistic> getSevereIllnessDisease(){
        return severeIllnessDisease;
    }
    
    public Map<RacialCategory, DiseaseStatistic> getRacialDisease(){
        return racialDisease;
    }
    
    public void setAgeDisease( Map<AgeGroup,DiseaseStatistic> ageMap ){
        ageDisease.clear();
        ageDisease.putAll( ageMap );
    }
    
    public void setId( long id ){
        this.id = id;
    }
    
    public void setIncreasedRiskDisease( Map<IncreasedRisk,DiseaseStatistic> riskMap ){
        increasedRiskDisease.clear();
        increasedRiskDisease.putAll( riskMap );
    }
    
    public void setOccupationDisease( Map<Occupation,DiseaseStatistic> occupMap){
        occupationDisease.clear();
        occupationDisease.putAll( occupMap );
    }
    
    public void setRacialDisease( Map<RacialCategory, DiseaseStatistic> racialMap ){
        racialDisease.clear();
        racialDisease.putAll( racialMap );
    }
    
    public void setSevereIllnessDisease( Map<SevereIllness,DiseaseStatistic> diseaseMap){
        severeIllnessDisease.clear();
        severeIllnessDisease.putAll( diseaseMap );
    }
    
    @Override
    public boolean equals( Object o ){
        boolean equalObs = false;
        if ( o instanceof Disease ){
            Disease otherDisease = (Disease) o;
            if ( ObjectUtility.objectsAreEqual( ageDisease, otherDisease.ageDisease)){
                if ( ObjectUtility.objectsAreEqual( increasedRiskDisease, otherDisease.increasedRiskDisease)){
                    if ( ObjectUtility.objectsAreEqual( occupationDisease, otherDisease.occupationDisease)){
                        if ( ObjectUtility.objectsAreEqual( racialDisease, otherDisease.racialDisease)){
                            equalObs = true;
                        }
                    }
                }
                
            }
        }
        return equalObs;
    }
    
    @Override
    public int hashCode(){
        final int MULT = 5;
        int base = ObjectUtility.hashCode( ageDisease );
        base = MULT * base + ObjectUtility.hashCode( increasedRiskDisease );
        base = MULT * base + ObjectUtility.hashCode( occupationDisease );
        base = MULT * base + ObjectUtility.hashCode( racialDisease );
       return base;  
    }
}
