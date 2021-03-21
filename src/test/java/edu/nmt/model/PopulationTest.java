/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nmt.model;

import edu.nmt.dao.HibernatePopulationDao;
import java.util.HashMap;
import java.util.Map;
import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *  Test the Population Model.
 */
public class PopulationTest {
    
    public PopulationTest() {
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

    /**
     * Tests that a population can be written to the database.
     */
    @Test
    public void testPopulationSave() {
        //Create a population
        Population pop = new Population();
        pop.setChronicMedicalConditionPercent(.1f);
        pop.setIncreasedRiskPercent(.2f);
        pop.setSevereIllnessPercent(.05f);
        final float FIXED_PERCENT = .2f;
        Map<RacialCategory,Float> racialMix = new HashMap<>();
        racialMix.put(RacialCategory.ASIAN, FIXED_PERCENT );
        racialMix.put(RacialCategory.BLACK, FIXED_PERCENT );
        racialMix.put(RacialCategory.OTHER, FIXED_PERCENT );
        racialMix.put(RacialCategory.PACIFIC_ISLANDER, FIXED_PERCENT );
        racialMix.put(RacialCategory.WHITE, FIXED_PERCENT );
        pop.setRacialMix(racialMix );
        Map<AgeGroup,Float> ageMix = new HashMap<>();
        ageMix.put(AgeGroup.CHILD,FIXED_PERCENT );
        ageMix.put(AgeGroup.ADULT, FIXED_PERCENT );
        ageMix.put(AgeGroup.MIDDLE_ADULT, FIXED_PERCENT );
        ageMix.put(AgeGroup.LESS_THAN_YEAR, .1f);
        ageMix.put(AgeGroup.YOUNG_ADULT, .1f);
        ageMix.put(AgeGroup.OLDER_ADULT, .1f );
        ageMix.put(AgeGroup.OLDER_ADULT, .1f );
        pop.setAgeMix( ageMix );
                
        
        HibernatePopulationDao dao = new HibernatePopulationDao();
        try {
            //Save the population to the database.
            System.out.println( "Pinging database");
            dao.ping( 5 );
            System.out.println( "Finished pinging database");
            dao.save( pop);
            
            long identifier = pop.getId();
            System.out.println( "Identifier="+identifier);
            //See if we can then retrieve the population from the database.
            Population savedPop = dao.findById( identifier );
            System.out.println( "Saved pop="+savedPop);
            //See if the retrieved population is the same one we started with.
            Assert.assertTrue( pop.equals(savedPop));
        }
        catch( RepositoryException re ){
            System.out.println( "Could not save population to database: "+re);
            Assert.assertTrue( false );
        }
    }
}
