/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nmt.model;

import edu.nmt.dao.HibernatePrioritizationDao;
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
public class PrioritizationTest {
    
    public PrioritizationTest() {
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
     * Tests that a prioritization can be written to the database.
     */
    @Test
    public void testPrioritizationSave() {
        //Create a prioritization
        Prioritization prior = new Prioritization();
        
        Map<Occupation, PriorityGroup> occupationPriority = new HashMap<>();
        occupationPriority.put( Occupation.CONSTRUCTION, PriorityGroup.EIGHT );
        occupationPriority.put( Occupation.COUNTY, PriorityGroup.SIX);
        occupationPriority.put( Occupation.CITY, PriorityGroup.FIVE );
        prior.setOccupationPriority( occupationPriority );
        
        Map<IncreasedRisk, PriorityGroup> increasedRiskPriority = new HashMap<>();
        increasedRiskPriority.put( IncreasedRisk.LIVER_DISEASE, PriorityGroup.THREE );
        increasedRiskPriority.put( IncreasedRisk.PULMONARY_FIBROSIS, PriorityGroup.FOUR );
        increasedRiskPriority.put( IncreasedRisk.THALASSEMIA, PriorityGroup.FIVE );
        prior.setIncreasedRiskPriority( increasedRiskPriority );
        
        Map<SevereIllness,PriorityGroup> severeIllnessPriority = new HashMap<>();
        severeIllnessPriority.put( SevereIllness.CANCER, PriorityGroup.TWO );
        severeIllnessPriority.put( SevereIllness.SICKLE_CELL_DISEASE, PriorityGroup.THREE );
        severeIllnessPriority.put( SevereIllness.SMOKING, PriorityGroup.SEVEN );
        prior.setSevereIllnessPriority( severeIllnessPriority );
        
        Map<AgeGroup, PriorityGroup> agePriority = new HashMap<>();
        agePriority.put( AgeGroup.ADULT, PriorityGroup.FOUR );
        agePriority.put( AgeGroup.MIDDLE_ADULT, PriorityGroup.FIVE );
        agePriority.put( AgeGroup.OLDER_ADULT, PriorityGroup.THREE );
        prior.setAgePriority( agePriority );
  
    
        
        HibernatePrioritizationDao dao = new HibernatePrioritizationDao();
        try {
            //Save the disease to the database.
            System.out.println( "Pinging database");
            dao.ping( 5 );
            System.out.println( "Finished pinging database");
            dao.save( prior);
            
            long identifier = prior.getId();
            System.out.println( "Identifier="+identifier);
            //See if we can then retrieve the disease from the database.
            Prioritization savedPrior = dao.findById( identifier );
            System.out.println( "Saved dis="+savedPrior);
            //See if the retrieved prioritization is the same one we started with.
            Assert.assertTrue( prior.equals(savedPrior));
        }
        catch( RepositoryException re ){
            System.out.println( "Could not save prioritization to database: "+re);
            Assert.assertTrue( false );
        }
    }
}
