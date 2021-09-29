package net.kappabyte.sapling.platform.fabric;

import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.kappabyte.sapling.SaplingAPI;
import net.kappabyte.sapling.core.SaplingPlayer;
import net.kappabyte.sapling.gui.GUIManager;
import net.kappabyte.sapling.platform.Platform;
import net.kappabyte.sapling.platform.fabric.core.FabricSaplingPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.UUID;

public class FabricSaplingAPI extends SaplingAPI {

    DedicatedServerModInitializer mod;
    MinecraftServer server;

    public FabricSaplingAPI(DedicatedServerModInitializer mod) {
        this.mod = mod;
        ServerTickEvents.START_SERVER_TICK.register(server -> this.server = server);
    }

    @Override
    public GUIManager getGUIManager() {
        return null;
    }

    @Override
    public Platform getPlatform() {
        return Platform.FABRIC;
    }

    @Override
    public SaplingPlayer getPlayerFromUniqueID(UUID uuid) {
        return new FabricSaplingPlayer(server.getPlayerManager().getPlayer(uuid));
    }

    @Override
    public SaplingPlayer getPlayerFromNativePlayer(Object nativePlayer) {
        return new FabricSaplingPlayer((ServerPlayerEntity) nativePlayer);
    }
}
