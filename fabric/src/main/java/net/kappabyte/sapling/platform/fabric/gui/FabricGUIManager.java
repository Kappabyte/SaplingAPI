package net.kappabyte.sapling.platform.fabric.gui;

import net.kappabyte.sapling.core.SaplingPlayer;
import net.kappabyte.sapling.gui.GUIComponent;
import net.kappabyte.sapling.gui.GUIManager;
import net.kappabyte.sapling.gui.SaplingGUI;
import net.minecraft.item.ItemStack;

import java.util.UUID;

public class FabricGUIManager extends GUIManager {

    @Override
    public void ShowToPlayer(SaplingPlayer player, SaplingGUI gui) {
        player.openInventory(renderGUI(this, gui, player));
    }

    private static Object renderGUI(GUIManager manager, SaplingGUI gui, SaplingPlayer player) {
        return null;
    }

    private static GUIComponent.ClickType clickTypeFromNative() {
        return null;
    }

    private static ItemStack renderComponent(SaplingGUI gui, GUIComponent component, SaplingPlayer player) {
//        //Get the component render data
//        GUIComponent.RenderableComponentData data = component.Render(player, gui);
//        //Get the material of the component
//        Material material = Material.fromNamespaceId(data.icon());
//        //Ensure the material exists
//        if(material == null) material = Material.BARRIER;
//
//        //Create the item
//        ItemStackBuilder builder = ItemStack.builder(material).amount(data.count()).displayName(data.title()).lore(data.description());
//        //Apply item meta
//        builder = builder.meta(ItemMetaBuilder.Provider.class, (meta) -> {
//            //Apply enchantments
//            for(GUIComponent.Enchantment enchantment : data.data().enchantments()) {
//                Enchantment e = Enchantment.fromNamespaceId(enchantment.enchantment());
//                if(e != null) meta.enchantment(e, (short) enchantment.level());
//            }
//            //Apply custom model data and hide flags
//            if(data.data().customModelData() != 0) meta.customModelData(data.data().customModelData());
//            if(data.data().flags() != 0) meta.hideFlag(data.data().flags());
//        });
//        //Apply skull meta data if it is a skull
//        if(material.equals(Material.PLAYER_HEAD)) {
//            System.out.println("Applying Skull Meta!");
//            builder = builder.meta(PlayerHeadMeta.class, meta -> {
//                //meta.displayName(Component.text("This is a test!"));
//                meta.skullOwner(data.data().skullOwner());
//                meta.playerSkin(PlayerSkin.fromUuid(data.data().skullOwner().toString()));
//            });
//        }
//
//        //Return the rendered item
//        return builder.build();
        return null;
    }

    private static Object getNativeInventoryType(SaplingGUI gui) {
//        switch(gui.getInventoryType()) {
//            case CHEST_1_ROW: return InventoryType.CHEST_1_ROW;
//            case CHEST_2_ROW: return InventoryType.CHEST_2_ROW;
//            case CHEST_3_ROW: return InventoryType.CHEST_3_ROW;
//            case CHEST_4_ROW: return InventoryType.CHEST_4_ROW;
//            case CHEST_5_ROW: return InventoryType.CHEST_5_ROW;
//            case CHEST_6_ROW: return InventoryType.CHEST_6_ROW;
//        }
//
//        return InventoryType.CHEST_1_ROW;
        return null;
    }
}
