/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nmt.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Produces a model production based on input information.
 * @author bryce
 */
public class Prediction {
    private final int FORECAST_LENGTH = 31;
    private final int INITIAL_ILL = 10;
    private final int INITIAL_HOSP = 4;
    private final int POPULATION_SIZE = 1000;
    private final int INFECTION_LENGTH = 14;
    
    private final Population population;
    private final Disease disease;
    private final VaccineDelivery delivery;
    private final Prioritization priority;
    
    private final List<Person> people;
    private final Set<Integer> infectedIndices;
    private final Set<Integer> hospitalizedIndices;
    private final DailyInfectionStatus[] dailyInfectionStatus;
    
    /**
     * Makes a prediction about the spread of a disease based on the input parameters.
     * @param pop - description of a population of people.
     * @param dis - contains disease information such as how spreadable it is.
     * @param vd - information about what vaccines will be delivered when.
     * @param prior - information about when various groups of people in the population will be vaccinated.
     */
    public Prediction( Population pop, Disease dis, VaccineDelivery vd, Prioritization prior ){
        population = pop;
        disease = dis;
        delivery = vd;
        priority = prior;
        
        infectedIndices = new HashSet<>();
        hospitalizedIndices = new HashSet<>();
        
        //Initialize the people.
        people = new ArrayList<>();
        initPeople();
        
        //Sort the people by priority so they will be in the order they should be vaccinated in.
        Collections.sort( people, new PersonCompare() );
        
        //Data structure to map the status of the infection.
        dailyInfectionStatus = new DailyInfectionStatus[FORECAST_LENGTH];
        
        //Model the disease spread
        computeTheSpread();
    }
    
    /**
     * Forecasts the spread of the disease.
     */
    private void computeTheSpread() {
        for (int i = 0; i < FORECAST_LENGTH; i++) {
            infectPeople(i);
            vaccinatePeople(i);
            storeStats( i );
        }
    }
    
    /**
     * Returns the computed daily infection statistics.
     * @return - the daily infection statistics. 
     */
    public DailyInfectionStatus[] getDailyInfectionStats(){
        return dailyInfectionStatus;
    }
    
    /**
     * Returns the total number of people who were hospitalized.
     * @return - how many people were hospitalized. 
     */
    public int getHospitalizedSize(){
        return hospitalizedIndices.size();
    }
    
    /**
     * Returns the total number of people who can still catch the disease.
     * @return - the total number of people who could still catch the disease.
     */
    private int getInfectibleCount() {
        int count = 0;
        for (int i = 0; i < people.size(); i++) {
            if (people.get(i).getDiseaseStatus() == DiseaseStatus.NOT_EXPOSED
                    && people.get(i).getVaccineStatus() != VaccineStatus.VACCINATED) {
                count++;
            }
        }
        return count;
    }
    
    /**
     * Returns the total number of people who were infected.
     * @return - how many people were infected. 
     */
    public int getInfectedSize(){
        return infectedIndices.size();
    }
    
    /**
     * Returns the total number of people in the population.
     * @return - the population size. 
     */
    public int getPopulationSize(){
        return people.size();
    }
    
    /**
     * Decides which people should become infected on a given day.
     * @param day - a particular day into the model. 
     */
    private void infectPeople(int day) {
        Random rand = new Random(System.currentTimeMillis());
        for (Person person : people) {
            //See who has the potential to infect other people.
            if (person.getDiseaseStatus() == DiseaseStatus.INFECTED || person.getDiseaseStatus() == DiseaseStatus.HOSPITALIZED) {
                if (day != person.getInfectDay()) {
                    //Still infectious
                    if ((day - person.getInfectDay()) < INFECTION_LENGTH) {
                        //Infect the required number of people.
                        float infectionRate = disease.getInfectionRate(person);
                        int infectPeopleCount = (int) Math.round(infectionRate * 100 / INFECTION_LENGTH);
                        int infectableCount = getInfectibleCount();
                        if (infectableCount == 0) {
                            break;
                        }
                        int limit = Math.min(infectPeopleCount, infectableCount);
                        if (limit > 0) {
                            int sickCount = 0;
                            while (sickCount < limit) {
                                int index = rand.nextInt(people.size());
                                //Infect the person if they haven't been exposed to covid and are not
                                //vaccinated.
                                if (people.get(index).getDiseaseStatus() == DiseaseStatus.NOT_EXPOSED
                                        && people.get(index).getVaccineStatus() != VaccineStatus.VACCINATED) {
                                    people.get(index).setDiseaseStatus(DiseaseStatus.INFECTED);
                                    people.get(index).setInfectDay(day);
                                    sickCount++;
                                }
                            }
                        }
                    } 
                    //No longer infectious; set them to recovered.
                    else {
                        person.setDiseaseStatus(DiseaseStatus.RECOVERED);
                    }
                }
                
                //See if the person should be hosptialized.
                if (person.getDiseaseStatus() == DiseaseStatus.INFECTED) {                  
                    float hospRate = disease.getHospitalizationRate(person);
                    int hospCutoff = (int) Math.round(hospRate * 100 / INFECTION_LENGTH);
                    int hospValue = rand.nextInt(100);
                    if (hospValue <= hospCutoff) {
                        person.setDiseaseStatus(DiseaseStatus.HOSPITALIZED);
                    }
                }
                
                //See if the person should be dead
                if (person.getDiseaseStatus() == DiseaseStatus.HOSPITALIZED) {                    
                    float deathRate = disease.getDeathRate(person);
                    int deathCutoff = (int) Math.round(deathRate * 100 / INFECTION_LENGTH);
                    int deathValue = rand.nextInt(100);
                    if (deathValue <= deathCutoff) {
                        person.setDiseaseStatus(DiseaseStatus.DEAD);
                    }
                }
            }

        }
    }
    
