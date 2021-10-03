package net.kappabyte.sapling.gui;

import net.kappabyte.sapling.core.SaplingPlayer;

public abstract class ConditionalGUIComponent extends GUIComponent {
    public abstract boolean shouldRender(SaplingPlayer player, SaplingGUI gui);
}
