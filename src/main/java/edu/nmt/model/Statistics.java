/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nmt.model;

import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * Produces summary statistics for the result.
 * @author bryce
 */
@XmlRootElement
public class Statistics {
    
    private int totalInfections;
    private int dayCount;
    private int totalHospitalizations;
    private int totalDeaths;
    private int totalPopulation;
    private int totalVacced;
    private int totalPartiallyVacced;
    private static JAXBContext jaxbContext;
    
    /**
     * Constructor.
     */
    public Statistics(){
    }
    
    /**
     * Compute summary statistics about the infection.
     * @param stats - daily infection statistics.
     */
    public void computeStatistics( DailyInfectionStatus[] stats ){
        dayCount = stats.length;
        totalVacced = stats[stats.length - 1].getVaccinatedCount( );
        totalPartiallyVacced = stats[stats.length - 1].getPartiallyVaccinatedCount();   
        totalDeaths += stats[stats.length - 1].getDeathCount();
    }
    
    public int getDayCount(){
        return dayCount;
    }
    
    public int getTotalDeaths(){
        return totalDeaths;
    }
    
    public int getTotalHospitalizations(){
        return totalHospitalizations;
    }
    
    public int getTotalInfections(){
        return totalInfections;
    }
    
    public int getTotalVacced(){
        return totalVacced;
    }
    
    public int getTotalPartiallyVacced(){
        return totalPartiallyVacced;
    }
    
    public int getTotalPopulationSize(){
        return totalPopulation;
    }
    
    public void setDayCount( int day ){
        dayCount = day;
    }
    
    public void setTotalDeaths( int deaths ){
        totalDeaths = deaths;
    }
    
    public void setTotalHospitalizations( int hosps ){
        totalHospitalizations = hosps;
    }
    
    public void setTotalInfections( int count ){
        totalInfections = count;
    }
    
    public void setTotalPopulationSize( int count ){
        totalPopulation = count;
    }
    
    public void setTotalPartiallyVacced( int count ){
        totalPartiallyVacced = count;
    }
    
    public void setTotalVacced( int count ){
        totalVacced = count;
    }
    
    /**
     * Converts the summary statistics to XML format
     * @return - the summary statistics in XML format.
     * @throws JAXBException 
     */
    public String toXML() throws JAXBException {
        String result = null;
        try {
            if ( jaxbContext == null ){
                jaxbContext = JAXBContext.newInstance(Statistics.class);
            }
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            StringWriter sw = new StringWriter();
            jaxbMarshaller.marshal(this, sw);
            result = sw.toString();
        }
        catch( JAXBException je ){
            System.out.println( "Could not write statistics to XML: "+je);
            throw je;
        }
        return result;
    }
   
}