     /**
     * Initialize the people.
     */
    private void initPeople(){
         //Initialize the people.
        for ( int i= 0; i < POPULATION_SIZE; i++ ){
            people.add( new Person());
        }
        population.initialize( people );
        
        //Randomly pick people to be ill and in the hospital with an infection.
        //Use an infection day up to two weeks in the past.
        Random r = new Random( System.currentTimeMillis());
        List<Person> infected = new ArrayList<>();
        while ( infected.size() < INITIAL_ILL ){
            int index = r.nextInt( people.size());
            if ( people.get(index).getDiseaseStatus() == DiseaseStatus.getDefault()){
                people.get(index).setDiseaseStatus( DiseaseStatus.INFECTED );
                int infectDay = r.nextInt( 14 );
                people.get(index).setInfectDay( -1 * infectDay );
                infected.add( people.get(index));
            }
        }

        int hospCount = 0;       
        while (hospCount < INITIAL_HOSP ){
            int index = r.nextInt( infected.size());
            if ( infected.get(index).getDiseaseStatus() == DiseaseStatus.INFECTED ){
                infected.get(index).setDiseaseStatus( DiseaseStatus.HOSPITALIZED );
                hospCount++;
            }
        }
    }
    
    /**
     * Store the daily disease statistics.
     * @param day - the model day.
     */
    private void storeStats( int day){
        this.dailyInfectionStatus[day] = new DailyInfectionStatus();
        int deathCount = 0;
        int hospCount = 0;
        int infectCount = 0;
        int vaccinatedCount = 0;
        int partiallyVaccinatedCount = 0;
        int recoveredCount = 0;
        
        for ( int i = 0; i < people.size(); i++ ){
            Person person = people.get(i);
            //Disease status
            DiseaseStatus stat = person.getDiseaseStatus();
            if ( null != stat )
                switch (stat) {
                case DEAD:
                    deathCount++;
                    break;
                case HOSPITALIZED:                   
                    hospCount++;
                    hospitalizedIndices.add(i);
                    break;
                case INFECTED:
                    infectCount++;
                    infectedIndices.add(i);              
                    break;
                case RECOVERED:
                    recoveredCount++;
                default:
                    break;
            }
            
            //Vaccinated status
            VaccineStatus vStat = person.getVaccineStatus();
            if ( vStat == VaccineStatus.VACCINATED ){
                vaccinatedCount++;
            }
            else if ( vStat == VaccineStatus.PARTIALLY_VACCINATED ){
                partiallyVaccinatedCount++;
            }           
        }
        this.dailyInfectionStatus[day].setDeathCount( deathCount );
        this.dailyInfectionStatus[day].setHospCount( hospCount );
        this.dailyInfectionStatus[day].setInfectionCount( infectCount );
        this.dailyInfectionStatus[day].setRecoveredCount( recoveredCount );
        this.dailyInfectionStatus[day].setVaccinatedCount( vaccinatedCount );
        this.dailyInfectionStatus[day].setPartiallyVaccinatedCount( partiallyVaccinatedCount );
    }
    
    /**
     * Decide who should get vaccinated on the specified day.
     * @param day - a specific day in the model.
     */
    private void vaccinatePeople(int day) {
        //Loop through and vaccinate as many people as we can.
        for (Vaccine vac : Vaccine.values()) {
            int requiredDoses = vac.getRequiredDoses();
            int vaccineCount = delivery.getDoses(day, vac);
            if (vaccineCount > 0) {
                int administeredVaccineCount = 0;

                for (Person person : people) {
                    if (person.getVaccineStatus() != VaccineStatus.VACCINATED) {
                        //Never been vaccinated so vax them
                        if (person.getVaccine() == null) {
                            person.setVaccineDoses(1);
                            administeredVaccineCount++;
                            if (requiredDoses == 1) {
                                person.setVaccineStatus(VaccineStatus.VACCINATED);
                            } else {
                                person.setVaccineStatus(VaccineStatus.PARTIALLY_VACCINATED);
                            }
                            person.setVaccineDay(day);
                            person.setVaccine(vac);
                        } //The person has been vacced before with the same vaccine
                        else if (person.getVaccine() == vac) {
                            //See if enough time has elapsed to vax them again.
                            int vaccineDay = person.getVaccineDay();
                            if ((day - vaccineDay) >= vac.getDaysBetweenDoses()) {
                                //Okay to vax them again.
                                int doseCount = person.getVaccineDoses() + 1;
                                person.setVaccineDoses(doseCount);
                                person.setVaccineDay(day);
                                if (doseCount >= requiredDoses) {
                                    person.setVaccineStatus(VaccineStatus.VACCINATED);
                                }
                                administeredVaccineCount++;
                            }
                        }
                        //We are all out of this vaccine and have to to on to another one.
                        if (administeredVaccineCount >= vaccineCount) {
                            break;
                        }
                    }
                }
            }
        }
    }
    
    /**
     * Compares people on the basis of who should get vaccinated first.
     */
    class PersonCompare implements Comparator<Person> {
        @Override
        public int compare( Person a, Person b ){
            PriorityGroup aPriority = priority.getPriority( a );
            PriorityGroup bPriority = priority.getPriority( b );
            return aPriority.ordinal() - bPriority.ordinal();
        }
    }
    
}
