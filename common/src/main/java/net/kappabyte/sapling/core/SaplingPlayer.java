package net.kappabyte.sapling.core;

import net.kappabyte.sapling.SaplingAPI;

import java.util.UUID;

/**
 * A SaplingAPI player.
 */
public interface SaplingPlayer {

    /**
     * Get a sapling player by a native platform player.
     * @param nativePlayer the native player
     * @return A Sapling API player.
     */
    static SaplingPlayer getPlayerFromNativePlayer(Object nativePlayer) {
        return SaplingAPI.getInstance().getPlayerFromNativePlayer(nativePlayer);
    }

    /**
     * Get a sapling player by the player's UUID
     * @param uuid the UUID of the player
     * @return A Sapling API player.
     */
    static SaplingPlayer getPlayerFromUniqueID(UUID uuid) {
        return SaplingAPI.getInstance().getPlayerFromUniqueID(uuid);
    }

    /**
     * Get the name of the player.
     * @return The name of the player.
     */
    String getName();

    /**
     * get the UUID of the player.
     * @return The UUID of the player.
     */
    UUID getUniqueID();

    /**
     * Get the native player associated with this Sapling Player
     * @return The native API player
     */
    Object getNativePlayer();

    /**
     * Open an inventory
     * @param inventory A native inventory object.
     */
    void openInventory(Object inventory);

    /**
     * Close the currently open inventory.
     */
    void closeInventory();
}
