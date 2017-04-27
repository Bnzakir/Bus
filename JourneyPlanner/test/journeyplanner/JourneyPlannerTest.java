/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package journeyplanner;

import java.util.Date;
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
        String expResult = "383	Marple, Back of Beyond                  	20:53\n" +
"\n" +
"383	Marple, Norfolk Arms                    	20:55\n" +
"\n" +
"383	Romiley, Corcoran Drive                 	21:00\n" +
"\n" +
"383	Romiley, Train Station                  	21:04\n" +
"\n" +
"383	Romiley, Frog and Diver Arms            	21:07\n" +
"\n" +
"383	Stockport, Lower Bents Lane/Stockport Road	21:11\n" +
"\n" +
"383	Stockport, Bus Station                  	21:22";
        void result = JourneyPlanner.calculateJourney(startStop, endStop);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTimes method, of class JourneyPlanner.
     */
    @Test
    public void testGetTimes() {
        System.out.println("getTimes");
        int busStop = 0;
        Date today = null;
        int route = 0;
        int time = 0;
        int expResult = 0;
        int result = JourneyPlanner.getTimes(busStop, today, route, time);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
