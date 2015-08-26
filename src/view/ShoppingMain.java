/*
 * TCSS 305 Assignment 2 - Shopping Cart
 */

package view;

import java.awt.EventQueue;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import model.Item;

/**
 * ShoppingMain provides the main method for a simple shopping cart GUI
 * displayer and calculator.
 * 
 * @author Marty Stepp
 * @author Daniel M. Zimmerman (Formatting and Comments)
 * @author Alan Fowler (Numerous changes including use of BigDecimal and file input)
 * @version Winter 2015
 */

public final class ShoppingMain {
    
    /**
     * The filename of the file containing the items to display in the cart.
     */
    private static final String ITEM_FILE = "items.txt";

    /**
     * A private constructor, to prevent external instantiation.
     */
    private ShoppingMain() {
    }

    /**
     * Reads item information from a file and returns a List of Item objects.
     * 
     * @return a List of Item objects created from data in an input file
     */
    private static List<Item> readItemsFromFile() {
        final List<Item> items = new LinkedList<>();
        
        try (Scanner input = new Scanner(Paths.get(ITEM_FILE))) { // Java 7!
            while (input.hasNextLine()) {
                final Scanner line = new Scanner(input.nextLine());
                line.useDelimiter(";");
                final String itemName = line.next();
                final BigDecimal itemPrice = line.nextBigDecimal();
                if (line.hasNext()) {
                    final int bulkQuantity = line.nextInt();
                    final BigDecimal bulkPrice = line.nextBigDecimal();
                    items.add(new Item(itemName, itemPrice, bulkQuantity, bulkPrice));
                } else {
                    items.add(new Item(itemName, itemPrice));
                }
                line.close();
            }
        } catch (final IOException e) {
            e.printStackTrace();
        } // no finally block needed to close 'input' with the Java 7 try with resource block
    
        return items;
    }

    /**
     * The main() method - displays and runs the shopping cart GUI.
     * 
     * @param theArgs Command line arguments, ignored by this program.
     */
    public static void main(final String... theArgs) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ShoppingFrame(readItemsFromFile());     
            }
        });
    } // end main()

} // end class ShoppingMain
