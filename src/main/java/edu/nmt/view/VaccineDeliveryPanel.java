/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nmt.view;

import edu.nmt.model.Vaccine;
import edu.nmt.model.VaccineAvailabilityModelContinuous;
import edu.nmt.model.VaccineDelivery;
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
 * View for entering vaccine delivery information.
 * @author bryce
 */
public class VaccineDeliveryPanel extends JPanel{
    
    private final List<VaccineDeliveryListener> deliveryListeners;
    
    private JTextField fileText;
    
    public VaccineDeliveryPanel(){
        deliveryListeners = new ArrayList<>();
        JLabel deliveryLabel = new JLabel( "Vaccine Delivery:");
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
                VaccineDelivery vd = getDeliveryFromFile( fileName );
                if ( vd != null ){
                    deliveryChanged( vd );
                }
            }
        });
        add( deliveryLabel );
        add( fileText );
        add( chooseButton );
    }
    
    /**
     * Store a listener to be notified when the vaccine delivery model changes.
     * @param l -a listener wishing to receive notification of vaccine delivery change events.
     */
    public void addDeliveryChangeListener( VaccineDeliveryListener l ){
        if ( l != null ){
            deliveryListeners.add( l );
        }
    }
    
    /**
     * Let the listeners know that the selected vaccine delivery model has changed.
     * @param newDelivery - the new user specified vaccine delivery model. 
     */
    public void deliveryChanged( VaccineDelivery newDelivery ){
        deliveryListeners.forEach((pl) -> {
            pl.deliveryChanged( newDelivery );
        });
    }
    
    /**
     * Parses a text file to return a vaccine delivery model.
     * @param fileName - the name of the file to read.
     * @return - the disease described in the file.
     */
    //Note:  Until a sample text file can be provided for parsing, a sample disease will be
    //hard-coded for the purpose of producing results.
    public static VaccineDelivery getDeliveryFromFile( String fileName){
        //Create a VaccineDelivery
        VaccineDelivery vd = new VaccineDelivery();
        
        Map<Vaccine, VaccineAvailabilityModelContinuous> vaccineAvailability = new HashMap<>();
        VaccineAvailabilityModelContinuous mod = new VaccineAvailabilityModelContinuous();
        mod.setInitialAmount( 250 );
        vaccineAvailability.put( Vaccine.MODERNA, mod);
        vd.setVaccineDeliveryAvailability( vaccineAvailability);
        return vd;
    }
}
