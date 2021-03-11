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
    WHITE ("White American, European American, or Middle Eastern American"),
    BLACK ("Black or African American"),
    INDIAN ("American Indian or Alaska Native"),
    ASIAN( "Asian American"),
    PACIFIC_ISLANDER ("Native Hawaiian or Other Pacific Islander"),
    OTHER( "Other");
    
    private final String description;
    
    private RacialCategory( String descript ){
        description = descript;
    }
}
