/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nmt.view;

import edu.nmt.model.AgeGroup;
import edu.nmt.model.IncreasedRisk;
import edu.nmt.model.Occupation;
import edu.nmt.model.Prioritization;
import edu.nmt.model.PriorityGroup;
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
 * View for entering priority information.
 * @author bryce
 */
public class PriorityPanel extends JPanel{
    
    private final List<PriorityListener> priorityListeners;
    
    private JTextField fileText;
    
    public PriorityPanel(){
        priorityListeners = new ArrayList<>();
        JLabel priorityLabel = new JLabel( "Prioritization:");
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
                Prioritization priority = getPriorityFromFile( fileName );
                if ( priority != null ){
                    priorityChanged( priority );
                }
            }
        });
        add( priorityLabel );
        add( fileText );
        add( chooseButton );
    }
    
    /**
     * Store a listener to be notified when the prioritization changes.
     * @param l -a listener wishing to receive notification of prioritization change events.
     */
    public void addPriorityChangeListener( PriorityListener l ){
        if ( l != null ){
            priorityListeners.add( l );
        }
    }
    
    /**
     * Let the listeners know that the selected priority has changed.
     * @param newPriority - the new user specified priority. 
     */
    public void priorityChanged( Prioritization newPriority ){
        priorityListeners.forEach((pl) -> {
            pl.priorityChanged( newPriority );
        });
    }
    
    /**
     * Parses a text file to return a prioritization.
     * @param fileName - the name of the file to read.
     * @return - the disease described in the file.
     */
    //Note:  Until a sample text file can be provided for parsing, a sample prioritization will be
    //hard-coded for the purpose of producing results.
    public static Prioritization getPriorityFromFile( String fileName){
        Prioritization prior = new Prioritization();
        
        Map<Occupation, PriorityGroup> occupationPriority = new HashMap<>();
        occupationPriority.put( Occupation.CONSTRUCTION, PriorityGroup.EIGHT );
        occupationPriority.put( Occupation.COUNTY, PriorityGroup.SIX);
        occupationPriority.put( Occupation.CITY, PriorityGroup.FIVE );
        prior.setOccupationPriority( occupationPriority );
        
        Map<IncreasedRisk, PriorityGroup> increasedRiskPriority = new HashMap<>();
        increasedRiskPriority.put( IncreasedRisk.LIVER_DISEASE, PriorityGroup.THREE );
        increasedRiskPriority.put( IncreasedRisk.PULMONARY_FIBROSIS, PriorityGroup.FOUR );
        increasedRiskPriority.put( IncreasedRisk.THALASSEMIA, PriorityGroup.FIVE );
        prior.setIncreasedRiskPriority( increasedRiskPriority );
        
        Map<SevereIllness,PriorityGroup> severeIllnessPriority = new HashMap<>();
        severeIllnessPriority.put( SevereIllness.CANCER, PriorityGroup.TWO );
        severeIllnessPriority.put( SevereIllness.SICKLE_CELL_DISEASE, PriorityGroup.THREE );
        severeIllnessPriority.put( SevereIllness.SMOKING, PriorityGroup.SEVEN );
        prior.setSevereIllnessPriority( severeIllnessPriority );
        
        Map<AgeGroup, PriorityGroup> agePriority = new HashMap<>();
        agePriority.put( AgeGroup.ADULT, PriorityGroup.FOUR );
        agePriority.put( AgeGroup.MIDDLE_ADULT, PriorityGroup.FIVE );
        agePriority.put( AgeGroup.OLDER_ADULT, PriorityGroup.THREE );
        prior.setAgePriority( agePriority );
        return prior;
    }
}
