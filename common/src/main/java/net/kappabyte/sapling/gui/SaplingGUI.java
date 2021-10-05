package net.kappabyte.sapling.gui;


import net.kappabyte.sapling.SaplingAPI;
import net.kappabyte.sapling.core.SaplingPlayer;
import net.kyori.adventure.text.Component;

import java.util.HashMap;

/**
 * A Sapling GUI
 */
public class SaplingGUI {
    Component title;
    InventoryType type;

    GUIManager.GUILookAndFeel laf = new GUIManager.GUILookAndFeel();

    HashMap<Integer, GUIComponent> components = new HashMap<Integer, GUIComponent>();

    /**
     * Create a sapling GUI
     * @param title The title of the GUI
     * @param type The type of the GUI
     */
    public SaplingGUI(Component title, InventoryType type) {
        this.title = title;
        this.type = type;
    }

    /**
     * Get the title of the GUI
     * @return the GUI title.
     */
    public final Component getTitle() {
        return this.title;
    }

    /**
     * Get the type of the inventory
     * @return The inventory type.
     */
    public final InventoryType getInventoryType() {
        return this.type;
    }

    /**
     * Add a component to the GUI
     * @param x The x coordinate in the GUI to place the component. (0-8)
     * @param y The y coordinate in the GIU to place the component. (0-invheight)
     * @param component The component to add to the GUI
     */
    public void addComponent(int x, int y, GUIComponent component) {
        components.put(y * 9 + x, component);
    }

    /**
     * Get all the components in the GUI.
     * @return The components in the GUI.
     */
    public HashMap<Integer, GUIComponent> getComponents() {
        return this.components;
    }

    /**
     * Get the look and feel of the GUI
     * @return The look and feel of the GUI.
     */
    public final GUIManager.GUILookAndFeel getLookAndFeel() {
        if(laf != null) return laf;

        return SaplingAPI.getInstance().getGUIManager().defaultLookAndFeel;
    }

    /**
     * The type of GUI.
     */
    public enum InventoryType {
        CHEST_1_ROW(1),
        CHEST_2_ROW(2),
        CHEST_3_ROW(3),
        CHEST_4_ROW(4),
        CHEST_5_ROW(5),
        CHEST_6_ROW(6);

        private int rows;

        InventoryType(int rows) {
            this.rows = rows;
        }

        /**
         * Get the number of rows of the inventory.
         * @return
         */
        public int getRows() {
            return rows;
        }

        /**
         * Get the size of the inventory.
         * @return
         */
        public int getSize() {
            return 9 * rows;
        }
    }
}
