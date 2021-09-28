package net.kappabyte.sapling.gui;

import net.kappabyte.sapling.Manager;
import net.kappabyte.sapling.core.SaplingPlayer;

public interface GUIManager extends Manager {
    void ShowToPlayer(SaplingPlayer player, SaplingGUI gui);
}
