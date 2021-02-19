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
public enum AgeGroup {
    LESS_THAN_YEAR( "Less than 1 year"),
    CHILD("1-14 years"),
    YOUNG_ADULT( "15-24 years"),
    ADULT( "25-44 years"),
    MIDDLE_ADULT( "45-64"),
    OLD_ADULT( "65-84"),
    OLDER_ADULT("85+ years");
    
    private final String description;
    
    private AgeGroup( String descript){
        description = descript;
    }
    
}
