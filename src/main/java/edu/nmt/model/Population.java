/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nmt.model;

import java.util.Map;

/**
 *
 * @author bryce
 */
public class Population {
    private float chronicMedicalConditionPercent;
    private float increasedRiskPercent;
    private float severeIllnessPercent;
    private Map<RacialCategory,Float> racialMix;
    private Map<AgeGroup,Float> ageMix;
}
