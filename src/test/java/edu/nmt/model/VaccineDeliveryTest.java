/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nmt.model;

import edu.nmt.dao.HibernateVaccineDeliveryDao;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *  Test the VaccineDeliveryl.
 */
public class VaccineDeliveryTest {
    
    private VaccineDelivery vd;
    
    public VaccineDeliveryTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
         //Create a VaccineDelivery
        vd = new VaccineDelivery();
        
        Map<Vaccine, VaccineAvailabilityModelContinuous> vaccineAvailability = new HashMap<>();
        VaccineAvailabilityModelContinuous mod = new VaccineAvailabilityModelContinuous();
        mod.setInitialAmount( 250 );
        vaccineAvailability.put( Vaccine.MODERNA, mod);
        vd.setVaccineDeliveryAvailability( vaccineAvailability);
    }
    
    @After
    public void tearDown() {
    }
    
       /**
     * Test writing a vaccine delivery to a string and reading it back in.
     */
    @Test
    public void testFromString(){
        String vdString = vd.toString();       
        System.out.println( "Delivery is "+vdString);
        VaccineDelivery vd2 = VaccineDelivery.fromString( vdString );
        Assert.assertTrue( vd.equals(vd2));
    }
    
   

    /**
     * Tests that a VaccineDelivery model can be written to the database.
     */
    //@Test
    public void testVaccineDeliverySave() {
       
        
        HibernateVaccineDeliveryDao dao = new HibernateVaccineDeliveryDao();
        try {
            //Save the disease to the database.
            System.out.println( "Pinging database");
            dao.ping( 5 );
            System.out.println( "Finished pinging database");
            dao.save( vd);
            System.out.println( "Saved delivery "+vd);
            
            long identifier = vd.getId();
            System.out.println( "Identifier="+identifier);
            //See if we can then retrieve the vaccine delivery from the database.
            VaccineDelivery savedVd = dao.findById( identifier );
            System.out.println( "Saved vd="+savedVd);
            //See if the retrieved vaccine delivery is the same one we started with.
            Assert.assertTrue( vd.equals(savedVd));
        }
        catch( RepositoryException re ){
            System.out.println( "Could not save vaccine delivery to database: "+re);
            Assert.assertTrue( false );
        }
    }
    
    /**
     * Test that we can query for vaccine deliveries.
     */
    //@Test
    public void testVaccineDeliveryFind(){
        HibernateVaccineDeliveryDao dao = new HibernateVaccineDeliveryDao();
        try {
            //Look up the first vaccine delivery from the database.
            System.out.println( "Pinging database");
            dao.ping( 5 );
            System.out.println( "Finished pinging database");
            List<VaccineDelivery> dvList = dao.getAllVaccineDeliveries();
            System.out.println( "Found delivery "+dvList.size()+" vaccine deliveries");
            Assert.assertTrue( dvList != null);
        }
        catch( RepositoryException re ){
            System.out.println( "Could not find vaccine delivery in database: "+re);
            Assert.assertTrue( false );
        }
         
    }
}
