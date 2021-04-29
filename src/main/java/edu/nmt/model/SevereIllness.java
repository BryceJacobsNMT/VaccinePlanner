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
public enum SevereIllness {
    CANCER(5),
    CHRONIC_KIDNEY_DISEASE(-5),
    CHRONIC_OBSTRUCTIVE_PULMONARY_DISEASE(-5),
    INTELLECTUAL_DEVELOPMENTAL_DISABILITIES(-5),
    HEART_CONDITIONS(-5),
    IMMUNOCOMPROMISED_TRANSPLANT(-5),
    OBESITY(0),
    SEVERE_OBESITY(0),
    PREGNANCY(0),
    SICKLE_CELL_DISEASE(-5),
    SMOKING(0),
    TYPE_2_DIABETES(0),
    NONE(0);
    
    private final int contactAdjustment;
    
    private SevereIllness( int adjust ){
        contactAdjustment = adjust;
    }
    
    public static SevereIllness getDefault(){
        return NONE;
    }
    
    public int getContactCountAdjustment(){
        return contactAdjustment;
    }
}
