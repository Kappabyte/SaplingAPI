package net.kappabyte.sapling;

import net.kappabyte.sapling.core.SaplingPlayer;
import net.kappabyte.sapling.gui.GUIComponentDemo;
import net.kappabyte.sapling.gui.GUIManager;
import net.kappabyte.sapling.gui.SaplingGUI;
import net.kappabyte.sapling.platform.minestom.MinestomSaplingAPI;
import net.kyori.adventure.text.Component;
import net.minestom.server.MinecraftServer;

public class Test {
    public static void main(String[] args) {
        SaplingAPI.initalize(new MinestomSaplingAPI());

        SaplingPlayer player = SaplingAPI.getInstance().getPlayerFromNativePlayer(MinecraftServer.getConnectionManager().getOnlinePlayers().stream().findFirst().get());

        SaplingGUI gui = new SaplingGUI(Component.text("Example Window"), SaplingGUI.InventoryType.CHEST_1_ROW);
        gui.addComponent(5, 0, new GUIComponentDemo());

        SaplingAPI.getInstance().getGUIManager().ShowToPlayer(player, gui);
    }
}
