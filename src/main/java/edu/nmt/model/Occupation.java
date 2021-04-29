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
public enum Occupation {
    AGRICULTURE(0),
    CARE_GIVER(10),
    CHILD_CARE(10),
    CITY(5),
    CLEANING_SERVICE(20),
    CONGREGATE_CARE(5),
    CONSTRUCTION(0),
    CONVENIENCE_STORE(20),
    CORRECTIONS(10),
    COUNTY(0),
    COURT_PERSONNEL(10),
    ENERGY(0),
    FACTORY_FOOD_PROCESSING(20),
    FEDERAL_EMPLOYEE(0),
    FINANCIAL_SERVICE(0),
    FIRST_RESPONDER(5),
    FOOD_SERVICE(0),
    GROCERY_STORE(20),
    HEALTHCARE(20),
    HIGHER_EDUCATION(20),
    INDIGENT_CARE(10),
    IT_COMMUNICATION(-5),
    K_12_EDUCATOR(20),
    LEGAL_ACCOUNTING(0),
    MANUFACTURING(10),
    MEDIA(0),
    MEDICAL_ENGINEERING_SUPPLIES(0),
    MORTUARY_FUNERAL_SERVICE(10),
    NON_FOOD_RETAIL(10),
    PUBLIC_HEALTH(0),
    PUBLIC_SAFETY_ENGINEERING(0),
    PUBLIC_SAFETY_NONMEDICAL_FIRST_RESPONDER(0),
    PUBLIC_TRANSIT(10),
    RESEARCH_LABORATORIES(0),
    SCHOOL_STAFF(10),
    SHELTER_HOUSING(10),
    STATE(0),
    STUDENT(0),
    TRANSPORTATION_LOGISTICS(0),
    US_POSTAL_SERVICE(5),
    VETERINARY_SERVICE(5),
    WATER_WASTEWATER(0),
    UNEMPLOYED(0),
    RETIRED(-5),
    OTHER(0);
    
    private final int contactRateAdjustment;
    
    private Occupation( int adjust ){
        contactRateAdjustment = adjust;
    }
    
    public static Occupation getDefault(){
        return Occupation.OTHER;
    }
    
    public int getContactCountAdjustment(){
        return contactRateAdjustment;
    }
}
