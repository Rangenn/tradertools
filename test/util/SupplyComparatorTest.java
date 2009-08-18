/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import entity.Product;
import entity.Supply;
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
public class SupplyComparatorTest {

    Supply o1, o2;

    public SupplyComparatorTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        o1 = new Supply();
        o2 = new Supply();
        o1.setProduct(new Product(null));
        o2.setProduct(new Product(null));
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of compare method, of class SupplyComparator.
     */
    @Test
    public void testCompareNulls() {
        System.out.println("compare nulls");
        
        SupplyComparator instance = new SupplyComparator();
        int result;

        result = instance.compare(o1, o2);
        assertEquals(result, 0);

        o1.getProduct().setTitle("qwe");
        o2.getProduct().setTitle(null);
        result = instance.compare(o1, o2);
        assertTrue(result > 0);

        o1.getProduct().setTitle(null);
        o2.getProduct().setTitle("qwe");
        result = instance.compare(o1, o2);
        assertTrue(result < 0);
    }

    /**
     * Test of compare method, of class SupplyComparator.
     */
    @Test
    public void testCompareStrings() {
        System.out.println("compare strings");
        SupplyComparator instance = new SupplyComparator();
        int result;

        o1.getProduct().setTitle("zzz");
        o2.getProduct().setTitle("qwe");
        result = instance.compare(o1, o2);
        assertTrue(result > 0);

        o1.getProduct().setTitle("zzz");
        o2.getProduct().setTitle("zZz");
        result = instance.compare(o1, o2);
        assertEquals(result, 0);

        o1.getProduct().setTitle("dfg");
        o2.getProduct().setTitle("qwe");
        result = instance.compare(o1, o2);
        assertTrue(result < 0);

    }

}