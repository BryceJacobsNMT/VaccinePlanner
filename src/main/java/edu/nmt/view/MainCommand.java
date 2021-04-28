/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nmt.view;

import edu.nmt.model.Disease;
import edu.nmt.model.Population;
import edu.nmt.model.Prioritization;
import edu.nmt.model.VaccineDelivery;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

/**
 * Application entry point for command line version.
 * @author bryce
 */
public class MainCommand {
    /**
     * Entry point
     * @param args - list of input files for the model.
     */
    public static void main( String[] args ){
        //To specify population information a command line argument of the form
        //-PabsolutePathToPopulationFile is expected.  If none is provided, a default
        //population file will be used.
        System.out.println( "Argument count="+args.length);
        Population pop = initializePopulation(args);
        System.out.println( "Population is: "+pop);
        
        //To specify disease information a command line argument of the form
        //-DabsolutePathToDiseaseFile is expected.  If none is provided, a default
        //disease file will be used.
        Disease disease = initializeDisease(args);
        System.out.println( "Disease is: "+disease);
        
        //To specify prioritization information a command line argument of the form
        //-ZabsolutePathToPrioritizationFile is expected.  If none is provided, a default
        //prioritization file will be used.
        Prioritization priority = initializePrioritization(args);
        System.out.println( "Prioritization is: "+priority);
        
        //To specify vaccine delivery information a command line argument of the form
        //-ZabsolutePathToVaccineDeliveryFile is expected.  If none is provided, a default
        //VaccineDelivery file will be used.
        VaccineDelivery vd = initializeVaccineDelivery(args);
        System.out.println( "Vaccine Delivery is: "+vd);
    }
    
    /**
     * Initializes a population using a file specified in the command line arguments, if possible,
     * otherwise uses a default file for initialization.
     * @param args - command line arguments.
     * @return - the corresponding population.
     */
    private static Population initializePopulation( String[] args ){
       
        String userSpecifiedPopFileName = getUserSpecifiedFile( args, "-P");
        
        Population pop = null;
        //If the user specified a file try to use that.
        if ( userSpecifiedPopFileName != null ){
            File popFile = new File( userSpecifiedPopFileName );
            pop = getPopulationFromFile( popFile );
        }
       
        //We were either unable to read the user specified input file or one was not specified.
        if ( pop == null ){
            System.out.println( "No user specified file; using default");
            URL mainURL = MainCommand.class.getClassLoader().getResource( "SamplePopulation.txt");
            System.out.println( "Main URL path is "+mainURL.getPath());
            File popFile = new File( mainURL.getPath());
            pop = getPopulationFromFile( popFile );
            
        }
        else {
            System.out.println( "Got population from user specified file");
        }
        return pop;
    }
    
    /**
     * Parses the command line argument to find an argument that starts with the indicated
     * flag.
     * @param args - the command line arguments.
     * @param start - the starting flag for the target argument.
     * @return - the target argument, if it exists; otherwise null.
     */
    private static String getUserSpecifiedFile( String[] args, String start){
        
        String userSpecifiedFileName = null;
        for ( String arg : args ){
            System.out.println( "arg="+arg);
            if ( arg.startsWith(start)){
                userSpecifiedFileName = arg.replaceAll(start,"").trim();
                System.out.println( "User specified file: "+userSpecifiedFileName);
                break;
            }
        }
        return userSpecifiedFileName;
    }
    
    /**
     * Initializes a disease using a file specified in the command line arguments, if possible,
     * otherwise uses a default file for initialization.
     * @param args - command line arguments.
     * @return - the corresponding disease.
     */
    private static Disease initializeDisease( String[] args ){
       
        String userSpecifiedFileName = getUserSpecifiedFile( args, "-D");
        
       
        Disease dis = null;
        //If the user specified a file try to use that.
        if ( userSpecifiedFileName != null ){
            File disFile = new File( userSpecifiedFileName );
            dis = getDiseaseFromFile( disFile );
        }
       
        //We were either unable to read the user specified input file or one was not specified.
        if ( dis == null ){
            System.out.println( "No user specified file; using default");
            URL mainURL = MainCommand.class.getClassLoader().getResource( "SampleDisease.txt");
            System.out.println( "Main URL path is "+mainURL.getPath());
            File disFile = new File( mainURL.getPath());
            dis = getDiseaseFromFile( disFile );
            
        }
        else {
            System.out.println( "Got disease from user specified file");
        }
        return dis;
    }
    
    /**
     * Initializes a prioritization using a file specified in the command line arguments, if possible,
     * otherwise uses a default file for initialization.
     * @param args - command line arguments.
     * @return - the corresponding prioritization.
     */
    private static Prioritization initializePrioritization( String[] args ){
       
        String userSpecifiedFileName = getUserSpecifiedFile( args, "-Z");
        
       
        Prioritization prior = null;
        //If the user specified a file try to use that.
        if ( userSpecifiedFileName != null ){
            File disFile = new File( userSpecifiedFileName );
            prior = getPrioritizationFromFile( disFile );
        }
       
        //We were either unable to read the user specified input file or one was not specified.
        if ( prior == null ){
            System.out.println( "No user specified file; using default");
            URL mainURL = MainCommand.class.getClassLoader().getResource( "SamplePrioritization.txt");
            System.out.println( "Main URL path is "+mainURL.getPath());
            File priorFile = new File( mainURL.getPath());
            prior = getPrioritizationFromFile( priorFile );
            
        }
        else {
            System.out.println( "Got prioritization from user specified file");
        }
        return prior;
    }
    
     /**
     * Initializes a vaccine delivery using a file specified in the command line arguments, if possible,
     * otherwise uses a default file for initialization.
     * @param args - command line arguments.
     * @return - the corresponding vaccine delivery.
     */
    private static VaccineDelivery initializeVaccineDelivery( String[] args ){
       
        String userSpecifiedFileName = getUserSpecifiedFile( args, "-V");
        
       
        VaccineDelivery vd = null;
        //If the user specified a file try to use that.
        if ( userSpecifiedFileName != null ){
            File vdFile = new File( userSpecifiedFileName );
            vd = getVaccineDeliveryFromFile( vdFile );
        }
       
        //We were either unable to read the user specified input file or one was not specified.
        if ( vd == null ){
            System.out.println( "No user specified file; using default");
            URL mainURL = MainCommand.class.getClassLoader().getResource( "SampleVaccineDelivery.txt");
            System.out.println( "Main URL path is "+mainURL.getPath());
            File vdFile = new File( mainURL.getPath());
            vd = getVaccineDeliveryFromFile( vdFile );
            
        }
        else {
            System.out.println( "Got vaccine delivery from user specified file");
        }
        return vd;
    }
    
    /**
     * Constructs a population from a text file.
     * @param popFile - a text file specifying a population.
     * @return - the corresponding population.
     */
    private static Population getPopulationFromFile( File popFile ){
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
     * Constructs a disease from a text file.
     * @param disFile - a text file specifying a disease.
     * @return - the corresponding disease.
     */
    private static Disease getDiseaseFromFile( File disFile ){
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
     * Constructs a prioritization from a text file.
     * @param priorFile - a text file specifying a prioritization.
     * @return - the corresponding prioritization.
     */
    private static Prioritization getPrioritizationFromFile( File priorFile ){
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
    private static VaccineDelivery getVaccineDeliveryFromFile( File vdFile ){
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
