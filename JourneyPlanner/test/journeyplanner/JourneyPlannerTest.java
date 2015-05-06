/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package journeyplanner;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mbax2ib2
 */
public class JourneyPlannerTest {
    
    public JourneyPlannerTest() {
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
     * Test of main method, of class JourneyPlanner.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        JourneyPlanner.main();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of calculateJourney method, of class JourneyPlanner.
     */
    @Test
    public void testCalculateJourney() {
        System.out.println("calculateJourney");
        String startStop = "Marple, Back of Beyond";
        String endStop = "Stockport, Bus Station";
        JourneyPlanner tester = new JourneyPlanner();
        tester.calculateJourney(startStop, endStop);
        int expResult = 
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
