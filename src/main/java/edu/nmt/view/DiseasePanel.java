/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nmt.view;

import edu.nmt.model.Disease;
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
 * View for entering disease information.
 * @author bryce
 */
public class DiseasePanel extends JPanel{
    
    private final List<DiseaseListener> diseaseListeners;
    
    private JTextField fileText;
    
    /**
     * Constructor.
     */
    public DiseasePanel(){
        diseaseListeners = new ArrayList<>();
        JLabel diseaseLabel = new JLabel( "Disease:");
        //For now the design appears to be to read from a file rather than using the
        //database.
        fileText = new JTextField();
        fileText.setColumns(30);
        URL defaultURL = DiseasePanel.class.getClassLoader().getResource( IOUtility.DIS_FILE);
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
        add( diseaseLabel );
        add( fileText );
        add( chooseButton );
    }
    
    /**
     * Update the UI with the new file name.
     */
    public void fileChanged() {
        String fileName = fileText.getText();
        File disFile = new File(fileName);
        Disease dis = IOUtility.getDiseaseFromFile(disFile);
        if (dis != null) {
            diseaseChanged(dis);
        }
        else {
            System.out.println( "Could not get disease from file: "+fileName);
        }
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
}
