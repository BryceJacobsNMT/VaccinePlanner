/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nmt.view;

import edu.nmt.model.Disease;
import edu.nmt.model.DiseaseStatistic;
import edu.nmt.model.IncreasedRisk;
import edu.nmt.model.Occupation;
import edu.nmt.model.RacialCategory;
import edu.nmt.model.SevereIllness;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * View for entering disease information.
 * @author bryce
 */
public class DiseasePanel extends JPanel{
    
    private final List<DiseaseListener> diseaseListeners;
    
    private JTextField fileText;
    
    public DiseasePanel(){
        diseaseListeners = new ArrayList<>();
        JLabel diseaseLabel = new JLabel( "Disease:");
        //For now the design appears to be to read from a file rather than using the
        //database.
        fileText = new JTextField();
        fileText.setColumns(30);
        JButton chooseButton = new JButton( "From File...");
        chooseButton.addActionListener((ActionEvent ae) -> {
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter( "Text File", "txt");
            chooser.setFileFilter( filter );
            int result = chooser.showOpenDialog( null );
            if ( result == JFileChooser.APPROVE_OPTION ){
                String fileName = chooser.getSelectedFile().getName();
                fileText.setText( fileName );
                Disease dis = getDiseaseFromFile( fileName );
                if ( dis != null ){
                    diseaseChanged( dis );
                }
            }
        });
        add( diseaseLabel );
        add( fileText );
        add( chooseButton );
    }
    
    /**
     * Store a listener to be notified when the disease changes.
     * @param l -a listener wishing to receive notification of disease change events.
     */
    public void addDiseaseChangeListener( DiseaseListener l ){
        if ( l != null ){
            diseaseListeners.add( l );
        }
    }
    
    /**
     * Let the listeners know that the selected disease has changed.
     * @param newDisease - the new user specified disease. 
     */
    public void diseaseChanged( Disease newDisease ){
        diseaseListeners.forEach((pl) -> {
            pl.diseaseChanged( newDisease );
        });
    }
    
    /**
     * Parses a text file to return a disease.
     * @param fileName - the name of the file to read.
     * @return - the disease described in the file.
     */
    //Note:  Until a sample text file can be provided for parsing, a sample disease will be
    //hard-coded for the purpose of producing results.
    public static Disease getDiseaseFromFile( String fileName){
          //Create a disease
        Disease dis = new Disease();
        
        Map<Occupation,DiseaseStatistic> occDisease = new HashMap<>();
        DiseaseStatistic ds = makeDiseaseStatistic( 0.01f, .15f, .2f, .2f );
        occDisease.put(Occupation.CITY, ds);
        ds = makeDiseaseStatistic( 0.015f, .13f, .19f, .22f );
        occDisease.put(Occupation.FINANCIAL_SERVICE, ds);
        ds = makeDiseaseStatistic( 0.008f, .12f, .3f, .25f );
        occDisease.put(Occupation.CONSTRUCTION, ds);
        dis.setOccupationDisease( occDisease );
       
        Map<IncreasedRisk,DiseaseStatistic> incDisease = new HashMap<>();
        ds = makeDiseaseStatistic( 0.02f, .15f, .2f, .2f );
        incDisease.put(IncreasedRisk.ASTHMA, ds);
        ds = makeDiseaseStatistic( 0.03f, .13f, .11f, .1f );
        incDisease.put(IncreasedRisk.CYSTIC_FIBROSIS, ds);
        dis.setIncreasedRiskDisease(incDisease);
      
        Map<SevereIllness,DiseaseStatistic> sevDisease = new HashMap<>();
        ds = makeDiseaseStatistic( 0.04f, .15f, .15f, .2f );
        sevDisease.put(SevereIllness.CANCER, ds);
        ds = makeDiseaseStatistic( 0.05f, .13f, .13f, .1f );
        sevDisease.put(SevereIllness.SMOKING, ds);
        dis.setSevereIllnessDisease(sevDisease);
   
        Map<RacialCategory,DiseaseStatistic> racDisease = new HashMap<>();
        ds = makeDiseaseStatistic( 0.01f, .15f, .17f, .1f );
        racDisease.put(RacialCategory.BLACK, ds);
        ds = makeDiseaseStatistic( 0.005f, .13f, .11f, .12f );
        racDisease.put(RacialCategory.INDIAN, ds);
        dis.setRacialDisease(racDisease);
        return dis;   
    }
    
    private static DiseaseStatistic makeDiseaseStatistic( float deathRate, float hospRate, float infectRate, float spreadRate ){
        DiseaseStatistic ds = new DiseaseStatistic();
        ds.setDeathRate( .01f );
        ds.setHospitalizationRate( .15f );
        ds.setInfectionRate( 0.2f );
        return ds;
    }
}
