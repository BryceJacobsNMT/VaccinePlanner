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
public class Disease {
    private Map<Occupation,DiseaseStatistic> occupationDisease;
    private Map<IncreasedRisk,DiseaseStatistic> increasedRiskDisease;
    private Map<SevereIllness,DiseaseStatistic> severeIllnessDisease;
    private Map<RacialCategory,DiseaseStatistic> racialDisease;
    private Map<AgeGroup,DiseaseStatistic> ageDisease;
}
