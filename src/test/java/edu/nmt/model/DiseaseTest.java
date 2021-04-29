/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nmt.model;

import edu.nmt.dao.HibernateDiseaseDao;
import java.util.HashMap;
import java.util.Map;
import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *  Test Disease.
 */
public class DiseaseTest {
    
    private Disease dis;
    
    public DiseaseTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
         //Create a disease
        dis = new Disease();
        
        Map<Occupation,DiseaseStatistic> occDisease = new HashMap<>();
        DiseaseStatistic ds = makeDiseaseStatistic( 0.01f, .15f, .2f, .2f );
        occDisease.put(Occupation.CITY, ds);
        ds = makeDiseaseStatistic( 0.015f, .13f, .19f, .22f );
        occDisease.put(Occupation.FINANCIAL_SERVICE, ds);
        ds = makeDiseaseStatistic( 0.008f, .12f, .3f, .25f );
        occDisease.put(Occupation.CONSTRUCTION, ds);
        dis.setOccupationDisease( occDisease );
       
        Map<IncreasedRisk,DiseaseStatistic> incDisease = new HashMap<>();
        ds = makeDiseaseStatistic( 0.02f, .15f, .2f, .2f );
        incDisease.put(IncreasedRisk.ASTHMA, ds);
        ds = makeDiseaseStatistic( 0.03f, .13f, .11f, .1f );
        incDisease.put(IncreasedRisk.CYSTIC_FIBROSIS, ds);
        dis.setIncreasedRiskDisease(incDisease);
      
        Map<SevereIllness,DiseaseStatistic> sevDisease = new HashMap<>();
        ds = makeDiseaseStatistic( 0.04f, .15f, .15f, .2f );
        sevDisease.put(SevereIllness.CANCER, ds);
        ds = makeDiseaseStatistic( 0.05f, .13f, .13f, .1f );
        sevDisease.put(SevereIllness.SMOKING, ds);
        dis.setSevereIllnessDisease(sevDisease);
   
        Map<RacialCategory,DiseaseStatistic> racDisease = new HashMap<>();
        ds = makeDiseaseStatistic( 0.01f, .15f, .17f, .1f );
        racDisease.put(RacialCategory.BLACK, ds);
        ds = makeDiseaseStatistic( 0.005f, .13f, .11f, .12f );
        racDisease.put(RacialCategory.INDIAN, ds);
        dis.setRacialDisease(racDisease);
    }
    
    @After
    public void tearDown() {
    }
    
    private DiseaseStatistic makeDiseaseStatistic( float deathRate, float hospRate, float infectRate, float spreadRate ){
        DiseaseStatistic ds = new DiseaseStatistic();
        ds.setDeathRate( .01f );
        ds.setHospitalizationRate( .15f );
        ds.setInfectionRate( 0.2f );
        return ds;
    }
    
    /**
     * Test writing a population to a string and reading it back in.
     */
    @Test
    public void testFromString(){
        String disString = dis.toString();       
        System.out.println( "Disease is "+disString);
        Disease dis2 = Disease.fromString( disString );
        Assert.assertTrue( dis.equals(dis2));
    }


    /**
     * Tests that a disease can be written to the database.
     */
    //@Test
    public void testDiseaseSave() {
           
        HibernateDiseaseDao dao = new HibernateDiseaseDao();
        try {
            //Save the disease to the database.
            System.out.println( "Pinging database");
            dao.ping( 5 );
            System.out.println( "Finished pinging database");
            dao.save( dis);
            
            long identifier = dis.getId();
            System.out.println( "Identifier="+identifier);
            //See if we can then retrieve the disease from the database.
            Disease savedDis = dao.findById( identifier );
            System.out.println( "Saved dis="+savedDis);
            //See if the retrieved disease is the same one we started with.
            Assert.assertTrue( dis.equals(savedDis));
        }
        catch( RepositoryException re ){
            System.out.println( "Could not save disease to database: "+re);
            Assert.assertTrue( false );
        }
    }
}
