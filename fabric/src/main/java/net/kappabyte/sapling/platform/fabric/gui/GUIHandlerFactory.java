package net.kappabyte.sapling.platform.fabric.gui;

import net.kappabyte.sapling.core.SaplingPlayer;
import net.kappabyte.sapling.gui.SaplingGUI;
import net.kappabyte.sapling.platform.fabric.FabricSaplingAPI;
import net.kappabyte.sapling.platform.fabric.core.FabricSaplingPlayer;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.fabric.FabricAudiences;
import net.kyori.adventure.platform.fabric.FabricServerAudiences;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

public class GUIHandlerFactory implements NamedScreenHandlerFactory {

    SaplingGUI gui;
    FabricGUIManager manager;

    public GUIHandlerFactory(SaplingGUI gui, FabricGUIManager manager) {
        this.gui = gui;
        this.manager = manager;
    }

    @Override
    public Text getDisplayName() {
        return FabricSaplingAPI.getFabricInstance().getServerAudience().toNative(gui.getTitle());
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        GUIHandler handler = new GUIHandler(gui, syncId, SaplingPlayer.getPlayerFromNativePlayer(player));
        manager.inventories.put(player.getUuid(), handler);
        return handler;
    }
}
