/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nmt.model;

import edu.nmt.util.ObjectUtility;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
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
    @ElementCollection
    @CollectionTable(
    name = "OccupationPopulation",
    joinColumns=@JoinColumn(name = "population_id", referencedColumnName = "id"))
    private final Map<Occupation,Float> occupationMix;
    private String name;
    
    private final static String POP_LABEL = "Population: ";
    private final static String OCC_LABEL = "Occupation: ";
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
        occupationMix = new HashMap<>();
        name = ObjectUtility.DEFAULT_NAME;
    }
    
    public void initialize( List<Person> people ){
        Random rand = new Random();
        
        //Initialize race
        Set<RacialCategory> cats = racialMix.keySet();
        for ( RacialCategory cat : cats ){
            int peopleCount = (int)(racialMix.get(cat) * people.size());
            if ( peopleCount <= people.size()){
                int initCount = 0;
                while ( initCount < peopleCount ){
                    int personIndex = rand.nextInt( people.size() );
                    if ( people.get(personIndex).getRacialCategory() == RacialCategory.getDefault() ){
                        people.get(personIndex).setRacialCategory( cat );
                        initCount++;
                    }
                }
            }
            else {
                System.out.println( "Could not initialize racial category "+cat+"; invalid percent: "+racialMix.get(cat));
            }
        }
        
         //Initialize age
        Set<AgeGroup> ages = ageMix.keySet();
        int occupationCount = 0;
        for ( AgeGroup age : ages ){
            int peopleCount = (int)(ageMix.get(age) * people.size());
            if ( peopleCount <= people.size()){
                int initCount = 0;
                while ( initCount < peopleCount ){
                    int personIndex = rand.nextInt( people.size() );
                    if ( people.get(personIndex).getAgeGroup() == AgeGroup.getDefault() ){
                        people.get(personIndex).setAgeGroup( age );
                        initCount++;
                        if ( people.get(personIndex).isEmployable() ){
                            occupationCount++;
                        }
                    }
                }
            }
            else {
                System.out.println( "Could not initialize age group "+age+"; invalid percent: "+ageMix.get(age));
            }
        }
        
        //Initialize increasedRisk
        int peopleCount = (int)( this.increasedRiskPercent * people.size());
        if (peopleCount <= people.size()) {
            int initCount = 0;
            while (initCount < peopleCount) {
                int personIndex = rand.nextInt(people.size());
                if (people.get(personIndex).getIncreasedRisk() == IncreasedRisk.getDefault()) {
                    IncreasedRisk[] values = IncreasedRisk.values();
                    int riskBound = values.length;
                    people.get(personIndex).setIncreasedRisk(values[rand.nextInt(riskBound)]);
                    initCount++;
                }
            }
        }
        else {
            System.out.println( "Could not initialize increased risk people; invalid percent: "+this.increasedRiskPercent );
        }
        
        //Initialize occupation.
        //Only teenages and above will have occupations.
        Set<Occupation> occs = occupationMix.keySet();
        for ( Occupation occ : occs ){
            peopleCount = (int)(occupationMix.get(occ) * occupationCount);
            if ( peopleCount <= occupationCount){
                int initCount = 0;
                while ( initCount < peopleCount ){
                    int personIndex = rand.nextInt( people.size() );
                    if ( people.get(personIndex).getOccupation() == Occupation.getDefault() ){
                        if ( people.get(personIndex).isEmployable()){
                            people.get(personIndex).setOccupation( occ );
                            initCount++;
                        }
                    }
                }
            }
            else {
                System.out.println( "Could not initialize occuation invalid percent: "+occupationMix.get(occ));
            }
        }
       
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
    
    public Map<Occupation,Float> getOccupationMix(){
        return this.occupationMix;
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
    
    public void setOccupationMix( Map<Occupation,Float> occMix ){
        this.occupationMix.clear();
        this.occupationMix.putAll(occMix );
    }
  
    public void setRacialMix( Map<RacialCategory,Float> racialMix ){
         this.racialMix.clear();
        this.racialMix.putAll( racialMix );
    }
    
    @Override
    public boolean equals(Object other) {
        boolean result = false;
        if (other instanceof Population) {
            Population otherPop = (Population) other;
            final double ERR = 0.000001;
            if (Math.abs(increasedRiskPercent - otherPop.increasedRiskPercent) < ERR) {
                if (Math.abs(severeIllnessPercent - otherPop.severeIllnessPercent) < ERR) {
                    if (racialMix.equals(otherPop.racialMix)) {
                        if (ageMix.equals(otherPop.ageMix)) {
                            if (occupationMix.equals(otherPop.occupationMix)) {
                                if (ObjectUtility.objectsAreEqual(name, otherPop.name)) {
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
    public int hashCode() {
        final int MULT = 5;
        int base = Float.hashCode(increasedRiskPercent);
        base = MULT * base + Float.hashCode(severeIllnessPercent);
        base = MULT * base + ObjectUtility.hashCode(racialMix);
        base = MULT * base + ObjectUtility.hashCode(ageMix);
        base = MULT * base + ObjectUtility.hashCode( occupationMix );
        base = MULT * base + ObjectUtility.hashCode(name);
        return base;
    }
    
    @Override
    public String toString(){
        final String EOL = "\n";
        StringBuilder build = new StringBuilder();
        build.append( POP_LABEL ).append( name ).append( EOL );
        build.append( RISK_LABEL ).append( increasedRiskPercent ).append( EOL );
        build.append( SEVERE_LABEL).append( severeIllnessPercent ).append( EOL );
        build.append( RACIAL_LABEL).append( racialMix ).append( EOL );
        build.append( AGE_LABEL).append( ageMix ).append( EOL );
        build.append( OCC_LABEL).append( occupationMix).append(EOL);
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
                pop.setIncreasedRiskPercent( Float.parseFloat(lines[1].substring( RISK_LABEL.length(),lines[1].length())));
            }
            catch( NumberFormatException nfe ){
                System.out.println( "Could not parse increased risk percent: "+lines[1]);
            }
            try {
                pop.setSevereIllnessPercent( Float.parseFloat(lines[2].substring( SEVERE_LABEL.length(),lines[2].length())));
            }
            catch( NumberFormatException nfe ){
                System.out.println( "Could not parse severe illness percent: "+lines[2]);
            }
           
            try {
                String racialStr = lines[3].substring(RACIAL_LABEL.length(), lines[3].length());
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
                System.out.println("Error parsing racial mix: " + lines[3] + iae);
            }
            
            try {
                String ageStr = lines[4].substring(AGE_LABEL.length(), lines[4].length());
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
                System.out.println("Error parsing age mix: " + lines[4] + iae);
            }
            
            try {
                String popStr = lines[5].substring(OCC_LABEL.length(), lines[5].length());
                String[] pairs = ObjectUtility.mapParse( popStr );
                Map<Occupation, Float> popMix = new HashMap<>();
                for (String pair : pairs) {
                     if ( pair.contains( "=")){
                        String[] parts = pair.split("=");
                        popMix.put(Occupation.valueOf(parts[0].trim()), Float.parseFloat(parts[1].trim()));
                     }
                }
                pop.setOccupationMix(popMix);
            } 
            catch (IllegalArgumentException iae) {
                System.out.println("Error parsing occupation mix: " + lines[5] + iae);
            }
        }
        else {
            System.out.println( "Unexpected string for population: "+popString+" line count="+lines.length );
        }
        return pop;
    }
    
    
}
