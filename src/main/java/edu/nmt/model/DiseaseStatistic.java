/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nmt.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Represents how contagious a disease is for a particular population group.
 * @author bryce
 */
@Entity
@Table( name="diseasestatistic")
public class DiseaseStatistic implements Serializable {
     @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    private float infectionRate;
    private float hospitalizationRate;
    private float deathRate;
    
    private static final String DEATH_LABEL = "Death Rate: ";
    private static final String INFECT_LABEL = "Infection Rate: ";
    private static final String HOSP_LABEL = "Hospitalization Rate: ";
    
    /**
     * Returns the percentage of people who are exposed that become infected.
     * @return - the percentage of people who catch the disease after being exposed.
     */
    public float getInfectionRate(){
        return infectionRate;
    }
    
    /**
     * Returns the percentage of people who are hospitalized after being infected.
     * @return 
     */
    public float getHospitalizationRate(){
        return hospitalizationRate;
    }
    
    /**
     * Returns the percentage of people who die after being hospitalized.
     * @return 
     */
    public float getDeathRate(){
        return deathRate;
    }
    
    /**
     * Sets the percentage of people who die from the disease after being hospitalized.
     * @param deathRate - the percentage of people who die from the disease after being hospitalized.
     */
    public void setDeathRate( float deathRate ){
        this.deathRate = deathRate;
    }
    
    /**
     * Sets the percentage of infected people who are hospitalized.
     * @param hospRate - the percentage of infected people who are hospitalized.
     */
    public void setHospitalizationRate( float hospRate ){
        this.hospitalizationRate = hospRate;
    }
    
    /**
     * Sets the percentage of people each infected person passes the disease to.
     * @param infectRate - the disease infection rate.
     */
    public void setInfectionRate( float infectRate ){
        this.infectionRate = infectRate;
    }
    
    @Override
    public boolean equals( Object o ){
        boolean equalObs = false;
        if ( o instanceof DiseaseStatistic ){
            DiseaseStatistic otherStat  = (DiseaseStatistic )o;
            final float ERR = 0.000001f;
            if ( Math.abs( infectionRate - otherStat.infectionRate) < ERR ){
                if ( Math.abs( hospitalizationRate - otherStat.hospitalizationRate ) < ERR ){
                    if ( Math.abs( deathRate - otherStat.deathRate ) < ERR ){
                        equalObs = true;
                    }
                }
            }
        }
        return equalObs;
    }
    
    @Override
    public int hashCode(){
        final int MULT = 5;
        int base = Float.hashCode(deathRate );
        base = MULT * base + Float.hashCode( hospitalizationRate );
        base = MULT * base + Float.hashCode( infectionRate );
       return base;       
    }
    
    @Override
    public String toString(){
        StringBuilder build = new StringBuilder();
        build.append("[");
        final String COLON = "; ";
        build.append( INFECT_LABEL).append(infectionRate).append( COLON );
        build.append( HOSP_LABEL ).append(hospitalizationRate ).append( COLON );
        build.append( DEATH_LABEL ).append( deathRate ).append( "]");
        return build.toString();
    }
    
    public static DiseaseStatistic getDefault(){
        DiseaseStatistic stat = new DiseaseStatistic();
        stat.setInfectionRate( 0.02f);
        stat.setHospitalizationRate( 0.15f);
        stat.setDeathRate(0.25f);
        return stat;
    }
    
    /**
     * Constructs a disease statistic from a textual representation.
     * @param statString - a textual description of a disease statistic.
     * @return - the corresponding DiseaseStatistic.
     */
    public static DiseaseStatistic fromString( String statString ){
        DiseaseStatistic stat = new DiseaseStatistic();
        statString = statString.replaceAll( "\\[", "");
        statString = statString.replaceAll( "\\]", "");
        String[] lines = statString.split( "; ");
        
        if ( lines.length == 3 ){
            
            try {
                stat.setInfectionRate( Float.parseFloat(lines[0].substring( INFECT_LABEL.length(),lines[0].length()).trim()));
            }
            catch( NumberFormatException nfe ){
                System.out.println( "Could not infection rate: "+lines[0]);
            }
            
            try {
                stat.setHospitalizationRate( Float.parseFloat(lines[1].substring( HOSP_LABEL.length(),lines[1].length()).trim()));
            }
            catch( NumberFormatException nfe ){
                System.out.println( "Could not hospitalization rate: "+lines[1]);
            }
            
            try {
                stat.setDeathRate( Float.parseFloat(lines[2].substring( DEATH_LABEL.length(),lines[2].length()).trim()));
            }
            catch( NumberFormatException nfe ){
                System.out.println( "Could not death rate: "+lines[2]);
            }
        }
        else {
            System.out.println( "Could not parse disease statistic: "+statString);
        }
        return stat;
    }
}
