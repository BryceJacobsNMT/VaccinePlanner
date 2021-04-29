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
    
    
    public DailyInfectionStatus( ){
    }
    
    public int getDeathCount(){
        return deathCount;
    }
    
    public int getHospCount(){
        return hospCount;
    }
    
    public int getInfectionCount(){
        return infectionCount;
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
    
    @Override
    public String toString(){
        StringBuilder build = new StringBuilder();
        build.append( "Dead: ").append(deathCount).append(", Hospitalized: ").append(hospCount).append(", Infected: ").append(infectionCount);
        return build.toString();
    }
}
