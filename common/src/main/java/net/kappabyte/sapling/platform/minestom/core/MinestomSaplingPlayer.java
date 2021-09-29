package net.kappabyte.sapling.platform.minestom.core;

import net.kappabyte.sapling.core.SaplingPlayer;
import net.minestom.server.entity.Player;
import net.minestom.server.inventory.Inventory;

import java.util.UUID;

public class MinestomSaplingPlayer implements SaplingPlayer {

    Player player = null;

    public MinestomSaplingPlayer(Player player) {
        this.player = player;
    }

    @Override
    public String getName() {
        return player.getUsername();
    }

    @Override
    public Object getNativePlayer() {
        return player;
    }

    @Override
    public UUID getUniqueID() {
        return player.getUuid();
    }

    @Override
    public void openInventory(Object inventory) {
        if(!(inventory instanceof Inventory)) throw new IllegalArgumentException("The provided argument must be a native Minestom Inventory type");
        player.openInventory((Inventory) inventory);
    }

    @Override
    public void closeInventory() {
        player.closeInventory();
    }
}
