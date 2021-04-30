/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nmt.view;

import edu.nmt.model.DailyInfectionStatus;
import edu.nmt.model.Disease;
import edu.nmt.model.Grapher;
import edu.nmt.model.Population;
import edu.nmt.model.Prediction;
import edu.nmt.model.Prioritization;
import edu.nmt.model.VaccineDelivery;
import edu.nmt.util.ObjectUtility;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

/**
 * View for displaying the results of a model.
 * @author bryce
 */
public class ResultPanel extends JPanel implements PopulationListener, 
        DiseaseListener, PriorityListener, VaccineDeliveryListener {
    
    //A model relies on VaccineDelivery, a Population, a Prioritization of the Population,
    //and a Disease.
    private Population pop;
    private VaccineDelivery delivery;
    private Prioritization priority;
    private Disease disease;
    
    /**
     * Constructor.
     */
    public ResultPanel(){
        setMinimumSize( new Dimension(1600, 1600));
    }
    
    @Override
    public void populationChanged( Population p ){
        if ( !ObjectUtility.objectsAreEqual(p,pop)){
            pop = p;
            this.buildResultsPanel();
        }
    }
    
    @Override
    public void diseaseChanged( Disease dis ){
        if ( !ObjectUtility.objectsAreEqual( disease, dis)){
            disease = dis;
            buildResultsPanel();
        }
    }
    
    @Override
    public void priorityChanged( Prioritization prior ){
        if ( !ObjectUtility.objectsAreEqual(priority,prior)){
            priority = prior;
            buildResultsPanel();
        }
    }
    
    @Override
    public void deliveryChanged( VaccineDelivery del ){
        if ( !ObjectUtility.objectsAreEqual( delivery, del)){
            delivery = del;
            buildResultsPanel();
        }
    }
      
    /**
     * Display the model results.
     */
    private void buildResultsPanel(){
        //Run the model with the given inputs.
        if ( pop != null && disease != null && delivery != null && priority != null ){
            Prediction predict = new Prediction( pop, disease, delivery, priority );
            DailyInfectionStatus[] stats = predict.getDailyInfectionStats();

            //Generate some graphs and statistics and persist them.
            JFreeChart caseGraph = Grapher.generateCaseGraph( stats);
            JFreeChart cumGraph = Grapher.generateCumulativeCaseGraph( stats );
            JFreeChart vacGraph = Grapher.generateVaccineAvailabilityGraph( delivery, stats.length );

            this.removeAll();
            setLayout( new BoxLayout(this, BoxLayout.PAGE_AXIS));
            JPanel vaccPanel = new ChartPanel( vacGraph );
            add( vaccPanel );
            JPanel casePanel = new ChartPanel( caseGraph );
            add( casePanel );
            JPanel cumPanel = new ChartPanel( cumGraph );
            add( cumPanel );
           
            invalidate();
            repaint();
        }
        else {
            System.out.println( "One or more input elements have not been specified");
            
        }
   }
}
