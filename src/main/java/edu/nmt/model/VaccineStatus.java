/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nmt.model;

/**
 * Represents a person's status as to being vaccinated.
 * @author bryce
 */
public enum VaccineStatus {
    NOT_VACCINATED,
    PARTIALLY_VACCINATED,
    VACCINATED;
    
    /**
     * Returns the default vaccinated status.
     * @return - the default vaccinated status.
     */
    public static VaccineStatus getDefault(){
        return NOT_VACCINATED;
    } 
}
