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
    private String name;
    
    private final static String NAME_LABEL = "Prioritization: ";
    private final static String OCC_LABEL = "Occupation Priority: ";
    private final static String RISK_LABEL = "Increased Risk Priority: ";
    private final static String SEVERE_LABEL = "Severe Illness Priority: ";
    private final static String RACIAL_LABEL = "Racial Mix Priority: ";
    private final static String AGE_LABEL = "Age Mix Priority: ";
    
    public Prioritization(){
        occupationPriority = new HashMap<>();
        initOccupationPriorityDefaults();
        increasedRiskPriority = new HashMap<>();
        initIncreasedRiskPriorityDefaults();
        severeIllnessPriority = new HashMap<>();
        initSevereIllnessPriorityDefaults();
        racialPriority = new HashMap<>();
        initRacialPriorityDefaults();
        agePriority = new HashMap<>();
        initAgePriorityDefaults();
        name = ObjectUtility.DEFAULT_NAME;
    }
    
    private void initOccupationPriorityDefaults(){
        for ( Occupation occ : Occupation.values()){
            occupationPriority.put(occ, PriorityGroup.TEN);
        }
    }
    
     private void initIncreasedRiskPriorityDefaults(){
        for ( IncreasedRisk risk : IncreasedRisk.values()){
            increasedRiskPriority.put(risk, PriorityGroup.TEN);
        }
    }
    
      private void initSevereIllnessPriorityDefaults(){
        for ( SevereIllness si : SevereIllness.values()){
            severeIllnessPriority.put(si, PriorityGroup.TEN);
        }
    }
      
      private void initRacialPriorityDefaults(){
        for ( RacialCategory rc : RacialCategory.values()){
            racialPriority.put(rc, PriorityGroup.TEN);
        }
    }
      
      private void initAgePriorityDefaults(){
        for ( AgeGroup ac : AgeGroup.values()){
            agePriority.put(ac, PriorityGroup.TEN);
        }
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
    
    public String getName(){
        return name;
    }
    
    public PriorityGroup getPriority( Person person ){
        PriorityGroup group = PriorityGroup.getDefault();
        PriorityGroup personAgePriority = agePriority.get(person.getAgeGroup() );
        if ( personAgePriority.ordinal() < group.ordinal()){
            group = personAgePriority;
        }
        PriorityGroup personOccPriority = occupationPriority.get(person.getOccupation());
        if ( personOccPriority.ordinal() < group.ordinal()){
            group = personOccPriority;
        }
        PriorityGroup severeIllPriority = severeIllnessPriority.get(person.getSevereIllness());
        if ( severeIllPriority.ordinal() < group.ordinal()){
            group = severeIllPriority;
        }
        PriorityGroup personRacePriority = this.racialPriority.get(person.getRacialCategory());
        if ( personRacePriority.ordinal() < group.ordinal()){
            group = personRacePriority;
        }
        PriorityGroup personRiskPriority = increasedRiskPriority.get(person.getIncreasedRisk());
        if ( personRiskPriority.ordinal() < group.ordinal()){
            group = personRiskPriority;
        }
        return group;
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
        initIncreasedRiskPriorityDefaults();
        increasedRiskPriority.putAll( riskPriority );
    }
    
    public void setOccupationPriority( Map<Occupation,PriorityGroup> occPriority ){
        occupationPriority.clear();
        initOccupationPriorityDefaults();
        occupationPriority.putAll( occPriority );
    }
    
    public void setSevereIllnessPriority( Map<SevereIllness,PriorityGroup> sevPriority ){
        severeIllnessPriority.clear();
        initSevereIllnessPriorityDefaults();
        severeIllnessPriority.putAll( sevPriority );
    }
    
    public void setRacialPriority( Map<RacialCategory,PriorityGroup> racGroup ){
        racialPriority.clear();
        initRacialPriorityDefaults();
        racialPriority.putAll( racGroup );
    }
    
    public void setAgePriority( Map<AgeGroup,PriorityGroup> ageGroup ){
        agePriority.clear();
        initAgePriorityDefaults();
        agePriority.putAll( ageGroup );
    }
    
    public void setName( String name ){
        this.name = name;
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
                                if ( ObjectUtility.objectsAreEqual( name, otherPrior.name )){
                                    equalObs = true;
                                }
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
        base = MULT * base + ObjectUtility.hashCode( name );
       return base;       
    }
    
    @Override
    public String toString(){
        final String EOL = "\n";
        StringBuilder build = new StringBuilder();
        build.append( NAME_LABEL ).append( name ).append( EOL );
        build.append( OCC_LABEL ).append( occupationPriority ).append(EOL);
        build.append( RISK_LABEL ).append( increasedRiskPriority ).append( EOL );
        build.append( SEVERE_LABEL).append( severeIllnessPriority ).append( EOL );
        build.append( RACIAL_LABEL).append( racialPriority ).append( EOL );
        build.append( AGE_LABEL).append( agePriority ).append( EOL );
        return build.toString();
    }
    
    /**
     * Constructs a prioritization from a textual representation.
     * @param priorString - a textual description of a prioritization.
     * @return - the corresponding Prioritization.
     */
    public static Prioritization fromString( String priorString ){
        Prioritization prior = new Prioritization();
        String[] lines = priorString.split( "\n");
        if ( lines.length == 6 ){
            prior.setName( lines[0].substring(NAME_LABEL.length(), lines[0].length()));
            
            try {
                String occStr = lines[1].substring(OCC_LABEL.length(), lines[1].length());
                String[] pairs = ObjectUtility.mapParse( occStr );
                Map<Occupation, PriorityGroup> occPriors = new HashMap<>();
                for (String pair : pairs) {
                    if ( pair.contains( "")){
                        String[] parts = pair.split("=");
                        occPriors.put(Occupation.valueOf(parts[0].trim()), PriorityGroup.valueOf(parts[1].trim()));
                    }
                }
                prior.setOccupationPriority(occPriors);
            } 
            catch (IllegalArgumentException iae) {
                System.out.println("Error parsing occupation priority: " + lines[1] + iae);
            }
           
           
            try {
                String riskStr = lines[2].substring(RISK_LABEL.length(), lines[2].length());
                String[] pairs = ObjectUtility.mapParse( riskStr );
                Map<IncreasedRisk, PriorityGroup> riskPriors = new HashMap<>();
                for (String pair : pairs) {
                    if ( pair.contains( "=")){
                        String[] parts = pair.split("=");
                        riskPriors.put(IncreasedRisk.valueOf(parts[0].trim()), PriorityGroup.valueOf(parts[1].trim()));
                    }
                }
                prior.setIncreasedRiskPriority(riskPriors);
            } 
            catch (IllegalArgumentException iae) {
                System.out.println("Error parsing increased risk priorities: " + lines[2] + iae);
            }
            
            try {
                String illStr = lines[3].substring(SEVERE_LABEL.length(), lines[3].length());
                String[] pairs = ObjectUtility.mapParse( illStr );
                Map<SevereIllness, PriorityGroup> illPriors = new HashMap<>();
                for (String pair : pairs) {
                    if ( pair.contains( "=")){
                        String[] parts = pair.split("=");
                        illPriors.put(SevereIllness.valueOf(parts[0].trim()), PriorityGroup.valueOf(parts[1].trim()));
                    }
                }
                prior.setSevereIllnessPriority(illPriors);
            } 
            catch (IllegalArgumentException iae) {
                System.out.println("Error parsing severe illness priorities: " + lines[3] + iae);
            }
            
            try {
                String raceStr = lines[4].substring(RACIAL_LABEL.length(), lines[4].length());
                String[] pairs = ObjectUtility.mapParse( raceStr );
                Map<RacialCategory, PriorityGroup> racePriors = new HashMap<>();
                for (String pair : pairs) {
                    if ( pair.contains( "")){
                        String[] parts = pair.split("=");
                        racePriors.put(RacialCategory.valueOf(parts[0].trim()), PriorityGroup.valueOf(parts[1].trim()));
                    }
                }
                prior.setRacialPriority(racePriors);
            } 
            catch (IllegalArgumentException iae) {
                System.out.println("Error parsing racial priorities: " + lines[4] + iae);
            }
            
             try {
                String ageStr = lines[5].substring(AGE_LABEL.length(), lines[5].length());
                String[] pairs = ObjectUtility.mapParse( ageStr );
                Map<AgeGroup, PriorityGroup> agePriors = new HashMap<>();
                for (String pair : pairs) {
                    if ( pair.contains( "=")){
                        String[] parts = pair.split("=");
                        agePriors.put(AgeGroup.valueOf(parts[0].trim()), PriorityGroup.valueOf(parts[1].trim()));
                    }
                }
                prior.setAgePriority(agePriors);
            } 
            catch (IllegalArgumentException iae) {
                System.out.println("Error parsing age priorities: " + lines[5] + iae);
            }
        }
        else {
            System.out.println( "Unexpected string for prioritization: "+priorString );
        }
        return prior;
    }
}
