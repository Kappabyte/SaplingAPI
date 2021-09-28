package net.kappabyte.sapling.core;

import net.kappabyte.sapling.SaplingAPI;

import java.util.UUID;

public interface SaplingPlayer {

    static SaplingPlayer getPlayerFromNativePlayer(Object nativePlayer) {
        return SaplingAPI.getInstance().getPlayerFromNativePlayer(nativePlayer);
    }

    static SaplingPlayer getPlayerFromUniqueID(UUID uuid) {
        return SaplingAPI.getInstance().getPlayerFromUniqueID(uuid);
    }

    UUID getUniqueID();
    void openInventory(Object inventory);
}
