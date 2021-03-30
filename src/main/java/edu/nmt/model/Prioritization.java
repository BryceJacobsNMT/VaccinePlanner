/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nmt.model;

import edu.nmt.util.ObjectUtility;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Priority for vaccination based on various population characteristics.
 * @author bryce
 */
@Entity
@Table( name="prioritization")
public class Prioritization {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    @ElementCollection
    private final Map<Occupation, PriorityGroup> occupationPriority;
    @ElementCollection
    private final Map<IncreasedRisk, PriorityGroup> increasedRiskPriority;
    @ElementCollection
    private final Map<SevereIllness,PriorityGroup> severeIllnessPriority;
    @ElementCollection
    private final Map<RacialCategory,PriorityGroup> racialPriority;
    @ElementCollection
    private final Map<AgeGroup,PriorityGroup> agePriority;
    
    public Prioritization(){
        occupationPriority = new HashMap<>();
        increasedRiskPriority = new HashMap<>();
        severeIllnessPriority = new HashMap<>();
        racialPriority = new HashMap<>();
        agePriority = new HashMap<>();
    }
    
    public long getId(){
        return id;
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
    
    public void setId( long id ){
        this.id = id;
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
    
    @Override
    public boolean equals( Object o ){
        boolean equalObs = false;
        if ( o instanceof Prioritization ){
            Prioritization otherPrior = (Prioritization) o;
            if ( ObjectUtility.objectsAreEqual( occupationPriority, otherPrior.occupationPriority ) ){
                if ( ObjectUtility.objectsAreEqual( increasedRiskPriority, otherPrior.increasedRiskPriority )){
                    if ( ObjectUtility.objectsAreEqual( severeIllnessPriority, otherPrior.severeIllnessPriority )){
                        if ( ObjectUtility.objectsAreEqual( racialPriority, otherPrior.racialPriority )){
                            if ( ObjectUtility.objectsAreEqual( agePriority, otherPrior.agePriority )){
                                equalObs = true;
                            }
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
        int base = ObjectUtility.hashCode( occupationPriority );
        base = MULT * base + ObjectUtility.hashCode( increasedRiskPriority );
        base = MULT * base + ObjectUtility.hashCode( severeIllnessPriority );
        base = MULT * base + ObjectUtility.hashCode( racialPriority );
        base = MULT * base + ObjectUtility.hashCode( agePriority );
       return base;       
    }
}
