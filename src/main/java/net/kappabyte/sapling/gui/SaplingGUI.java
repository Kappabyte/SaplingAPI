package net.kappabyte.sapling.gui;


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

    HashMap<Integer, GUIComponent> components = new HashMap<Integer, GUIComponent>();

    public SaplingGUI(Component title, InventoryType type) {
        this.title = title;
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

    public enum InventoryType {
        CHEST_1_ROW,
        CHEST_2_ROW,
        CHEST_3_ROW,
        CHEST_4_ROW,
        CHEST_5_ROW,
        CHEST_6_ROW
    }
}
