/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nmt.model;

/**
 * Possible status of the disease in a person
 * @author bryce
 */
public enum DiseaseStatus {
    NOT_EXPOSED,
    INFECTED,
    HOSPITALIZED,
    RECOVERED,
    DEAD;
    
    public static DiseaseStatus getDefault(){
        return NOT_EXPOSED;
    }
}
