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
    private String name;
    
    private final static String POP_LABEL = "Population: ";
    private final static String CHRON_LABEL = "Chronic Medical Condition Percent: ";
    private final static String RISK_LABEL = "Increased Risk Percent: ";
    private final static String SEVERE_LABEL = "Severe Illness Percent: ";
    private final static String RACIAL_LABEL = "Racial Mix: ";
    private final static String AGE_LABEL = "Age Mix: ";
    
    /**
     * Constructor.
     */
    public Population(){
        racialMix = new HashMap<>();
        ageMix = new HashMap<>();
        name = ObjectUtility.DEFAULT_NAME;
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
    
    public String getName(){
        return name;
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
    
    public void setName( String name ){
        this.name = name;
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
                                if ( ObjectUtility.objectsAreEqual(name, otherPop.name)){
                                    result = true;
                                }
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
       
       base = MULT * base + ObjectUtility.hashCode(name);
       return base;       
    }
    
    @Override
    public String toString(){
        final String EOL = "\n";
        StringBuilder build = new StringBuilder();
        build.append( POP_LABEL ).append( name ).append( EOL );
        build.append( CHRON_LABEL ).append( chronicMedicalConditionPercent ).append(EOL);
        build.append( RISK_LABEL ).append( increasedRiskPercent ).append( EOL );
        build.append( SEVERE_LABEL).append( severeIllnessPercent ).append( EOL );
        build.append( RACIAL_LABEL).append( racialMix ).append( EOL );
        build.append( AGE_LABEL).append( ageMix ).append( EOL );
        return build.toString();
    }
    
    /**
     * Constructs a population from a textual representation.
     * @param popString - a textual description of a population.
     * @return - the corresponding Population.
     */
    public static Population fromString( String popString ){
        Population pop = new Population();
        String[] lines = popString.split( "\n");
        if ( lines.length == 6 ){
            pop.setName( lines[0].substring(POP_LABEL.length(), lines[0].length()));
            try {
                pop.setChronicMedicalConditionPercent( Float.parseFloat(lines[1].substring( CHRON_LABEL.length(),lines[1].length())));
            }
            catch( NumberFormatException nfe ){
                System.out.println( "Could not parse chronic medical condition: "+lines[1]);
            }
            try {
                pop.setIncreasedRiskPercent( Float.parseFloat(lines[2].substring( RISK_LABEL.length(),lines[2].length())));
            }
            catch( NumberFormatException nfe ){
                System.out.println( "Could not parse increased risk percent: "+lines[2]);
            }
            try {
                pop.setSevereIllnessPercent( Float.parseFloat(lines[3].substring( SEVERE_LABEL.length(),lines[3].length())));
            }
            catch( NumberFormatException nfe ){
                System.out.println( "Could not parse severe illness percent: "+lines[3]);
            }
           
            try {
                String racialStr = lines[4].substring(RACIAL_LABEL.length(), lines[4].length());
                String[] pairs = ObjectUtility.mapParse( racialStr );
                Map<RacialCategory, Float> rMix = new HashMap<>();
                for (String pair : pairs) {
                    if ( pair.contains( "=")){
                        String[] parts = pair.split("=");
                        rMix.put(RacialCategory.valueOf(parts[0].trim()), Float.parseFloat(parts[1].trim()));
                    }
                }
                pop.setRacialMix(rMix);
            } 
            catch (IllegalArgumentException iae) {
                System.out.println("Error parsing racial mix: " + lines[4] + iae);
            }
            
            try {
                String ageStr = lines[5].substring(AGE_LABEL.length(), lines[5].length());
                String[] pairs = ObjectUtility.mapParse( ageStr );
                Map<AgeGroup, Float> ageMix = new HashMap<>();
                for (String pair : pairs) {
                     if ( pair.contains( "=")){
                        String[] parts = pair.split("=");
                        ageMix.put(AgeGroup.valueOf(parts[0].trim()), Float.parseFloat(parts[1].trim()));
                     }
                }
                pop.setAgeMix(ageMix);
            } 
            catch (IllegalArgumentException iae) {
                System.out.println("Error parsing age mix: " + lines[5] + iae);
            }
        }
        else {
            System.out.println( "Unexpected string for population: "+popString+" line count="+lines.length );
        }
        return pop;
    }
    
    
}
