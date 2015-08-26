/*
 * TCSS 305 Assignment 2 - Shopping Cart
 */

package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.image.BufferedImage;
import java.text.NumberFormat;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import model.Item;
import model.ItemOrder;
import model.ShoppingCart;

/**
 * ShoppingFrame provides the user interface for a shopping cart program.
 * 
 * @author Marty Stepp
 * @author Daniel M. Zimmerman (Formatting and Comments)
 * @author Alan Fowler (Numerous changes to code and comments including use of BigDecimal)
 * @version Winter 2015
 */
public final class ShoppingFrame extends JFrame {

    /**
     * The Serialization ID.
     */
    private static final long serialVersionUID = 0;
    
    // constants to capture screen dimensions
    /** A ToolKit. */
    private static final Toolkit KIT = Toolkit.getDefaultToolkit();
    
    /** The Dimension of the screen. */
    private static final Dimension SCREEN_SIZE = KIT.getScreenSize();

    /**
     * The width of the text field in the GUI.
     */
    private static final int TEXT_FIELD_WIDTH = 12;
    
    /*
     * For the UW color palette and other UW branding information see
     * http://www.washington.edu/marketing/files/2012/09/WebColorPalette1.pdf 
     */
    
    /**
     * The color for some elements in the GUI.
     */
    private static final Color COLOR_1 = new Color(199, 153, 0); // UW Gold

    /**
     * The color for some elements in the GUI.
     */
    private static final Color COLOR_2 = new Color(57, 39, 91); // UW Purple

    /**
     * The shopping cart used by this GUI.
     */
    private final ShoppingCart myItems;

    /**
     * The text field used to display the total amount owed by the customer.
     */
    private final JTextField myTotal;

    /**
     * A List of the item text fields.
     */
    private final List<JTextField> myQuantities;

    /**
     * Initializes the shopping cart GUI.
     * 
     * @param theItems The list of items.
     */
    public ShoppingFrame(final List<Item> theItems) {
        // create frame and order list
        super(); // no title on the JFrame
        
        myItems = new ShoppingCart();

        // set up text field with order total
        myTotal = new JTextField("$0.00", TEXT_FIELD_WIDTH);
        
        myQuantities = new LinkedList<>();

        setupGUI(theItems);
    }

    /**
     * Setup the various parts of the GUI.
     * 
     * @param theItems The list of items.
     */
    private void setupGUI(final List<Item> theItems) {
        // hide the default JFrame icon
        final Image icon = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB_PRE);
        setIconImage(icon);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        add(makeTotalPanel(), BorderLayout.NORTH);
        add(makeItemsPanel(theItems), BorderLayout.CENTER);
        add(makeCheckBoxPanel(), BorderLayout.SOUTH);

        // adjust size to just fit
        pack();
        
        // position the frame in the center of the screen
        setLocation(SCREEN_SIZE.width / 2 - getWidth() / 2,
                    SCREEN_SIZE.height / 2 - getHeight() / 2);
        setVisible(true);
    }

    /**
     * Creates a panel to hold the total.
     * 
     * @return The created panel
     */
    private JPanel makeTotalPanel() {
        // tweak the text field so that users can't edit it, and set
        // its color appropriately

        myTotal.setEditable(false);
        myTotal.setEnabled(false);
        myTotal.setDisabledTextColor(Color.BLACK);

        // create the panel, and its label

        final JPanel p = new JPanel();
        p.setBackground(COLOR_2);
        final JLabel l = new JLabel("order total");
        l.setForeground(Color.WHITE);
        p.add(l);
        p.add(myTotal);
        return p;
    }

    /**
     * Creates a panel to hold the specified list of items.
     * 
     * @param theItems The items
     * @return The created panel
     */
    private JPanel makeItemsPanel(final List<Item> theItems) {
        final JPanel p = new JPanel(new GridLayout(theItems.size(), 1));

        for (final Item item : theItems) {
            addItem(item, p);
        }

        return p;
    }

    /**
     * Creates and returns the checkbox panel.
     * 
     * @return the checkbox panel
     */
    private JPanel makeCheckBoxPanel() {
        final JPanel p = new JPanel();
        p.setBackground(COLOR_2);
        
        final JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                myItems.clear();
                for (final JTextField field : myQuantities) {
                    field.setText("");
                }
                updateTotal();
            }
        });
        p.add(clearButton);
        
        final JCheckBox cb = new JCheckBox("customer has store membership");
        cb.setForeground(Color.BLACK);
        cb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                myItems.setMembership(cb.isSelected());
                updateTotal();
            }
        });
        p.add(cb);
        
        return p;
    }

    /**
     * Adds the specified product to the specified panel.
     * 
     * @param theItem The product to add.
     * @param thePanel The panel to add the product to.
     */
    private void addItem(final Item theItem, final JPanel thePanel) {
        final JPanel sub = new JPanel(new FlowLayout(FlowLayout.LEFT));
        sub.setBackground(COLOR_1);
        final JTextField quantity = new JTextField(3);
        myQuantities.add(quantity);
        quantity.setHorizontalAlignment(SwingConstants.CENTER);
        quantity.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                quantity.transferFocus();
            }
        });
        quantity.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(final FocusEvent theEvent) {
                updateItem(theItem, quantity);
            }
        });
        sub.add(quantity);
        final JLabel l = new JLabel(theItem.toString());
        l.setForeground(COLOR_2);
        sub.add(l);
        thePanel.add(sub);
    }

    /**
     * Updates the set of items by changing the quantity of the specified
     * product to the specified quantity.
     * 
     * @param theItem The product to update.
     * @param theQuantity The new quantity.
     */
    private void updateItem(final Item theItem, final JTextField theQuantity) {
        final String text = theQuantity.getText().trim();
        int number;
        try {
            number = Integer.parseInt(text);
            if (number < 0) {
                // disallow negative numbers
                throw new NumberFormatException();
            }
        } catch (final NumberFormatException e) {
            number = 0;
            theQuantity.setText("");
        }
        myItems.add(new ItemOrder(theItem, number));
        updateTotal();
    }

    /**
     * Updates the total displayed in the window.
     */
    private void updateTotal() {
        final double total = myItems.calculateTotal().doubleValue();
        myTotal.setText(NumberFormat.getCurrencyInstance().format(total));
    }
}

// end of class ShoppingFrame
