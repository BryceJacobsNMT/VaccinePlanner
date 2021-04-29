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
public enum IncreasedRisk {
    ASTHMA(0),
    CEREBROVASCULAR_DISEASE(-10),
    HYPERTENSION(0),
    CYSTIC_FIBROSIS(-10),
    IMMUNOCOMPROMISED(-10),
    LIVER_DISEASE(-10),
    NEUROTOLOGIC_CONDITION(-10),
    OVERWEIGHT(0),
    PULMONARY_FIBROSIS(-10),
    THALASSEMIA(-10),
    TYPE_1_DIABETES(0),
    NONE(0);
    
    private int contactCountAdjustment;
    
    private IncreasedRisk( int adjust){
        contactCountAdjustment = adjust;
    }
    
    public static IncreasedRisk getDefault(){
        return NONE;
    }
    
    public int getContactCountAdjustment(){
        return contactCountAdjustment;
    }
}
