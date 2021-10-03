package net.kappabyte.sapling.platform.fabric.gui;

import net.kappabyte.sapling.core.SaplingPlayer;
import net.kappabyte.sapling.gui.GUIComponent;
import net.kappabyte.sapling.gui.GUIManager;
import net.kappabyte.sapling.gui.SaplingGUI;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.UUID;

public class FabricGUIManager extends GUIManager {

    HashMap<UUID, GUIHandler> inventories = new HashMap<>();

    @Override
    public void ShowToPlayer(SaplingPlayer player, SaplingGUI gui) {
        inventories.remove(player);
        player.openInventory(renderGUI(this, gui, player));
    }

    @Override
    public void ReRender(SaplingPlayer player, SaplingGUI gui) {
        GUIHandler handler = inventories.get(player.getUniqueID());

        if(handler == null) {
            ShowToPlayer(player, gui);
            return;
        }

        for(int slot : gui.getComponents().keySet()) {
            handler.inventory.setStack(slot, handler.renderItem(gui.getComponents().get(slot), player, gui));
        }
    }

    @Override
    public void handleClose(SaplingPlayer player, SaplingGUI gui) {
        inventories.remove(player);
    }

    private static GUIHandlerFactory renderGUI(FabricGUIManager manager, SaplingGUI gui, SaplingPlayer player) {
        GUIHandlerFactory factory = new GUIHandlerFactory(gui, manager);
        return factory;
    }
}
