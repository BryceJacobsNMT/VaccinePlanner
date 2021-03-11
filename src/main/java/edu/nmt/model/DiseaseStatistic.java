/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nmt.model;


/**
 *
 * @author bryce
 */
public class DiseaseStatistic {
    private long id;
    private float infectionRate;
    private float hospitalizationRate;
    private float deathRate;
    private float spreadRate;
    
    public float getInfectionRate(){
        return infectionRate;
    }
    public float getHospitalizationRate(){
        return hospitalizationRate;
    }
    public float getDeathRate(){
        return deathRate;
    }
    public float getSpreadRate(){
        return spreadRate;
    }
}
