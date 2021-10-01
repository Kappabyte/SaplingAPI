package net.kappabyte.sapling.platform.fabric.gui;

import net.kappabyte.sapling.gui.SaplingGUI;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;

import java.util.HashMap;

public class GUI implements Inventory {

    HashMap<Integer, ItemStack> items;

    SaplingGUI gui;

    public GUI(SaplingGUI gui) {
        this.gui = gui;
    }

    @Override
    public int size() {
        return gui.getInventoryType().getSize();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public ItemStack getStack(int slot) {
        return null;
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        return null;
    }

    @Override
    public ItemStack removeStack(int slot) {
        return null;
    }

    @Override
    public void setStack(int slot, ItemStack stack) {

    }

    @Override
    public void markDirty() {

    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return true;
    }

    @Override
    public void clear() {

    }
}
