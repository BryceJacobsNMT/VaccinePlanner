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
public class Disease {
    private long id;
    private final Map<Occupation,DiseaseStatistic> occupationDisease;
    private final Map<IncreasedRisk,DiseaseStatistic> increasedRiskDisease;
    private final Map<SevereIllness,DiseaseStatistic> severeIllnessDisease;
    private final Map<RacialCategory,DiseaseStatistic> racialDisease;
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
}
