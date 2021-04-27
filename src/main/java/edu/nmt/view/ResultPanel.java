/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nmt.view;

import edu.nmt.model.Population;
import edu.nmt.model.VaccineAvailabilityModelContinuous;
import edu.nmt.model.VaccineContinuousModelType;
import java.awt.BasicStroke;
import java.awt.Color;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * View for displaying the results of a model.
 * @author bryce
 */
public class ResultPanel extends JPanel implements PopulationListener {
    
    /**
     * Constructor.
     */
    public ResultPanel(){
        buildResultsPanel();
    }
    
    @Override
    public void populationChanged( Population p ){
       
    }
    
    
    
    /**
     * Display the model results.
     */
    private void buildResultsPanel(){
        JPanel vaccAvailable = generateVaccineAvailabilityGraph();
        add( vaccAvailable );
        
    }
    
    /**
     * Produces a panel containing a vaccine availability graph.
     * @return - panel showing vaccine availability.
     */
    private JPanel generateVaccineAvailabilityGraph(){
        //Assume that the available vaccine follows an exponential distribution with an initial amount
        //of 500 doses and a growth factor of .2.
        VaccineContinuousModelType vcmt = VaccineContinuousModelType.EXPONENTIAL;
        VaccineAvailabilityModelContinuous vamc = new VaccineAvailabilityModelContinuous(vcmt, 500, .2f);
        
        //Generate the vaccine supply for the next month
        XYSeries vaccineSupplyNextMonth = new XYSeries( "March 2021 Vaccine Availability");
        for ( int i = 1; i <= 30; i++ ){
            vaccineSupplyNextMonth.add( i, vamc.getDoses( i ) );
        }
        
        //Add the vaccine supply to the data in the graph
        XYSeriesCollection graphData = new XYSeriesCollection();
        graphData.addSeries( vaccineSupplyNextMonth );
        
        //Create the graph
        JFreeChart chart = ChartFactory.createXYLineChart( "Vaccine Supply", "Doses", 
                "Days", graphData, PlotOrientation.HORIZONTAL, true, true, true);
        XYPlot plot = chart.getXYPlot();
        XYLineAndShapeRenderer render = new XYLineAndShapeRenderer();
        render.setSeriesPaint(0, Color.RED);
        render.setSeriesStroke(0, new BasicStroke(2.0f));
        plot.setRenderer( render );
        plot.setBackgroundPaint(Color.white);
        plot.setRangeGridlinesVisible( true );
        plot.setRangeGridlinePaint( Color.BLACK );
        ChartPanel pane = new ChartPanel(chart);
        return pane;
    }
    
}
