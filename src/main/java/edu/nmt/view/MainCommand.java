/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nmt.view;

import edu.nmt.model.DailyInfectionStatus;
import edu.nmt.model.Disease;
import edu.nmt.model.Grapher;
import edu.nmt.model.Population;
import edu.nmt.model.Prediction;
import edu.nmt.model.Prioritization;
import edu.nmt.model.Statistics;
import edu.nmt.model.VaccineDelivery;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.xml.bind.JAXBException;
import org.jfree.chart.JFreeChart;

/**
 * Application entry point for command line version.
 * @author bryce
 */
public class MainCommand {
    
    private static final String POP_FILE = "SamplePopulation.txt";
    private static final String DIS_FILE = "SampleDisease.txt";
    private static final String VACC_FILE = "SampleVaccineDelivery.txt";
    private static final String PRIOR_FILE = "SamplePrioritization.txt";
    
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
        
        //To specify the location where output files should be written a command line
        //argument of the form -Wdirectory is expected.  The directory should be an absolute
        //path ending in a path separator, i.e., '/'.  If no location is provided, the
        //current directory will be used.
        String writeDirectory = initializeWriteDirectory(args);
        System.out.println( "Write directory is "+writeDirectory);
        
        //Run the model with the given inputs.
        Prediction predict = new Prediction( pop, disease, vd, priority );
        DailyInfectionStatus[] stats = predict.getDailyInfectionStats();
        
        //Generate some graphs and statistics and persist them.
        JFreeChart caseGraph = Grapher.generateCaseGraph( stats);
        writeGraph( caseGraph, writeDirectory +"DailyCaseCounts.png");
        JFreeChart cumGraph = Grapher.generateCumulativeCaseGraph( stats );
        writeGraph( cumGraph, writeDirectory+"CumulativeCaseCounts.png");
        JFreeChart vacGraph = Grapher.generateVaccineAvailabilityGraph( vd, stats.length );
        writeGraph( vacGraph, writeDirectory+"VaccineAvailability.png");
        
