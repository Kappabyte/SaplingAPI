package net.kappabyte.sapling.platform.minestom.gui;

import net.kappabyte.sapling.core.SaplingPlayer;
import net.kappabyte.sapling.gui.GUIComponent;
import net.kappabyte.sapling.gui.GUIManager;
import net.kappabyte.sapling.gui.SaplingGUI;
import net.minestom.server.inventory.Inventory;
import net.minestom.server.inventory.InventoryType;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;

public class MinestomGUIManager implements GUIManager {

    @Override
    public void ShowToPlayer(SaplingPlayer player, SaplingGUI gui) {
        player.openInventory(renderGUI(gui, player));
    }

    private static Inventory renderGUI(SaplingGUI gui, SaplingPlayer player) {
        Inventory inventory = new Inventory(getNativeInventoryType(gui), gui.getTitle());
        for(int slot : gui.getComponents().keySet()) {
            inventory.setItemStack(slot, renderComponent(gui.getComponents().get(slot), player));
        }

        return inventory;
    }

    private static ItemStack renderComponent(GUIComponent component, SaplingPlayer player) {
        GUIComponent.RenderableComponentData data = component.Render(player);
        Material material = Material.fromNamespaceId(data.icon());
        if(material == null) material = Material.BARRIER;
        return ItemStack.builder(material).displayName(data.title()).lore(data.description()).build();
    }

    private static InventoryType getNativeInventoryType(SaplingGUI gui) {
        switch(gui.getInventoryType()) {
            case CHEST_1_ROW: return InventoryType.CHEST_1_ROW;
            case CHEST_2_ROW: return InventoryType.CHEST_2_ROW;
            case CHEST_3_ROW: return InventoryType.CHEST_3_ROW;
            case CHEST_4_ROW: return InventoryType.CHEST_4_ROW;
            case CHEST_5_ROW: return InventoryType.CHEST_5_ROW;
            case CHEST_6_ROW: return InventoryType.CHEST_6_ROW;
        }

        return InventoryType.CHEST_1_ROW;
    }
}
