/*
 * TCSS 305 
 * 
 * ShoppingCart class.
 * Assignment 2 - shopping cart.
 */

package model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;


/**
 * ShoppingCart stores information about the customer's overall purchase.
 * 
 * @author  Alex Terikov teraliv@uw.edu
 * @version April 4, 2015
 *
 */
public class ShoppingCart {
    
    // constants (static final fields)
    
    /** A percentage value for discount item. */
    private static final BigDecimal DISCOUNT_RATE = new BigDecimal(".10");
    
    
    //instance fields
    
    /** The shopping cart. */
    private Map<Item, BigDecimal> myShoppingCart;
    
    /** The item order. */
    //private ItemOrder myOrder;
    
    /** The order total. */
    //private BigDecimal myOrderTotal;
    
    /** The membership status. */
    private boolean myMembership;
    
    
    // constructors
    
    /**
     * Constructor that creates an empty shopping cart.
     */
    public ShoppingCart() {
        myShoppingCart =  new HashMap<Item, BigDecimal>();
    }


    /**
     * Adds an order to the shopping cart, replacing any previous order for 
     * an equivalent item with the new order.
     * 
     * @param theOrder The ItemOrder parameter to add to shopping cart.
     */
    public void add(final ItemOrder theOrder) {
        Objects.requireNonNull(theOrder, "theOrder can't be null!");
        myShoppingCart.put(theOrder.getItem(), theOrder.calculateOrderTotal());
    }

    
    /**
     * Sets whether or not the customer for this shopping cart has a 
     * store membership.
     * 
     * @param theMembership True means the customer has membership, 
     *                      False means no membership.
     */
    public void setMembership(final boolean theMembership) {
        myMembership = theMembership;
    }

    /**
     * Calculates the shopping cart total cost.
     * 
     * @return Returns the total cost of this order as BigDecimal.
     */
    public BigDecimal calculateTotal() {
        BigDecimal orderTotal = BigDecimal.ZERO;
        
        final Iterator<Item> iterator = myShoppingCart.keySet().iterator();
        
        while (iterator.hasNext()) {
            final BigDecimal currentOrderPrice = myShoppingCart.get(iterator.next());
            
            orderTotal = orderTotal.add(currentOrderPrice);
        }
        
        //if membership take %10 off the total cost
        if (myMembership) {
            if (orderTotal.compareTo(new BigDecimal("25.00")) == 1) { //myOrderTotal > $25
                final BigDecimal discount = DISCOUNT_RATE.multiply(orderTotal); 
                orderTotal = orderTotal.subtract(discount);
            }
        }
        
        return orderTotal.setScale(2, RoundingMode.HALF_EVEN);
    }
    
    /**
     * Removes all orders from the cart.
     */
    public void clear() {
        myShoppingCart =  new HashMap<Item, BigDecimal>();
    }
    
    /**
     * {@inheritDoc}
     * 
     *  A String representation of this ShoppingCart.
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(myShoppingCart);
        
        return sb.toString();
    }

}
