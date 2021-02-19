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
public class Prioritization {
    private Map<Occupation, PriorityGroup> occupationPriority;
    private Map<IncreasedRisk, PriorityGroup> increasedRiskPriority;
    private Map<SevereIllness,PriorityGroup> severeIllnessPriority;
    private Map<RacialCategory,PriorityGroup> racialPriority;
    private Map<AgeGroup,PriorityGroup> agePriority;
}
