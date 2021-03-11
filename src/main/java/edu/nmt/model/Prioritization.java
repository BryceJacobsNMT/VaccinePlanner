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
public class Prioritization {
    private final Map<Occupation, PriorityGroup> occupationPriority;
    private final Map<IncreasedRisk, PriorityGroup> increasedRiskPriority;
    private final Map<SevereIllness,PriorityGroup> severeIllnessPriority;
    private final Map<RacialCategory,PriorityGroup> racialPriority;
    private final Map<AgeGroup,PriorityGroup> agePriority;
    
    public Prioritization(){
        occupationPriority = new HashMap<>();
        increasedRiskPriority = new HashMap<>();
        severeIllnessPriority = new HashMap<>();
        racialPriority = new HashMap<>();
        agePriority = new HashMap<>();
    }
    
    public Map<AgeGroup,PriorityGroup> getAgePriority(){
        return agePriority;
    }
    
    public Map<IncreasedRisk,PriorityGroup> getIncreasedRiskPriority(){
        return increasedRiskPriority;
    }
    
    public Map<Occupation,PriorityGroup> getOccupationPriority(){
        return occupationPriority;
    }
    
    public Map<SevereIllness,PriorityGroup> getSevereIllnessPriority(){
        return severeIllnessPriority;
    }
    
    public void setIncreasedRiskPriority( Map<IncreasedRisk,PriorityGroup> riskPriority ){
        increasedRiskPriority.clear();
        increasedRiskPriority.putAll( riskPriority );
    }
    
    public void setOccupationPriority( Map<Occupation,PriorityGroup> occPriority ){
        occupationPriority.clear();
        occupationPriority.putAll( occPriority );
    }
    
    public void setSevereIllnessPriority( Map<SevereIllness,PriorityGroup> sevPriority ){
        severeIllnessPriority.clear();
        severeIllnessPriority.putAll( sevPriority );
    }
    
    public void setRacialPriority( Map<RacialCategory,PriorityGroup> racGroup ){
        racialPriority.clear();
        racialPriority.putAll( racGroup );
    }
    
    public void setAgePriority( Map<AgeGroup,PriorityGroup> ageGroup ){
        agePriority.clear();
        agePriority.putAll( ageGroup );
    }
}
