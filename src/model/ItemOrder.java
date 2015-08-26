/*
 * TCSS 305 
 * 
 * ItemOrder class.
 * Assignment 2 - shopping cart.
 */

package model;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * ItemOrder stores information about a purchase order for a particular item.
 * 
 * @author Alex Terikov <teraliv@uw.edu>
 * @version April 4, 2015
 *
 */
public final class ItemOrder {
    
    //instance fields
    
    /** The product item. */
    private final Item myItem;
    
    /** The product quantity. */
    private final int myQuantity;

    /**
     * Constructor that creates an item order for the given quantity of the given Item.
     * 
     * @param   theItem The product item to assign to this order.
     * @param   theQuantity The quantity to assign to this order.
     * @throws  IllegalArgumentException if theQuantity is less than 0 or equal to 0.
     * @throws  NullPointerException if theItem is null or thePrice is null.
     */
    public ItemOrder(final Item theItem, final int theQuantity) {
        
        if (theQuantity < 0) {
            throw new IllegalArgumentException("theQuantity can't be < 0");
        }
        
        myItem = Objects.requireNonNull(theItem, "myItem can't be null");
        myQuantity = theQuantity;
    }

    
    //instance methods
    
    /**
     * Calculate the price with a given quantity.
     * 
     * @return The price for this item order.
     */
    public BigDecimal calculateOrderTotal() {
        
        return myItem.calculateItemTotal(myQuantity);
    }


    /**
     * Get a reference to the Item in the order.
     * 
     * @return Returns a reference to the Item in this order.
     */
    public Item getItem() {
        return myItem;
    }

    /**
     *{@inheritDoc}
     * 
     * A String representation of this ItemOrder will be formatted like:
     * <br> "Item: buttons, $0.95 (10 for $5.00), quantity is: 22".
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(22);
        
        sb.append("Item: ");
        sb.append(myItem);
        sb.append(", quantity is: ");
        sb.append(myQuantity);
        
        return sb.toString();
    }
  
}
