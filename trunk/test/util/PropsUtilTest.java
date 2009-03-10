/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import java.util.Properties;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author е
 */
public class PropsUtilTest {

    public PropsUtilTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getProps method, of class PropsUtil.
     */
    @Test
    public void testGetProperties() {
        System.out.println("getProperties");
        //Properties expResult = null;
        Properties result = PropsUtil.getProps();
        assertNotNull(result);
        //assertEquals(expResult, result);
    }

}