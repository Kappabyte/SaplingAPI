package net.kappabyte.sapling.platform.fabric.core;

import net.kappabyte.sapling.core.SaplingPlayer;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.UUID;

public class FabricSaplingPlayer implements SaplingPlayer {

    private ServerPlayerEntity player;

    public FabricSaplingPlayer(ServerPlayerEntity player) {
        this.player = player;
    }

    @Override
    public String getName() {
        return player.getEntityName();
    }

    @Override
    public UUID getUniqueID() {
        return player.getUuid();
    }

    @Override
    public Object getNativePlayer() {
        return player;
    }

    @Override
    public void openInventory(Object inventory) {
        //TODO
        player.openHandledScreen((NamedScreenHandlerFactory) inventory);
    }

    @Override
    public void closeInventory() {
        player.closeHandledScreen();
    }
}
