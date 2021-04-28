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
    private String name;
    
    private final static String NAME_LABEL = "Disease: ";
    private final static String OCC_LABEL = "Occupation Disease Statistics: ";
    private final static String RISK_LABEL = "Increased Risk Disease Statistics: ";
    private final static String SEVERE_LABEL = "Severe Illness Disease Statistics: ";
    private final static String RACIAL_LABEL = "Racial Mix Disease Statistics: ";
    private final static String AGE_LABEL = "Age Disease Statistics: ";
    
    public Disease(){       
        occupationDisease = new HashMap<>();
        increasedRiskDisease = new HashMap<>();
        severeIllnessDisease = new HashMap<>();
        racialDisease = new HashMap<>();
        ageDisease = new HashMap<>();
        name = ObjectUtility.DEFAULT_NAME;
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
    
    public String getName(){
        return name;
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
    
    public void setName( String name ){
        this.name = name;
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
                            if ( ObjectUtility.objectsAreEqual( name, otherDisease.name)){
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
        int base = ObjectUtility.hashCode( ageDisease );
        base = MULT * base + ObjectUtility.hashCode( increasedRiskDisease );
        base = MULT * base + ObjectUtility.hashCode( occupationDisease );
        base = MULT * base + ObjectUtility.hashCode( racialDisease );
        base = MULT * base + ObjectUtility.hashCode( name );
       return base;  
    }
    
    @Override
    public String toString(){
        final String EOL = "\n";
        StringBuilder build = new StringBuilder();
        build.append( NAME_LABEL ).append( name ).append( EOL );
        build.append( AGE_LABEL ).append( ageDisease ).append(EOL);
        build.append( RISK_LABEL ).append( increasedRiskDisease ).append( EOL );
        build.append( OCC_LABEL).append( occupationDisease ).append( EOL );
        build.append( RACIAL_LABEL).append( racialDisease ).append( EOL );
        return build.toString();
    }
    
    /**
     * Constructs disease statistics from a textual representation.
     * @param disString - a textual description of disease statistics.
     * @return - the corresponding Disease.
     */
    public static Disease fromString( String disString ){
        Disease disease = new Disease();
        String[] lines = disString.split( "\n");
        if ( lines.length == 5 ){
            disease.setName( lines[0].substring(NAME_LABEL.length(), lines[0].length()));
            
            try {
                String ageStr = lines[1].substring(AGE_LABEL.length(), lines[1].length());
                System.out.println( "ageStr-"+ageStr);
                String[] pairs = ObjectUtility.mapParse( ageStr );
                System.out.println( "Age pairs are "+pairs.length+" first="+pairs[0]);
                Map<AgeGroup, DiseaseStatistic> ageStats = new HashMap<>();
                for (String pair : pairs) {
                    if ( pair.contains( "=")){
                        String[] parts = pair.split("=");
                        ageStats.put(AgeGroup.valueOf(parts[0].trim()), DiseaseStatistic.fromString(parts[1].trim()));
                    }
                }
                disease.setAgeDisease(ageStats);
            } 
            catch (IllegalArgumentException iae) {
                System.out.println("Error parsing age disease statistics: " + lines[1] + iae);
            }
            
            try {
                String riskStr = lines[2].substring(RISK_LABEL.length(), lines[2].length());
                System.out.println( "Risk str="+riskStr);
                String[] pairs = ObjectUtility.mapParse( riskStr );
                Map<IncreasedRisk, DiseaseStatistic> riskStats = new HashMap<>();
                for (String pair : pairs) {
                    if ( pair.contains( "=")){
                        String[] parts = pair.split("=");
                        riskStats.put(IncreasedRisk.valueOf(parts[0].trim()), DiseaseStatistic.fromString(parts[1].trim()));
                    }
                }
                disease.setIncreasedRiskDisease(riskStats);
            } 
            catch (IllegalArgumentException iae) {
                System.out.println("Error parsing increased risk disease statistics: " + lines[2] + iae);
            }
            
            try {
                String occStr = lines[3].substring(OCC_LABEL.length(), lines[3].length());
                String[] pairs = ObjectUtility.mapParse( occStr );
                Map<Occupation, DiseaseStatistic> occStats = new HashMap<>();
                for (String pair : pairs) {
                    if ( pair.contains( "=")){
                        String[] parts = pair.split("=");
                        occStats.put(Occupation.valueOf(parts[0].trim()), DiseaseStatistic.fromString(parts[1].trim()));
                    }
                }
                disease.setOccupationDisease(occStats);
            } 
            catch (IllegalArgumentException iae) {
                System.out.println("Error parsing occupation disease statistics: " + lines[3] + iae);
            }
            
            try {
                String raceStr = lines[4].substring(RACIAL_LABEL.length(), lines[4].length());
                String[] pairs = ObjectUtility.mapParse( raceStr );
                Map<RacialCategory, DiseaseStatistic> disStats = new HashMap<>();
                for (String pair : pairs) {
                    if ( pair.contains( "=")){
                        String[] parts = pair.split("=");
                        disStats.put(RacialCategory.valueOf(parts[0].trim()), DiseaseStatistic.fromString(parts[1].trim()));
                    }
                }
                disease.setRacialDisease(disStats);
            } 
            catch (IllegalArgumentException iae) {
                System.out.println("Error parsing racial disease statistics: " + lines[4] + iae);
            }     
        }
        else {
            System.out.println( "Unexpected string for disease statistics: "+disString );
        }
        return disease;
    }
}
