/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nmt.view;

import edu.nmt.model.Disease;

/**
 * Notification that the user has selected a new disease to model.
 * @author bryce
 */
public interface DiseaseListener {
    
    /**
     * Notification that the disease for the model has changed.
     * @param disease - the new Disease.
     */
    public void diseaseChanged( Disease disease );
}
