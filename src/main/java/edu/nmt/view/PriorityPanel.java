/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nmt.view;

import edu.nmt.model.Prioritization;
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
        URL defaultURL = PriorityPanel.class.getClassLoader().getResource( IOUtility.PRIOR_FILE);
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
        add( priorityLabel );
        add( fileText );
        add( chooseButton );
    }
    
    /**
     * Update the UI with the new file name.
     */
    public void fileChanged() {
        String fileName = fileText.getText();
        File priorFile = new File(fileName);
        Prioritization priority = IOUtility.getPrioritizationFromFile(priorFile);
        if (priority != null) {
            priorityChanged(priority);
        }
        else {
            System.out.println( "Could not get prioritization from file: "+fileName);
        }
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
}
