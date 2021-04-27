/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nmt.view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

/**
 * Main application frame.
 * @author bryce
 */
public class MainPanel {
    
    /**
     * Application entry point.
     * @param args - command line arguments. 
     */
    public static void main( String[] args ){
        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
        });
    }
    
    /*
    * Shows the GUI.
    */
    public static void createAndShowGUI(){
        JFrame frame = new JFrame();
        frame.setSize(2000,1200);
        frame.setVisible( true );
        
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension dim = tk.getScreenSize();
        
        int xPos = (dim.width / 2) - (frame.getWidth() / 2);
        int yPos = (dim.height / 2 ) - (frame.getHeight() / 2 );
        
        frame.setLocation( xPos, yPos );
        frame.setResizable( false );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setTitle( "Vaccine Planner");
        
        
        JTabbedPane tabPane = new JTabbedPane(JTabbedPane.TOP);
        
        PopulationPanel popPanel = new PopulationPanel();
        tabPane.addTab( "Population", null, popPanel,"Population Entry");
        
        DiseasePanel disPanel = new DiseasePanel();
        tabPane.addTab( "Disease", null, disPanel, "Disease Entry");
        
        PriorityPanel priorPanel = new PriorityPanel();
        tabPane.addTab( "Prioritization", null, priorPanel, "Prioritization Entry");
        
        VaccineDeliveryPanel devPanel = new VaccineDeliveryPanel();
        tabPane.addTab( "VaccineDelivery", null, devPanel, "Vaccine Delivery Entry");
        
        ResultPanel resPanel = new ResultPanel();
        popPanel.addPopulationChangeListener( resPanel );
        disPanel.addDiseaseChangeListener( resPanel );
        priorPanel.addPriorityChangeListener( resPanel );
        devPanel.addDeliveryChangeListener( resPanel );
        tabPane.addTab( "Results", null, resPanel, "Displays Results");
       
        frame.getContentPane().setLayout( new GridLayout(1,1));
        frame.getContentPane().add(tabPane );    
    }  
}
