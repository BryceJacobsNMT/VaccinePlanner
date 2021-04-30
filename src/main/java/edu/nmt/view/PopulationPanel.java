/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nmt.view;

import edu.nmt.model.Population;
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
        URL defaultURL = PopulationPanel.class.getClassLoader().getResource( IOUtility.POP_FILE);
        fileText.setColumns(30);
        fileText.setText(defaultURL.getPath());
        JButton chooseButton = new JButton( "From File...");
        chooseButton.addActionListener((ActionEvent ae) -> {
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter( "Text File", "txt");
            chooser.setFileFilter( filter );
            int result = chooser.showOpenDialog( null );
            if ( result == JFileChooser.APPROVE_OPTION ){
                String fileName = chooser.getSelectedFile().getName();
                fileText.setText(fileName);
                fileChanged();
            }
        });
        add( populationLabel );
        add( fileText );
        add( chooseButton );
    }
    
    /**
     * Update the UI with the new file name.
     */
    public void fileChanged() {
        String fileName = fileText.getText();
        File popFile = new File(fileName);
        Population pop = IOUtility.getPopulationFromFile(popFile);
        if (pop != null) {
            populationChanged(pop);
        } 
        else {
            System.out.println("Could not read population file: " + fileName);
        }
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
        popListeners.forEach((pl) -> {
            pl.populationChanged( newPop );
        });
    }
}
