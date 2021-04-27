/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nmt.view;

import edu.nmt.model.Prioritization;

/**
 * Notification that the user has selected a new Prioritization scheme to model.
 * @author bryce
 */
public interface PriorityListener {
    
    /**
     * Notification that the prioritization for the model has changed.
     * @param priority - the new Prioritization.
     */
    public void priorityChanged( Prioritization priority );
}
