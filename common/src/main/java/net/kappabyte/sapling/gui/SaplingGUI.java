package net.kappabyte.sapling.gui;


import net.kappabyte.sapling.SaplingAPI;
import net.kappabyte.sapling.core.SaplingPlayer;
import net.kyori.adventure.text.Component;

import java.util.HashMap;

/*
Example Usage:

SaplingGUI gui = new SaplingGUI("title");
gui.addComponent(x, y, new GUIComponentDemo());
 */
public class SaplingGUI {
    Component title;
    InventoryType type;

    GUIManager.GUILookAndFeel laf = new GUIManager.GUILookAndFeel();

    HashMap<Integer, GUIComponent> components = new HashMap<Integer, GUIComponent>();

    public SaplingGUI(Component title, InventoryType type) {
        this.title = title;
        this.type = type;
    }

    public Component getTitle() {
        return this.title;
    }

    public InventoryType getInventoryType() {
        return this.type;
    }

    public void addComponent(int x, int y, GUIComponent component) {
        components.put(y * 9 + x, component);
    }

    public HashMap<Integer, GUIComponent> getComponents() {
        return this.components;
    }

    public GUIManager.GUILookAndFeel getLookAndFeel() {
        if(laf != null) return laf;

        return SaplingAPI.getInstance().getGUIManager().defaultLookAndFeel;
    }

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

        public int getRows() {
            return rows;
        }

        public int getSize() {
            return 9 * rows;
        }
    }
}
