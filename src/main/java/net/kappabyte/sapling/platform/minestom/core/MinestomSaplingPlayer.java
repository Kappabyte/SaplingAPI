package net.kappabyte.sapling.platform.minestom.core;

import net.kappabyte.sapling.core.SaplingPlayer;
import net.minestom.server.entity.Player;

import java.util.UUID;

public class MinestomSaplingPlayer implements SaplingPlayer {

    Player player = null;

    public MinestomSaplingPlayer(Player player) {
        this.player = player;
    }

    @Override
    public UUID getUniqueID() {
        return null;
    }

    @Override
    public void openInventory(Object inventory) {

    }
}
