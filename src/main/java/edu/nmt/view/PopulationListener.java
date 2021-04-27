/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nmt.view;

import edu.nmt.model.Population;

/**
 * Notification that the user has selected a new population to model.
 * @author bryce
 */
public interface PopulationListener {
    
    /**
     * Notification that the population for the model has changed.
     * @param pop - the new Population.
     */
    public void populationChanged( Population pop );
}
