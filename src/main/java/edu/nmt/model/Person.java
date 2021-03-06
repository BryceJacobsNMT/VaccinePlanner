/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nmt.model;

import edu.nmt.util.ObjectUtility;

/**
 * Represents a person in the hypothetical population
 * @author bryce
 */
public class Person {
    private DiseaseStatus diseaseStatus;
    private VaccineStatus vaccineStatus;
    private int infectDay;
    private int vaccineDoses;
    private int vaccineDay;
    private Occupation occupation;
    private AgeGroup ageGroup;
    private IncreasedRisk increasedRisk;
    private RacialCategory race;
    private SevereIllness illness;
    private Vaccine vaccine;
    
    /**
     * Constructor.
     */
    public Person(){
        diseaseStatus = DiseaseStatus.getDefault();
        occupation = Occupation.getDefault();
        ageGroup = AgeGroup.getDefault();
        increasedRisk = IncreasedRisk.getDefault();
        race = RacialCategory.getDefault();
        illness = SevereIllness.getDefault();
        vaccineStatus = VaccineStatus.getDefault();
    }
    
    public AgeGroup getAgeGroup(){
        return ageGroup;
    }
    
   
    
    public DiseaseStatus getDiseaseStatus(){
        return diseaseStatus;
    }
    
    public SevereIllness getSevereIllness(){
        return illness;
    }
    
    public IncreasedRisk getIncreasedRisk(){
        return increasedRisk;
    }
    
    public int getInfectDay(){
        return infectDay;
    }
    
    public int getVaccineDoses(){
        return vaccineDoses;
    }
    
    public Occupation getOccupation(){
        return occupation;
    }
    
    public RacialCategory getRacialCategory(){
        return race;
    }
    
    public int getVaccineDay(){
        return vaccineDay;
    }
    
    public VaccineStatus getVaccineStatus(){
        return vaccineStatus;
    }
    
    public Vaccine getVaccine(){
        return vaccine;
    }
    
    public boolean isEmployable(){
        boolean employable = false;
        if ( ageGroup !=AgeGroup.CHILD && ageGroup != AgeGroup.LESS_THAN_YEAR && ageGroup != AgeGroup.YOUNG_ADULT ){
            employable = true;
        }
        return employable;
    }
    
    public void setDiseaseStatus( DiseaseStatus status ){
        diseaseStatus = status;
    }
    
    public void setAgeGroup( AgeGroup group ){
        ageGroup = group;
    }
    
    public void setInfectDay( int day ){
        infectDay = day;
    }
    
    public void setSevereIllness( SevereIllness ill ){
        illness = ill;
    }
    
    public void setRacialCategory( RacialCategory race ){
        this.race = race;
    }
    
    public void setIncreasedRisk( IncreasedRisk risk ){
        increasedRisk = risk;
    }
    
    public void setOccupation( Occupation occ ){
        occupation = occ;
    }
    
    public void setVaccine( Vaccine vac ){
        vaccine = vac;
    }
    
    public void setVaccineDoses( int doses ){
        vaccineDoses = doses;
    }
    
    public void setVaccineStatus( VaccineStatus stat ){
        vaccineStatus = stat;
    }
    
    public void setVaccineDay( int day ){
        vaccineDay = day;
    }

    @Override
    public String toString(){
        StringBuilder build = new StringBuilder();
        final String EOL = "\n";
        build.append( "DiseaseStatus: ").append( diseaseStatus).append( EOL );
        build.append( "VaccineStatus: ").append( vaccineStatus).append( EOL );
        build.append( "Infection Day: ").append( infectDay).append( EOL );
        build.append( "Vaccine Doses: ").append( vaccineDoses ).append( EOL );
        build.append( "Dosage Day: ").append( vaccineDay).append( EOL );
        build.append( "Occupation: ").append( occupation ).append( EOL );
        build.append( "Age Group: ").append( ageGroup ).append( EOL );
        build.append( "Increased Risk: ").append( increasedRisk ).append( EOL );
        build.append( "Racial Category: ").append( race ).append( EOL );
        build.append( "Severe Illness: ").append( illness ).append( EOL );
        build.append( "Vaccine: ").append( vaccine ).append( EOL );
        return build.toString();
    }
    
    @Override
    public boolean equals( Object other ){
        boolean equalObs = false;
        if ( other instanceof Person ){
            Person oPerson = (Person)other;
            if ( this.diseaseStatus == oPerson.diseaseStatus ){
                if ( this.vaccineStatus == oPerson.vaccineStatus ){
                    if ( this.infectDay == oPerson.infectDay ){
                        if ( this.vaccineDoses == oPerson.vaccineDoses ){
                            if ( this.occupation == oPerson.occupation ){
                                if ( this.ageGroup == oPerson.ageGroup ){
                                    if ( this.increasedRisk == oPerson.increasedRisk){
                                        if ( this.race == oPerson.race ){
                                            if ( this.illness == oPerson.illness ){
                                                if ( this.vaccine == oPerson.vaccine ){
                                                    equalObs = true;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return equalObs;
    }
    
    @Override
    public int hashCode(){
        int result = 17;
        final int MULT = 3;
        result = result * MULT + ObjectUtility.hashCode(diseaseStatus);
        result = result * MULT + ObjectUtility.hashCode(vaccineStatus);
        result = result * MULT + infectDay;
        result = result * MULT + vaccineDoses;
        result = result * MULT + vaccineDay;
        result = result * MULT + ObjectUtility.hashCode( occupation );
        result = result * MULT + ObjectUtility.hashCode( ageGroup );
        result = result * MULT + ObjectUtility.hashCode( increasedRisk );
        result = result * MULT + ObjectUtility.hashCode( race );
        result = result * MULT + ObjectUtility.hashCode( illness );
        result = result * MULT + ObjectUtility.hashCode( vaccine );
        return result;
    }
}
