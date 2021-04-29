/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nmt.model;

/**
 * Represents a vaccination priority; ONE is the first group to get vaccinated.
 * @author bryce
 */
public enum PriorityGroup {
    ONE,
    TWO,
    THREE,
    FOUR,
    FIVE,
    SIX,
    SEVEN,
    EIGHT,
    NINE,
    TEN;
    
    private PriorityGroup(){
    }
    
    public static PriorityGroup getDefault(){
        return TEN;
    }
}
