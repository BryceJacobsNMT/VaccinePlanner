/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nmt.view;

import edu.nmt.model.VaccineDelivery;
import edu.nmt.util.IOUtility;
import java.awt.event.ActionEvent;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
        URL defaultURL = VaccineDeliveryPanel.class.getClassLoader().getResource( IOUtility.VACC_FILE);
        fileText.setText( defaultURL.getPath() );
        JButton chooseButton = new JButton( "From File...");
        chooseButton.addActionListener((ActionEvent ae) -> {
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter( "Text File", "txt");
            chooser.setFileFilter( filter );
            int result = chooser.showOpenDialog( null );
            if ( result == JFileChooser.APPROVE_OPTION ){
                String fileName = chooser.getSelectedFile().getName();
                fileText.setText( fileName );
                fileChanged();
            }
        });
        add( deliveryLabel );
        add( fileText );
        add( chooseButton );
    }
    
     /**
     * Update the UI with the new file name.
     */
    public void fileChanged() {
        String fileName = fileText.getText();
        File vdFile = new File(fileName);
        VaccineDelivery vd = IOUtility.getVaccineDeliveryFromFile(vdFile);
        if (vd != null) {
            deliveryChanged(vd);
        } 
        else {
            System.out.println("Could not read vaccine delivery file: " + fileName);
        }
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
}
