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
public enum PriorityGroup {
    ONE (1),
    TWO (2),
    THREE (3),
    FOUR(4),
    FIVE (5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    TEN(10);
    
    private final int ordering;
    
    private PriorityGroup( int order ){
        ordering = order;
    }
}
