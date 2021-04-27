/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nmt.view;

import edu.nmt.model.Disease;
import edu.nmt.model.Population;
import edu.nmt.model.Prioritization;
import edu.nmt.model.VaccineAvailabilityModelContinuous;
import edu.nmt.model.VaccineContinuousModelType;
import edu.nmt.model.VaccineDelivery;
import java.awt.BasicStroke;
import java.awt.Color;
import java.util.Random;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

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
        
        //Until there is a way to initialize the variables to a user specified model
        //we will hard code the dependencies.
        pop = PopulationPanel.getPopulationFromFile(null);
        disease = DiseasePanel.getDiseaseFromFile( null );
        priority = PriorityPanel.getPriorityFromFile( null );
        delivery = VaccineDeliveryPanel.getDeliveryFromFile(null);
        
        buildResultsPanel();
    }
    
    @Override
    public void populationChanged( Population p ){
       pop = p;
    }
    
    @Override
    public void diseaseChanged( Disease dis ){
        disease = dis;
    }
    
    @Override
    public void priorityChanged( Prioritization prior ){
        priority = prior;
    }
    
    @Override
    public void deliveryChanged( VaccineDelivery del ){
        delivery = del;
    }
      
    /**
     * Display the model results.
     */
    private void buildResultsPanel(){
        JPanel vaccAvailable = generateVaccineAvailabilityGraph();
        add( vaccAvailable );
        JPanel caseGraph = generateCaseGraph();
        add( caseGraph );
        
    }
    
    private JPanel generateCaseGraph(){
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        final String INFECTIONS = "Infections";
        final String HOSPITALIZATIONS = "Hospitalizations";
        final String DEATH = "Deaths";
        Random rand = new Random();
        int deathBound = 150;
        int hospBound = 500;
        for ( int i = 0; i < 31; i++ ){
            int bound = rand.nextInt(1000);
            dataset.addValue(bound,INFECTIONS, i+"");
            dataset.addValue( rand.nextInt( hospBound), HOSPITALIZATIONS,i+"");
            dataset.addValue( rand.nextInt(deathBound), DEATH, i+"" );
        }
        
        //Create the graph
        JFreeChart barChart = ChartFactory.createBarChart( "Cases by Report Date", "Date", 
               "Count", dataset, PlotOrientation.VERTICAL, true, true, false);      
        CategoryPlot plot = barChart.getCategoryPlot();
        plot.setBackgroundPaint(Color.white);
        plot.setRangeGridlinesVisible( true );
        plot.setRangeGridlinePaint( Color.BLACK );
        ChartPanel pane = new ChartPanel( barChart );
        return pane;
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
