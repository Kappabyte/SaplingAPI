package net.kappabyte.sapling.platform.minestom;

import net.kappabyte.sapling.SaplingAPI;
import net.kappabyte.sapling.core.SaplingPlayer;
import net.kappabyte.sapling.gui.GUIManager;
import net.kappabyte.sapling.platform.Platform;
import net.kappabyte.sapling.platform.minestom.core.MinestomSaplingPlayer;
import net.kappabyte.sapling.platform.minestom.gui.MinestomGUIManager;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.inventory.Inventory;

import java.util.UUID;

public class MinestomSaplingAPI extends SaplingAPI {

    GUIManager guiManager = new MinestomGUIManager();

    @Override
    public Platform getPlatform() {
        return Platform.MINESTOM;
    }

    @Override
    public GUIManager getGUIManager() {
        return guiManager;
    }

    @Override
    public SaplingPlayer getPlayerFromUniqueID(UUID uuid) {
        Player player = MinecraftServer.getConnectionManager().getPlayer(uuid);
        return new MinestomSaplingPlayer(player);
    }

    @Override
    public SaplingPlayer getPlayerFromNativePlayer(Object nativePlayer) {
        if(!(nativePlayer instanceof Player)) throw new IllegalArgumentException("The provided argument must be a native Minestom Player type");
        return new MinestomSaplingPlayer((Player) nativePlayer);
    }
}
