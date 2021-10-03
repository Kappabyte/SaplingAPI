package net.kappabyte.sapling.platform.fabric.gui;

import net.kappabyte.sapling.core.SaplingPlayer;
import net.kappabyte.sapling.gui.GUIComponent;
import net.kappabyte.sapling.gui.SaplingGUI;
import net.kappabyte.sapling.platform.fabric.FabricSaplingAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.minecraft.block.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtInt;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerListener;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.Nullable;

public class GUIHandler extends ScreenHandler {

    GUI inventory;
    SaplingPlayer player;

    protected GUIHandler(SaplingGUI gui, int syncId, SaplingPlayer player) {
        super(switch(gui.getInventoryType()) {
            case CHEST_1_ROW -> ScreenHandlerType.GENERIC_9X1;
            case CHEST_2_ROW -> ScreenHandlerType.GENERIC_9X2;
            case CHEST_3_ROW -> ScreenHandlerType.GENERIC_9X3;
            case CHEST_4_ROW -> ScreenHandlerType.GENERIC_9X4;
            case CHEST_5_ROW -> ScreenHandlerType.GENERIC_9X5;
            case CHEST_6_ROW -> ScreenHandlerType.GENERIC_9X6;
        }, syncId);

        this.inventory = new GUI(gui);
        this.player = player;
        int n;
        int m;

        for (n = 0; n < this.inventory.size(); ++n) {
            this.addSlot(new Slot(this.inventory, n, n % 9, n / 9));
        }

        PlayerInventory playerInventory = ((ServerPlayerEntity)player.getNativePlayer()).getInventory();
        for (n = 0; n < 3; ++n) {
            for (m = 0; m < 9; ++m) {
                this.addSlot(new Slot(playerInventory, m + n * 9 + 9, 0, 0));
            }
        }

        for (n = 0; n < 9; ++n) {
            this.addSlot(new Slot(playerInventory, n, 0, 0));
        }

        for(int slot : gui.getComponents().keySet()) {
            inventory.setStack(slot, renderItem(gui.getComponents().get(slot), player, gui));
        }
    }

    public ItemStack renderItem(GUIComponent component, SaplingPlayer player, SaplingGUI gui) {
        //Get the component render data
        GUIComponent.RenderableComponentData data = component.Render(player, gui);
        //Get the material of the component
        Item material = Registry.ITEM.get(Identifier.tryParse(data.icon()));
        //Ensure the material exists
        if(material == null) material = Registry.ITEM.get(Identifier.tryParse("minecraft:barrier"));

        //Create the item
        ItemStack stack = new ItemStack(material);
        //Apply item meta
        NbtList lore = new NbtList();
        for(Component loreLine : data.description()) {
            lore.add(NbtString.of(GsonComponentSerializer.gson().serialize(loreLine)));
        }
        NbtCompound display = new NbtCompound();
        display.put("Lore", lore);
        stack.setSubNbt("display", display);

        stack.setCustomName(FabricSaplingAPI.getFabricInstance().getServerAudience().toNative(data.title()));
        for(GUIComponent.Enchantment enchantment : data.data().enchantments()) {
            stack.addEnchantment(Registry.ENCHANTMENT.get(Identifier.tryParse(enchantment.enchantment())), enchantment.level());
        }
        stack.setSubNbt("customModelData", NbtInt.of(data.data().customModelData()));
        for(GUIComponent.HideFlags flag : data.data().hiddenFlags()) {
            stack.addHideFlag(hideFlagsToNative(flag));
        }
        if(stack.getItem().equals(Registry.ITEM.get(Identifier.tryParse("minecraft:player_head")))) {
            stack.setSubNbt("SkullOwner", NbtString.of(FabricSaplingAPI.getFabricInstance().server.getPlayerManager().getPlayer(data.data().skullOwner()).getEntityName()));
        }

        return stack;
    }

    private static ItemStack.TooltipSection hideFlagsToNative(GUIComponent.HideFlags flag) {
        return switch(flag) {
            case ENCHANTMENTS -> ItemStack.TooltipSection.ENCHANTMENTS;
            case ATTRIBUTE_MODIFIERS -> ItemStack.TooltipSection.MODIFIERS;
            case UNBREAKABLE -> ItemStack.TooltipSection.UNBREAKABLE;
            case CAN_DESTROY -> ItemStack.TooltipSection.CAN_DESTROY;
            case CAN_PLACE_ON -> ItemStack.TooltipSection.CAN_PLACE;
            case OTHER -> ItemStack.TooltipSection.ADDITIONAL;
            case DYED -> ItemStack.TooltipSection.DYE;
        };
    }

    public GUI getInventory() {
        return inventory;
    }

    public SaplingGUI getGUI() {
        return inventory.gui;
    }

    public SaplingPlayer getPlayer() {
        return player;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }
}
