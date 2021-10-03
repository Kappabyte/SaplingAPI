package net.kappabyte.sapling.platform.fabric.gui;

import net.kappabyte.sapling.gui.SaplingGUI;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;

import java.util.HashMap;

public class GUI implements Inventory {

    HashMap<Integer, ItemStack> items = new HashMap<>();

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
        return items.containsKey(slot) ? items.get(slot) : ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        if(!items.containsKey(slot)) return ItemStack.EMPTY;
        if(items.get(slot).getCount() <= amount) return items.remove(slot);
        items.get(slot).setCount(items.get(slot).getCount() - amount);
        return items.get(slot);
    }

    @Override
    public ItemStack removeStack(int slot) {
        return items.remove(slot);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        items.put(slot, stack);
    }

    @Override
    public void markDirty() {
        //ignore
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return true;
    }

    @Override
    public void clear() {
        items.clear();
    }
}
