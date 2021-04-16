/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nmt.view;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

/**
 * Main application frame.
 * @author bryce
 */
public class MainPanel extends JFrame {
    
    public static void main( String[] args ){
        new MainPanel();
    }
    
    public MainPanel(){
        this.setSize(500,400);
        this.setVisible( true );
        
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension dim = tk.getScreenSize();
        
        int xPos = (dim.width / 2) - (this.getWidth() / 2);
        int yPos = (dim.height / 2 ) - (this.getHeight() / 2 );
        
        this.setLocation( xPos, yPos );
        this.setResizable( false );
        this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        this.setTitle( "Vaccine Planner");
        
        
        JTabbedPane tabPane = new JTabbedPane();
        PopulationPanel popPanel = new PopulationPanel();
        tabPane.addTab( "Population", popPanel);
        ResultPanel resPanel = new ResultPanel();
        tabPane.addTab( "Results", resPanel);
       
        this.add(tabPane );    
    }
    
}
