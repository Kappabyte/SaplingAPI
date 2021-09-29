package net.kappabyte.sapling.platform.fabric.gui;

import eu.pb4.sgui.api.gui.SimpleGui;
import net.kappabyte.sapling.core.SaplingPlayer;
import net.kappabyte.sapling.gui.GUIComponent;
import net.kappabyte.sapling.gui.GUIManager;
import net.kappabyte.sapling.gui.SaplingGUI;
import net.minecraft.block.Material;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class FabricGUIManager extends GUIManager {
    @Override
    public void ShowToPlayer(SaplingPlayer player, SaplingGUI gui) {
        renderInventory(player, gui).open();
    }

    public SimpleGui renderInventory(SaplingPlayer player, SaplingGUI gui) {
        ServerPlayerEntity playerEntity = (ServerPlayerEntity) player.getNativePlayer();
        SimpleGui inventory = new SimpleGui(getNativeInventoryType(gui), playerEntity, false);
        for(int slot : gui.getComponents().keySet()) {
            GUIComponent component = gui.getComponents().get(slot);
            inventory.setSlot(slot, renderComponent(gui, component, player));
        }

        return inventory;
    }

    private static ItemStack renderComponent(SaplingGUI gui, GUIComponent component, SaplingPlayer player) {
        GUIComponent.RenderableComponentData data = component.Render(player, gui);
        String item = data.icon();
        if(item.contains(":")) {
            item = data.icon().split(":")[1];
        }
        ItemStack stack = new ItemStack(Registry.ITEM.get(new Identifier("minecraft", item)));

        return stack;
    }

    private static ScreenHandlerType getNativeInventoryType(SaplingGUI gui) {
        switch(gui.getInventoryType()) {
            case CHEST_1_ROW: return ScreenHandlerType.GENERIC_9X1;
            case CHEST_2_ROW: return ScreenHandlerType.GENERIC_9X2;
            case CHEST_3_ROW: return ScreenHandlerType.GENERIC_9X3;
            case CHEST_4_ROW: return ScreenHandlerType.GENERIC_9X4;
            case CHEST_5_ROW: return ScreenHandlerType.GENERIC_9X5;
            case CHEST_6_ROW: return ScreenHandlerType.GENERIC_9X6;
        }

        return ScreenHandlerType.GENERIC_9X1;
    }
}
