/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nmt.model;

/**
 * Stores the infection status for a single day.
 * @author bryce
 */
public class DailyInfectionStatus {
    private int infectionCount;
    private int hospCount;
    private int deathCount;
    private int vaccinatedCount;
    private int partiallyVaccinatedCount;
    private int recoveredCount;
    
    /**
     * Constructor.
     */
    public DailyInfectionStatus( ){
    }
    
    /**
     * Returns the number of deaths in the day.
     * @return - the number of deaths in the day.
     */
    public int getDeathCount(){
        return deathCount;
    }
    
    /**
     * Returns the number of hospitalizations in the day.
     * @return - the number of hosptializations in the day.
     */
    public int getHospCount(){
        return hospCount;
    }
    
    public int getInfectionCount(){
        return infectionCount;
    }
    
    public int getPartiallyVaccinatedCount(){
        return partiallyVaccinatedCount;
    }
    
    public int getRecoveredCount(){
        return recoveredCount;
    }
    
    public int getVaccinatedCount(){
        return vaccinatedCount;
    }
    
    public void setInfectionCount( int count ){
        infectionCount = count;
    }
    
    public void setHospCount( int count ){
        hospCount = count;
    }
    
    public void setDeathCount( int count ){
        deathCount = count;
    }
    
    public void setPartiallyVaccinatedCount( int count ){
        partiallyVaccinatedCount = count;
    }
    
    public void setRecoveredCount( int count ){
        recoveredCount = count;
    }
    
    public void setVaccinatedCount( int count ){
        vaccinatedCount = count;
    }
    
    @Override
    public String toString(){
        StringBuilder build = new StringBuilder();
        build.append( "Dead: ").append(deathCount).append(", Hospitalized: ").append(hospCount).append(", Infected: ").append(infectionCount);
        build.append( "Vaccinated: ").append( vaccinatedCount).append( " Partially Vaccinated").append( partiallyVaccinatedCount );
        return build.toString();
    }
}
