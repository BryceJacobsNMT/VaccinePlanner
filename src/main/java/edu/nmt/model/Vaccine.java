/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nmt.model;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author bryce
 */
public enum Vaccine {
    /*MODERNA( "Moderna", Arrays.asList( new Float[]{80f, 94.1f}), 28),
    PFIZER_BIONTECH("Pfizer-Biontech", Arrays.asList( new Float[]{90f, 95f}), 21),
    JOHNSON( "Johnson and Johnson", Arrays.asList( new Float[]{66f}), 0);
    
    
    private final String name;
    private final List<Float> efficacies;
    private final int dosageInterval;
    
    private Vaccine( String name, List<Float> efficacy, int dosageInterval ){
        this.name = name;
        this.efficacies = efficacy;
        this.dosageInterval = dosageInterval;
    }*/
    MODERNA,
    PFIZER,
    JOHNSON;
    
}
