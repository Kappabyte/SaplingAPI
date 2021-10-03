package net.kappabyte.sapling.platform.minestom.gui;

import net.kappabyte.sapling.gui.SaplingGUI;
import net.kyori.adventure.text.Component;
import net.minestom.server.inventory.Inventory;
import net.minestom.server.inventory.InventoryType;
import org.jetbrains.annotations.NotNull;

public class GUI extends Inventory {
    public SaplingGUI gui;

    public GUI(@NotNull InventoryType inventoryType, @NotNull Component title, SaplingGUI gui) {
        super(inventoryType, title);
        this.gui = gui;
    }
}
