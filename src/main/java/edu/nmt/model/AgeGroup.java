/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nmt.model;

/**
 * Groups of ages for people.
 * @author bryce
 */
public enum AgeGroup {
    LESS_THAN_YEAR( "Less than 1 year", -5),
    CHILD("1-14 years", 5),
    YOUNG_ADULT( "15-24 years", 10),
    ADULT( "25-44 years", 10),
    MIDDLE_ADULT( "45-64", 5),
    OLD_ADULT( "65-84", 0),
    OLDER_ADULT("85+ years", -10);
    
    private final String description;
    private final int contactCountAdjustment;
    
    private AgeGroup( String descript, int adjustment){
        description = descript;
        contactCountAdjustment = adjustment;
    }
    
    public static AgeGroup getDefault(){
        return ADULT;
    }
    
    public int getContactCountAdjustment(){
        return contactCountAdjustment;
    }
    
}
