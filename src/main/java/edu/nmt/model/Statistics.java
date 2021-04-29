/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nmt.model;

/**
 * Produces summary statistics for the result.
 * @author bryce
 */
public class Statistics {
    
    private float totalInfections;
    private int dayCount;
    private float totalHospitalizations;
    private float totalDeaths;
    private float totalPopulation;
    
    public Statistics( DailyInfectionStatus[] stats ){
        computeStatistics( stats );
    }
    
    private void computeStatistics( DailyInfectionStatus[] stats ){
        for ( int i = 0; i < stats.length; i++ ){
            
        }
    }
}
