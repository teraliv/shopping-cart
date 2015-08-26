/*
 * TCSS 305 
 * 
 * ItemTest class.
 * Assignment 2 - shopping cart.
 */


package tests;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import model.Item;

import org.junit.Before;
import org.junit.Test;


/**
 * Test for the Item class.
 * 
 * @author  Alex Terikov teraliv@uw.edu
 * @version April 4, 2015
 *
 */
public class ItemTest {
    
    /** An Item to be used in the tests. */
    private Item myItem;
    
    /**
     * A method to initialize the test fixture before each test.
     */
    @Before
    // This method runs before EACH test method.
    public void setUp() {
        myItem = new Item("Pencil", new BigDecimal("5.00"), 4, new BigDecimal("10.00"));
    }

    
    /**
     * Test of single Item constructor with empty string.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testItemConstructorEmptySting() {
        new Item("", new BigDecimal("5.00")); 
    }
    
    /**
     * Test of single Item constructor with negative item price.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testItemConstructorNegativePrice() {
        new Item("Pencil", new BigDecimal("-5.00")); 
    }
    
    /**
     * Test of single Item constructor with null name value.
     */
    @Test(expected = NullPointerException.class)
    public void testItemConstructorNameNull() {
        new Item(null, new BigDecimal("5.00")); 
    }
    
    /**
     * Test of single Item constructor with null name value.
     */
    @Test(expected = NullPointerException.class)
    public void testItemConstructorPriceNull() {
        new Item("Pencil", null); 
    }
    
    /**
     * Test of bulk Item constructor with negative bulk quantity value.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testBulkItemConstructorNegativeQuantity() {
        new Item("Pencil", new BigDecimal("5.00"), -1, new BigDecimal("5.00"));
        new Item("Pencil", new BigDecimal("5.00"), 0, new BigDecimal("5.00"));
    }
    
    /**
     * Test of bulk Item constructor with negative bulk price value.
     */
    @Test(expected = NullPointerException.class)
    public void testBulkItemConstructorNullPrice() {
        new Item("Pencil", new BigDecimal("5.00"), 1, null);
    }
    
    /**
     * Test of bulk Item constructor with negative bulk price value.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testBulkItemConstructorNegativePrice() {
        new Item("Pencil", new BigDecimal("5.00"), 1, new BigDecimal("-1.00"));
    }
    
    /**
     * 
     * Test method for {@link model.Item#calculateItemTotal(int)}.
     */
    @Test
    public void testCalculateItemTotal() {
        myItem.calculateItemTotal(2); //10.00
        
        //new Item("Pencil", new BigDecimal("5.00"), 0, null);
        assertEquals(new BigDecimal("10.00"), new BigDecimal("10.00"));
    }
    
    /**
     * 
     * Test method for {@link model.Item#calculateItemTotal(int)}.
     */
    @Test
    public void testCalculateItemTotalLessQuantity() {
        myItem = new Item("Pencil", new BigDecimal("5.00"), 1, new BigDecimal("10.00"));
        myItem.calculateItemTotal(2);
    }
    
    /**
     * 
     * Test method for {@link model.Item#calculateItemTotal(int)}.
     */
    @Test
    public void testCalculateItemTotalExtras() {
        myItem = new Item("buttons", new BigDecimal("0.95"), 10, new BigDecimal("5.00"));
        myItem.calculateItemTotal(22);
    }
    
    /**
     * Test method for {@link model.Item#toString()}.
     */
    @Test
    public void testToString() {
        
        assertEquals("toString() produced an unexpected result!", 
                     "Pencil, $5.00 (4 for $10.00)", myItem.toString());
    }
    
    /**
     * Test method for {@link model.Item#equals(java.lang.Object)}.
     */
    @Test
    public void testEquals() {
        final Item item3 = new Item("Pen", new BigDecimal("2.00"));
        // an object is equal to itself - reflexive property
        assertEquals("equals() fails a test of the reflexive property.", myItem, myItem);
        assertEquals("equals() fails a test of the reflexive property.", item3, item3);
        
        // .equals() should return false if the parameter is null        
        assertNotEquals("equals() fails to return false when passed a null parameter",
                        myItem, null);
        
        // .equals() should return false if the parameter is a different type
        assertNotEquals("equals() fails to return false when passed the wrong parameter type",
                    myItem, new BigDecimal("1.10"));
        
        // the symmetric property should hold
        final Item item2 = new Item("Pencil", new BigDecimal("5.00"), 4, 
                                    new BigDecimal("10.00"));
        assertEquals("equals() fails a test of the symmetric property.", myItem, item2);
        assertEquals("equals() fails a test of the symmetric property.", item2, myItem);
        
        // Item with different name should not be considered equal
        assertFalse("equals() fails to return false when prices do not match.",
                    myItem.equals(new Item("Pen", 
                                  new BigDecimal("5.00"), 4, new BigDecimal("10.00"))));
        assertFalse(item3.equals(new Item("Tomato", new BigDecimal("2.00"))));
        
        // Item with different price should not be considered equal
        assertFalse("equals() fails to return false when prices do not match.",
                    myItem.equals(new Item("Pencil", 
                                  new BigDecimal("3.00"), 4, new BigDecimal("10.00"))));
        assertFalse(item3.equals(new Item("Pen", new BigDecimal("3.00"))));
        
        // Item with different bulk quantity should not be considered equal
        assertFalse("equals() fails to return false when bulk quantity do not match.",
                    myItem.equals(new Item("Pencil", 
                                  new BigDecimal("5.00"), 1, new BigDecimal("10.00"))));
        
        // Item with different bulk price should not be considered equal
        assertFalse("equals() fails to return false when bulk price do not match.",
                    myItem.equals(new Item("Pencil", 
                                  new BigDecimal("5.00"), 1, new BigDecimal("3.30"))));
    }
    
    
    /**
     * Test method for {@link model.Item#hashCode()}.
     */
    @Test
    public void testHashCode() {
        // Equal objects should have equal hashCode values.
        final Item item2 = new Item("Pencil", new BigDecimal("5.00"), 4,
                                    new BigDecimal("10.00"));

        assertEquals("hashCode() fails to produce identical values for"
                        + " equal ImmutablePoints", myItem.hashCode(), item2.hashCode());
    }


}
