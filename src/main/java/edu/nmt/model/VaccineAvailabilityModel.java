/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nmt.model;

import java.io.Serializable;

/**
 *
 * @author bryce
 */
public interface VaccineAvailabilityModel {
  
    
    public abstract int getDoses( int dayCount );
    
}
