package net.kappabyte.sapling.platform.minestom.gui;

import net.kappabyte.sapling.core.SaplingPlayer;
import net.kappabyte.sapling.gui.GUIComponent;
import net.kappabyte.sapling.gui.GUIManager;
import net.kappabyte.sapling.gui.SaplingGUI;
import net.kyori.adventure.text.Component;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.PlayerSkin;
import net.minestom.server.event.inventory.InventoryCloseEvent;
import net.minestom.server.inventory.Inventory;
import net.minestom.server.inventory.InventoryType;
import net.minestom.server.inventory.click.ClickType;
import net.minestom.server.inventory.condition.InventoryConditionResult;
import net.minestom.server.item.*;
import net.minestom.server.item.attribute.ItemAttribute;
import net.minestom.server.item.metadata.PlayerHeadMeta;

import java.util.HashMap;
import java.util.UUID;

public class MinestomGUIManager extends GUIManager {

    HashMap<Player, Inventory> currentPlayerInventories = new HashMap<>();

    public MinestomGUIManager() {
        super();
        MinecraftServer.getGlobalEventHandler().addListener(InventoryCloseEvent.class, (event) -> {
            if(event.getInventory() instanceof GUI) {
                handleClose(SaplingPlayer.getPlayerFromNativePlayer(event.getPlayer()), ((GUI)event.getInventory()).gui);
            }
        });
    }

    @Override
    public void ShowToPlayer(SaplingPlayer player, SaplingGUI gui) {
        currentPlayerInventories.remove(player);
        player.openInventory(renderGUI(gui, player));
    }

    @Override
    public void ReRender(SaplingPlayer player, SaplingGUI gui) {
        Inventory inventory = currentPlayerInventories.get(player);

        if(inventory == null) {
            ShowToPlayer(player, gui);
            return;
        }

        for(int slot : gui.getComponents().keySet()) {
            inventory.setItemStack(slot, renderComponent(gui, gui.getComponents().get(slot), player));
        }
    }

    @Override
    public void handleClose(SaplingPlayer player, SaplingGUI gui) {
        currentPlayerInventories.remove(player);
    }

    private Inventory renderGUI(SaplingGUI gui, SaplingPlayer player) {
        GUI inventory = new GUI(getNativeInventoryType(gui), gui.getTitle(), gui);

        for(int slot : gui.getComponents().keySet()) {
            inventory.setItemStack(slot, renderComponent(gui, gui.getComponents().get(slot), player));
        }

        inventory.addInventoryCondition((clicker, slot, clickType, result) -> {
            handleInventoryClick(slot, clicker.getUuid(), clickTypeFromNative(clickType), gui);
            result.setCancel(true);
        });

        return inventory;
    }

    private static GUIComponent.ClickType clickTypeFromNative(ClickType type) {
        return switch(type) {
            case LEFT_CLICK, CHANGE_HELD -> GUIComponent.ClickType.LEFT_CLICK;
            case RIGHT_CLICK -> GUIComponent.ClickType.RIGHT_CLICK;
            case START_SHIFT_CLICK, SHIFT_CLICK -> GUIComponent.ClickType.SHIFT_CLICK;
            case START_LEFT_DRAGGING, LEFT_DRAGGING, END_LEFT_DRAGGING -> GUIComponent.ClickType.LEFT_DRAGGING;
            case START_RIGHT_DRAGGING, END_RIGHT_DRAGGING, RIGHT_DRAGGING -> GUIComponent.ClickType.RIGHT_DRAGGING;
            case START_DOUBLE_CLICK, DOUBLE_CLICK -> GUIComponent.ClickType.DOUBLE_CLICK;
            case DROP -> GUIComponent.ClickType.DROP;
        };
    }

    private static ItemStack renderComponent(SaplingGUI gui, GUIComponent component, SaplingPlayer player) {
        //Get the component render data
        GUIComponent.RenderableComponentData data = component.Render(player, gui);
        //Get the material of the component
        Material material = Material.fromNamespaceId(data.icon());
        //Ensure the material exists
        if(material == null) material = Material.BARRIER;

        //Create the item
        ItemStackBuilder builder = ItemStack.builder(material).amount(data.count()).displayName(data.title()).lore(data.description());
        //Apply item meta
        builder = builder.meta(ItemMetaBuilder.Provider.class, (meta) -> {
            //Apply enchantments
            for(GUIComponent.Enchantment enchantment : data.data().enchantments()) {
                Enchantment e = Enchantment.fromNamespaceId(enchantment.enchantment());
                if(e != null) meta.enchantment(e, (short) enchantment.level());
            }
            //Apply custom model data and hide flags
            if(data.data().customModelData() != 0) meta.customModelData(data.data().customModelData());
            if(data.data().flags() != 0) meta.hideFlag(data.data().flags());
        });
        //Apply skull meta data if it is a skull
        if(material.equals(Material.PLAYER_HEAD)) {
            System.out.println("Applying Skull Meta!");
            builder = builder.meta(PlayerHeadMeta.class, meta -> {
                //meta.displayName(Component.text("This is a test!"));
                meta.skullOwner(data.data().skullOwner());
                meta.playerSkin(PlayerSkin.fromUuid(data.data().skullOwner().toString()));
            });
        }

        //Return the rendered item
        return builder.build();
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
