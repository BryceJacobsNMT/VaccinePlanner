/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nmt.model;

import java.util.Map;

/**
 *
 * @author bryce
 */
public enum VaccineContinuousModelType {
    LINEAR( "Linear"){
        @Override
        public int getDoses( int elapsedDays, int initialAmount, float growthRate ){
            return (int)( initialAmount + growthRate * elapsedDays);
        }
    },
    EXPONENTIAL("Exponential"){
        @Override
        public int getDoses( int elapsedDays, int initialAmount, float growthRate ){
            return (int)(initialAmount * Math.exp(growthRate * elapsedDays));
        }
    };
    
    private final String modelName;
    
    private VaccineContinuousModelType( String name){
        modelName = name;
    }
    
    public abstract int getDoses( int elapsedDays, int initialAmount, float growthRate );
    
}
