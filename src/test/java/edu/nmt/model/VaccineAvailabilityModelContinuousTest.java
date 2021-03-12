/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nmt.model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Arrays;
import javax.imageio.ImageIO;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.junit.Assert;
import org.junit.Test;



/**
 * Test class for the continuous version of the vaccine availability model.
 * @author bryce
 */
public class VaccineAvailabilityModelContinuousTest {
    /**
     * Test that JFree Chart will produce a decent graph that can then be saved to a png for use
     * on a web page.
     */
    @Test
    public void testJFreeChartProofOfConcept(){
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
        
        //Export the chart as a PNG for use in the UI
        BufferedImage image = new BufferedImage( 600, 400, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();
        g2.setRenderingHint( JFreeChart.KEY_SUPPRESS_SHADOW_GENERATION, true );
        Rectangle r = new Rectangle( 0, 0, 600, 400 );
        chart.draw( g2, r );       
       
        try {
            //Save to a file
            File f = new File("/tmp/jfreeChartDemo.png");
            BufferedImage chartImage = chart.createBufferedImage(600, 400, null);
            ImageIO.write(chartImage, "png", f);

            //Test the content of the files is the same as the stored reference
            URL url = getClass().getResource("jfreeChartDemo.png");
            File referenceFile = new File(url.getPath());           
            byte[] fContents = Files.readAllBytes(f.toPath());
            byte[] referenceFileContents = Files.readAllBytes(referenceFile.toPath());
            boolean equalContent = Arrays.equals(fContents, referenceFileContents);
            Assert.assertTrue(equalContent);
        } 
        catch (IOException ioe) {
            System.out.println("Could not write png");
            Assert.assertTrue(false);
        }       
    }
  
}
