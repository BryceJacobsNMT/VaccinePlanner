/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nmt.model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.util.Map;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * Produces plots showing the results of a model.
 * @author bryce
 */
public class Grapher {
    
    private static final String INFECTIONS = "Infections";
    private static final String HOSPITALIZATIONS = "Hospitalizations";
    private static final String DEATHS = "Deaths";
    private static final Color INFECT_COLOR = Color.YELLOW;
    private static final Color HOSP_COLOR = Color.RED;
    private static final Color DEATH_COLOR = Color.BLACK;
    
    /**
     * Constructor.
     */
    private Grapher(){
    }
    
    /**
     * Produces a plot of daily infections, hospitalizations, and deaths based on
     * the daily infection stats.
     * @param stats - daily infection statistics.
     * @return - a plot of daily case counts.
     */
    public static JFreeChart generateCaseGraph(DailyInfectionStatus[] stats ){
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        for ( int i = 0; i < stats.length; i++ ){
            dataset.addValue(stats[i].getInfectionCount(),INFECTIONS, i+"");
            dataset.addValue(stats[i].getHospCount(), HOSPITALIZATIONS,i+"");
            dataset.addValue(stats[i].getDeathCount(), DEATHS, i+"" );
        }
        
        //Create the graph
        JFreeChart barChart = ChartFactory.createBarChart( "Cases by Report Day", "Day", 
               "Count", dataset, PlotOrientation.VERTICAL, true, true, false);      
        CategoryPlot plot = barChart.getCategoryPlot();
        plot.setBackgroundPaint(Color.white);
        plot.setRangeGridlinesVisible( true );
        plot.setRangeGridlinePaint( Color.BLACK );
        plot.getRenderer().setSeriesPaint(0, INFECT_COLOR );
        plot.getRenderer().setSeriesPaint(1, HOSP_COLOR);
        plot.getRenderer().setSeriesPaint(2, DEATH_COLOR);
        return barChart;
    }
    
    
    public static JFreeChart generateCumulativeCaseGraph( DailyInfectionStatus[] stats ){
         //Generate the data.
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
       
        double[][] data = new double [3][stats.length];
        for ( int j = 0; j < stats.length; j++ ){
            int infect = stats[j].getInfectionCount();
            int hosp = stats[j].getHospCount();
            int death = stats[j].getDeathCount();
            double prevInfect = 0;
            double prevHosp = 0;
            double prevDeath = 0;
            if ( j > 0 ){
                prevInfect = data[0][j-1];
                prevHosp = data[1][j-1];
                prevDeath = data[2][j-1];
            }
            dataset.addValue( prevInfect + infect, INFECTIONS, j+"");
            dataset.addValue( prevHosp + hosp, HOSPITALIZATIONS, j+"");
            dataset.addValue( prevDeath+death, DEATHS, j+"");

        }
               
        //Create the graph
        JFreeChart barChart = ChartFactory.createStackedBarChart( "Cumulative Cases", "Date", 
               "Count", dataset, PlotOrientation.VERTICAL, true, true, false);      
        CategoryPlot plot = barChart.getCategoryPlot();
        plot.setBackgroundPaint(Color.white);
        plot.getRenderer().setSeriesPaint(0, INFECT_COLOR );
        plot.getRenderer().setSeriesPaint(1, HOSP_COLOR);
        plot.getRenderer().setSeriesPaint(2, DEATH_COLOR);
        
        return barChart;
    }
    
    /**
     * Generates a line graph of vaccine supply by day.
     * @param vmc - a vaccine availability model
     * @param dayCount - the number of days in the forecast.
     * @return - a line graph of vaccine availability.
     */
    public static JFreeChart generateVaccineAvailabilityGraph( VaccineDelivery vmc, int dayCount ){
       
        //Generate the vaccine supply for the next month      
        Map<Vaccine, VaccineAvailabilityModelContinuous> availMap = vmc.getVaccineDeliveryAvailability();
        XYSeriesCollection graphData = new XYSeriesCollection();
        for ( Vaccine vac : availMap.keySet()){
            XYSeries vaccineSupply = new XYSeries( vac);
            for ( int i = 1; i <= dayCount; i++ ){
                vaccineSupply.add( availMap.get(vac).getDoses( i ), i );
            }
        
            //Add the vaccine supply to the data in the graph           
            graphData.addSeries( vaccineSupply );
        }
        
        //Create the graph
        JFreeChart chart = ChartFactory.createXYLineChart( "Vaccine Supply by Company", "Doses", 
                "Days", graphData, PlotOrientation.HORIZONTAL, true, true, true);
        XYPlot plot = chart.getXYPlot();
        XYLineAndShapeRenderer render = new XYLineAndShapeRenderer();
        render.setSeriesPaint(0, Color.RED);
        render.setSeriesStroke(0, new BasicStroke(2.0f));
        plot.setRenderer( render );
        plot.setBackgroundPaint(Color.white);
        plot.setRangeGridlinesVisible( true );
        plot.setRangeGridlinePaint( Color.BLACK );
        return chart;
    }
   
}
