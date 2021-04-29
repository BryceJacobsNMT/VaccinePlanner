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
public enum RacialCategory {
    WHITE ("White American, European American, or Middle Eastern American", 0),
    BLACK ("Black or African American", 0),
    INDIAN ("American Indian or Alaska Native", 0),
    ASIAN( "Asian American", 0),
    PACIFIC_ISLANDER ("Native Hawaiian or Other Pacific Islander", 0),
    OTHER( "Other", 0);
    
    private final String description;
    private final int contactRateAdjustment;
    
    private RacialCategory( String descript, int adjust ){
        description = descript;
        contactRateAdjustment = adjust;
    }
    
    public static RacialCategory getDefault(){
        return OTHER;
    }
    
    public int getContactCountAdjustment(){
        return contactRateAdjustment;
    }
}
