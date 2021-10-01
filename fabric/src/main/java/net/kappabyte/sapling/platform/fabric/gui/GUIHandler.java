package net.kappabyte.sapling.platform.fabric.gui;

import net.kappabyte.sapling.gui.SaplingGUI;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerListener;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import org.jetbrains.annotations.Nullable;

public class GUIHandler extends ScreenHandler {

    GUI inventory;

    protected GUIHandler(SaplingGUI gui, int syncId, PlayerEntity player) {
        super(switch(gui.getInventoryType()) {
            case CHEST_1_ROW -> ScreenHandlerType.GENERIC_9X1;
            case CHEST_2_ROW -> ScreenHandlerType.GENERIC_9X2;
            case CHEST_3_ROW -> ScreenHandlerType.GENERIC_9X3;
            case CHEST_4_ROW -> ScreenHandlerType.GENERIC_9X4;
            case CHEST_5_ROW -> ScreenHandlerType.GENERIC_9X5;
            case CHEST_6_ROW -> ScreenHandlerType.GENERIC_9X6;
        }, syncId);

        this.inventory = new GUI(gui);
        int n;
        int m;

        for (n = 0; n < this.inventory.size(); ++n) {
            this.addSlot(new Slot(this.inventory, n, n % 9, n / 9));
        }

        PlayerInventory playerInventory = player.getInventory();
        for (n = 0; n < 3; ++n) {
            for (m = 0; m < 9; ++m) {
                this.addSlot(new Slot(playerInventory, m + n * 9 + 9, 0, 0));
            }
        }

        for (n = 0; n < 9; ++n) {
            this.addSlot(new Slot(playerInventory, n, 0, 0));
        }
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }
}
