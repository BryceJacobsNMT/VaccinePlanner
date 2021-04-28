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
    
    private Population pop;
    
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
         //Create a population
        pop = new Population();
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
        System.out.println( "Population is: "+pop);
    }
    
    @After
    public void tearDown() {
    }
    
    /**
     * Test writing a population to a string and reading it back in.
     */
    @Test
    public void testFromString(){
        String popString = pop.toString();       
        Population pop2 = Population.fromString( popString );
        Assert.assertTrue( pop.equals(pop2));
    }

    /**
     * Tests that a population can be written to the database.
     */
    //@Test
    public void testPopulationSave() {
                
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
