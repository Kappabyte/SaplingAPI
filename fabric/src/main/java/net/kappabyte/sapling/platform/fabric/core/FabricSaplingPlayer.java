package net.kappabyte.sapling.platform.fabric.core;

import net.kappabyte.sapling.core.SaplingPlayer;
import net.kappabyte.sapling.platform.fabric.gui.GUIHandlerFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.UUID;

public class FabricSaplingPlayer implements SaplingPlayer {

    PlayerEntity player = null;

    public FabricSaplingPlayer(PlayerEntity player) {
        this.player = player;
    }

    @Override
    public String getName() {
        return player.getEntityName();
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
        if(!(inventory instanceof GUIHandlerFactory)) throw new IllegalArgumentException("The provided argument must be a native Fabric Inventory type");
        player.openHandledScreen((GUIHandlerFactory) inventory);
    }

    @Override
    public void closeInventory() {
        ((ServerPlayerEntity)player).closeHandledScreen();
    }
}
