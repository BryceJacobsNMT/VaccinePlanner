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
public enum VaccineStatus {
    NOT_VACCINATED,
    PARTIALLY_VACCINATED,
    VACCINATED;
    
    public static VaccineStatus getDefault(){
        return NOT_VACCINATED;
    } 
}
