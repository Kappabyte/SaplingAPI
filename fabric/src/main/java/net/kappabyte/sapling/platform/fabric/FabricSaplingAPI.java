package net.kappabyte.sapling.platform.fabric;

import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.server.ServerStartCallback;
import net.kappabyte.sapling.SaplingAPI;
import net.kappabyte.sapling.core.SaplingPlayer;
import net.kappabyte.sapling.gui.GUIManager;
import net.kappabyte.sapling.platform.Platform;
import net.kappabyte.sapling.platform.fabric.core.FabricSaplingPlayer;
import net.kappabyte.sapling.platform.fabric.gui.FabricGUIManager;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.fabric.FabricServerAudiences;
import net.kyori.adventure.text.Component;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;

import java.util.UUID;

public class FabricSaplingAPI extends SaplingAPI {

    GUIManager guiManager = new FabricGUIManager();

    MinecraftServer server;

    public FabricSaplingAPI(ModInitializer mod) {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nInit\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        ServerLifecycleEvents.SERVER_STARTING.register(this::onStart);

    }

    private void onStart(MinecraftServer server) {
        this.server = server;
        System.out.println(">>>>>>>>>>>> Server Starting!");
        return;
    }

    public static FabricSaplingAPI getFabricInstance() {
        return (FabricSaplingAPI) getInstance();
    }

    public FabricServerAudiences getServerAudience() {
        return FabricServerAudiences.of(server);
    }

    @Override
    public Platform getPlatform() {
        return Platform.FABRIC;
    }

    @Override
    public GUIManager getGUIManager() {
        return guiManager;
    }

    @Override
    public SaplingPlayer getPlayerFromUniqueID(UUID uuid) {
        return new FabricSaplingPlayer(this.server.getPlayerManager().getPlayer(uuid));
    }

    @Override
    public SaplingPlayer getPlayerFromNativePlayer(Object nativePlayer) {
        return getPlayerFromUniqueID((UUID) nativePlayer);
    }
}
