/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nmt.view;

import edu.nmt.model.VaccineDelivery;

/**
 * Notification that the user has selected a new vaccine delivery model.
 * @author bryce
 */
public interface VaccineDeliveryListener {
    
    /**
     * Notification that the vaccine delivery model has changed.
     * @param delivery - the new VaccineDelivery.
     */
    public void deliveryChanged( VaccineDelivery disease );
}
