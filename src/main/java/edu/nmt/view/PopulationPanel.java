/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nmt.view;

import edu.nmt.model.AgeGroup;
import edu.nmt.model.Population;
import edu.nmt.model.RacialCategory;
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
 * View for entering population information.
 * @author bryce
 */
public class PopulationPanel extends JPanel{
    
    private final List<PopulationListener> popListeners;
    
    private JTextField fileText;
    
    public PopulationPanel(){
        popListeners = new ArrayList<>();
        JLabel populationLabel = new JLabel( "Population:");
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
                Population pop = getPopulationFromFile( fileName );
                if ( pop != null ){
                    populationChanged( pop );
                }
            }
        });
        add( populationLabel );
        add( fileText );
        add( chooseButton );
    }
    
    /**
     * Store a listener to be notified when the population changes.
     * @param l -a listener wishing to receive notification of population change events.
     */
    public void addPopulationChangeListener( PopulationListener l ){
        if ( l != null ){
            popListeners.add( l );
        }
    }
    
    /**
     * Let the listeners know that the selected population has changed.
     * @param newPop 
     */
    public void populationChanged( Population newPop ){
        for ( PopulationListener pl : popListeners ){
            pl.populationChanged( newPop );
        }
    }
    
    /**
     * Parses a text file to return a population.
     * @param fileName - the name of the file to read.
     * @return - the population described in the file.
     */
    //Note:  Until a sample text file can be provided for parsing, a sample population will be
    //hard-coded for the purpose of producing results.
    public static Population getPopulationFromFile( String fileName){
        Population pop = new Population();
        pop.setChronicMedicalConditionPercent(.1f);
        pop.setIncreasedRiskPercent(.2f);
        pop.setSevereIllnessPercent(.05f);
        final float FIXED_PERCENT = .2f;
        Map<RacialCategory,Float> racialMix = new HashMap<>();
        racialMix.put(RacialCategory.ASIAN, FIXED_PERCENT );
        racialMix.put(RacialCategory.BLACK, FIXED_PERCENT );
        racialMix.put(RacialCategory.OTHER, FIXED_PERCENT );
        racialMix.put(RacialCategory.PACIFIC_ISLANDER, FIXED_PERCENT );
        racialMix.put(RacialCategory.WHITE, FIXED_PERCENT );
        pop.setRacialMix(racialMix );
        Map<AgeGroup,Float> ageMix = new HashMap<>();
        ageMix.put(AgeGroup.CHILD,FIXED_PERCENT );
        ageMix.put(AgeGroup.ADULT, FIXED_PERCENT );
        ageMix.put(AgeGroup.MIDDLE_ADULT, FIXED_PERCENT );
        ageMix.put(AgeGroup.LESS_THAN_YEAR, .1f);
        ageMix.put(AgeGroup.YOUNG_ADULT, .1f);
        ageMix.put(AgeGroup.OLDER_ADULT, .1f );
        ageMix.put(AgeGroup.OLDER_ADULT, .1f );
        pop.setAgeMix( ageMix );
        return pop;
    }
}
