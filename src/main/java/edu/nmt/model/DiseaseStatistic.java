/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nmt.model;

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
@Table( name="diseasestatistic")
public class DiseaseStatistic {
     @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
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
    
    public void setDeathRate( float deathRate ){
        this.deathRate = deathRate;
    }
    
    public void setHospitalizationRate( float hospRate ){
        this.hospitalizationRate = hospRate;
    }
    
    public void setInfectionRate( float infectRate ){
        this.infectionRate = infectRate;
    }
    
    public void setSpreadRate( float spreadRate ){
        this.spreadRate = spreadRate;
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
                        if ( Math.abs( spreadRate - otherStat.spreadRate ) < ERR ){
                            equalObs = true;
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
        int base = Float.hashCode(deathRate );
        base = MULT * base + Float.hashCode( hospitalizationRate );
        base = MULT * base + Float.hashCode( infectionRate );
        base = MULT * base + Float.hashCode( spreadRate );
       return base;       
    }
}
