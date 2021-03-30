/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nmt.model;

import edu.nmt.dao.HibernateVaccineDeliveryDao;
import java.util.HashMap;
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
    }
    
    @After
    public void tearDown() {
    }
    
    private DiseaseStatistic makeDiseaseStatistic( float deathRate, float hospRate, float infectRate, float spreadRate ){
        DiseaseStatistic ds = new DiseaseStatistic();
        ds.setDeathRate( .01f );
        ds.setHospitalizationRate( .15f );
        ds.setInfectionRate( 0.2f );
        ds.setSpreadRate( 0.2f);
        return ds;
    }

    /**
     * Tests that a VaccineDelivery model can be written to the database.
     */
    @Test
    public void testVaccineDeliverySave() {
        //Create a VaccineDelivery
        VaccineDelivery vd = new VaccineDelivery();
        
        Map<Vaccine, VaccineAvailabilityModelContinuous> vaccineAvailability = new HashMap<>();
        VaccineAvailabilityModelContinuous mod = new VaccineAvailabilityModelContinuous();
        mod.setInitialAmount( 250 );
        vaccineAvailability.put( Vaccine.MODERNA, mod);
        vd.setVaccineAvailability( vaccineAvailability);
        
        HibernateVaccineDeliveryDao dao = new HibernateVaccineDeliveryDao();
        try {
            //Save the disease to the database.
            System.out.println( "Pinging database");
            dao.ping( 5 );
            System.out.println( "Finished pinging database");
            dao.save( vd);
            
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
}