        //Write an XML file containing summary statistics.
        Statistics statistics = new Statistics();
        statistics.computeStatistics(stats);
        statistics.setTotalPopulationSize( predict.getPopulationSize() );
        statistics.setTotalInfections( predict.getInfectedSize());
        statistics.setTotalHospitalizations( predict.getHospitalizedSize());
        try {
            String summaryStats = statistics.toXML();
            System.out.println( "Summary stats are: "+summaryStats);
            try (BufferedWriter writer = new BufferedWriter( new FileWriter(writeDirectory + "SummaryStatistics.xml"))) {
                writer.write(summaryStats);
            }
        }
        catch( JAXBException | IOException je ){
            System.out.println( "Could not write summary statistics to a file: "+je);
        }
      
        
    }
    
    /**
     * Write a graph as a 'png' file to the indicated file.
     * @param graph - the graph to persist.
     * @param fileName- absolute path to the file where the graph should be persisted.
     */
    private static void writeGraph( JFreeChart graph, String fileName ){
        //Export the chart as a PNG for use in the UI
        System.out.println( "FileName="+fileName);
        BufferedImage image = new BufferedImage( 1000, 400, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();
        g2.setRenderingHint( JFreeChart.KEY_SUPPRESS_SHADOW_GENERATION, true );
        Rectangle r = new Rectangle( 0, 0, 600, 400 );
        graph.draw( g2, r );             
        try {
            //Save to a file
            File f = new File(fileName);
            BufferedImage chartImage = graph.createBufferedImage(1600, 400, null);
            ImageIO.write(chartImage, "png", f); 
            System.out.println( "Wrote graph");
        } 
        catch (IOException ioe) {
            System.out.println("Could not write graph: "+ioe);
        }       
    }
    
    /**
     * Returns the directory where output files should be written.
     * @param args - command line arguments.
     * @return - directory where output files should be written.
     */
    private static String initializeWriteDirectory( String[] args ){
        String dir = getUserSpecifiedFile( args, "-W");
        boolean fileExists = false;
        if ( dir != null ){
            File file = new File( dir );
            if ( file.exists()){
                fileExists = true;
            }
        }
        if ( !fileExists){
            //Use the current directory
            //URL mainURL = MainCommand.class.getClassLoader().getResource( "../SamplePopulation.txt");
            dir = new File( ".").getAbsolutePath();
            dir = dir.replaceAll( ".", "");
            System.out.println( "Using current directory as write: "+dir);
        }
        else {
            System.out.println( "Using user specified directory "+dir);
        }
        return dir;
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
            URL mainURL = MainCommand.class.getClassLoader().getResource( POP_FILE );           
            if ( mainURL.getProtocol().equals("jar")){
                pop = initializePopulationFromJar();
            }
            else {
                System.out.println( "Main URL path is "+mainURL.getPath());
                File popFile = new File( mainURL.getPath());
                pop = getPopulationFromFile( popFile );
            }
            System.out.println( "Pop is "+pop);
        }
        else {
            System.out.println( "Got population from user specified file");
        }
        return pop;
    }
    
    /**
     * Returns a Population produced from a sample file contained inside a jar file.
     * @return - a Population.
     */
    public static Population initializePopulationFromJar() {
        InputStream input = MainCommand.class.getClassLoader().getResourceAsStream(POP_FILE);
        Population pop = new Population();
        if ( input != null ){
            String popStr;
            StringBuilder buf = new StringBuilder();
           
            try ( BufferedReader reader = new BufferedReader(new InputStreamReader(input))){
                while ((popStr = reader.readLine()) != null) {
                    buf.append(popStr).append( "\n");
                }
                pop = Population.fromString(buf.toString());
            } 
            catch (IOException ioe) {
                System.out.println("Exception reading sample population file=" + ioe);
            } 
            finally {
                try {
                    input.close();
                } 
                catch (IOException ioe) {
                    System.out.println("Exception closing input stream: "+ioe);
                }
            }
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
            URL mainURL = MainCommand.class.getClassLoader().getResource( DIS_FILE);
            if ( mainURL.getProtocol().equals("jar")){
                dis = initializeDiseaseFromJar();
            }
            else {
                System.out.println( "Main URL path is "+mainURL.getPath());
                File disFile = new File( mainURL.getPath());
                dis = getDiseaseFromFile( disFile );
            }
            
        }
        else {
            System.out.println( "Got disease from user specified file");
        }
        return dis;
    }
    
    /**
     * Returns a Disease produced from a sample file contained inside a jar file.
     * @return - a Disease.
     */
    public static Disease initializeDiseaseFromJar() {
        InputStream input = MainCommand.class.getClassLoader().getResourceAsStream(DIS_FILE);
        Disease dis = new Disease();
        if ( input != null ){
            String popStr;
            StringBuilder buf = new StringBuilder();
           
            try ( BufferedReader reader = new BufferedReader(new InputStreamReader(input))){
                while ((popStr = reader.readLine()) != null) {
                    buf.append(popStr).append( "\n");
                }
                dis = Disease.fromString(buf.toString());
            } 
            catch (IOException ioe) {
                System.out.println("Exception reading sample disease file=" + ioe);
            } 
            finally {
                try {
                    input.close();
                } 
                catch (IOException ioe) {
                    System.out.println("Exception closing input stream: "+ioe);
                }
            }
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
            URL mainURL = MainCommand.class.getClassLoader().getResource( PRIOR_FILE);
            if ( mainURL.getProtocol().equals("jar")){
                prior = initializePrioritizationFromJar();
            }
            else {
                System.out.println( "Main URL path is "+mainURL.getPath());
                File priorFile = new File( mainURL.getPath());
                prior = getPrioritizationFromFile( priorFile );
            }
            
        }
        else {
            System.out.println( "Got prioritization from user specified file");
        }
        return prior;
    }
    
    /**
     * Returns a Prioritization produced from a sample file contained inside a jar file.
     * @return - a Prioritization.
     */
    public static Prioritization initializePrioritizationFromJar() {
        InputStream input = MainCommand.class.getClassLoader().getResourceAsStream(PRIOR_FILE);
        Prioritization prior = new Prioritization();
        if ( input != null ){
            String popStr;
            StringBuilder buf = new StringBuilder();
           
            try ( BufferedReader reader = new BufferedReader(new InputStreamReader(input))){
                while ((popStr = reader.readLine()) != null) {
                    buf.append(popStr).append( "\n");
                }
                prior = Prioritization.fromString(buf.toString());
            } 
            catch (IOException ioe) {
                System.out.println("Exception reading sample prioritization file=" + ioe);
            } 
            finally {
                try {
                    input.close();
                } 
                catch (IOException ioe) {
                    System.out.println("Exception closing input stream: "+ioe);
                }
            }
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
            URL mainURL = MainCommand.class.getClassLoader().getResource( VACC_FILE);
            if ( mainURL.getProtocol().equals("jar")){
                vd = initializeVaccineDeliveryFromJar();
            }
            else {
                System.out.println( "Main URL path is "+mainURL.getPath());
                File vdFile = new File( mainURL.getPath());
                vd = getVaccineDeliveryFromFile( vdFile );
            }         
        }
        else {
            System.out.println( "Got vaccine delivery from user specified file");
        }
        return vd;
    }
    
    /**
     * Returns a VaccineDelivery produced from a sample file contained inside a jar file.
     * @return - a VaccineDelivery.
     */
    public static VaccineDelivery initializeVaccineDeliveryFromJar() {
        InputStream input = MainCommand.class.getClassLoader().getResourceAsStream(VACC_FILE);
        VaccineDelivery vd = new VaccineDelivery();
        if ( input != null ){
            String popStr;
            StringBuilder buf = new StringBuilder();
           
            try ( BufferedReader reader = new BufferedReader(new InputStreamReader(input))){
                while ((popStr = reader.readLine()) != null) {
                    buf.append(popStr).append( "\n");
                }
                vd = VaccineDelivery.fromString(buf.toString());
            } 
            catch (IOException ioe) {
                System.out.println("Exception reading sample vaccine delivery file=" + ioe);
            } 
            finally {
                try {
                    input.close();
                } 
                catch (IOException ioe) {
                    System.out.println("Exception closing input stream: "+ioe);
                }
            }
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