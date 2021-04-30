/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nmt.util;

import edu.nmt.model.Disease;
import edu.nmt.model.Population;
import edu.nmt.model.Prioritization;
import edu.nmt.model.VaccineDelivery;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Reads objects from text files.
 * @author bryce
 */
public class IOUtility {
    
    public static final String POP_FILE = "SamplePopulation.txt";
    public static final String DIS_FILE = "SampleDisease.txt";
    public static final String VACC_FILE = "SampleVaccineDelivery.txt";
    public static final String PRIOR_FILE = "SamplePrioritization.txt";
    
    /**
     * Constructor.
     */
    private IOUtility(){
        
    }
    
    /**
     * Constructs a disease from a text file.
     * @param disFile - a text file specifying a disease.
     * @return - the corresponding disease.
     */
    public static Disease getDiseaseFromFile( File disFile ){
        Disease dis = null;
         if ( disFile.exists() ){
            String fileContents = readFile( disFile );
            if ( !fileContents.isEmpty() || !fileContents.isBlank() ){
            
                dis = Disease.fromString( fileContents );
            }
        }
        return dis;     
    }
    
    /**
     * Constructs a population from a text file.
     * @param popFile - a text file specifying a population.
     * @return - the corresponding population.
     */
    public static Population getPopulationFromFile( File popFile ){
        Population pop = null;
         if ( popFile.exists() ){
            String fileContents = readFile( popFile );
            if ( !fileContents.isEmpty() || !fileContents.isBlank() ){
            
                pop = Population.fromString( fileContents );
            }
        }
        return pop;     
    }
    
    /**
     * Constructs a prioritization from a text file.
     * @param priorFile - a text file specifying a prioritization.
     * @return - the corresponding prioritization.
     */
    public static Prioritization getPrioritizationFromFile( File priorFile ){
        Prioritization prior = null;
         if ( priorFile.exists() ){
            String fileContents = readFile( priorFile );
            if ( !fileContents.isEmpty() || !fileContents.isBlank() ){
            
                prior = Prioritization.fromString( fileContents );
            }
        }
        return prior;     
    } 
    
    /**
     * Constructs a vaccine delivery from a text file.
     * @param vdFile - a text file specifying vaccine delivery.
     * @return - the corresponding vaccine delivery.
     */
    public static VaccineDelivery getVaccineDeliveryFromFile( File vdFile ){
        VaccineDelivery vd = null;
         if ( vdFile.exists() ){
            String fileContents = readFile( vdFile );
            if ( !fileContents.isEmpty() || !fileContents.isBlank() ){
            
                vd = VaccineDelivery.fromString( fileContents );
            }
        }
        return vd;     
    }
    
    /**
     * Reads a file containing text.
     * @param file - the file to read.
     * @return - the text content of the file.
     */
    private static String readFile( File file ){
        StringBuilder fileContents = new StringBuilder();
        try 
            (BufferedReader in = new BufferedReader( new FileReader( file ))){
            String str;
            while ((str = in.readLine()) != null ){
                fileContents.append( str ).append("\n");
            }
        }
        catch( IOException ioe ){
            System.out.println( "Could not print file: "+file.getAbsolutePath()+ioe);
        }
        return fileContents.toString();
    }
}
