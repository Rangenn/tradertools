/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.JDBCConnectionException;
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
public class HibUtilTest {

    public HibUtilTest() {
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
     * Test of getSession method, of class HibUtil.
     */
    @Test
    public void testGetSession() {
        System.out.println("getSession");

        Session result = null;
        try {
            result = HibUtil.getSession();
            result.beginTransaction().commit();
        }
        catch (JDBCConnectionException ex) {
            fail(ex.getMessage());
        }
        assertNotNull(result);
//        assertTrue(result.isOpen());
//        assertTrue(result.isConnected());
        Session result2 = HibUtil.getSession();
        assertSame("проверка работы в рамках 1й сессии",result2, result);
    }

    /**
     * Test of getSessionFactory method, of class HibUtil.
     */
    @Test
    public void testGetSessionFactory() {
        System.out.println("getSessionFactory");
        SessionFactory result = HibUtil.getSessionFactory();
        assertNotNull(result);
    }

}