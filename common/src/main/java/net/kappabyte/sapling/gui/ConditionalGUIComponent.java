package net.kappabyte.sapling.gui;

import net.kappabyte.sapling.core.SaplingPlayer;

/**
 * WIP - A GUI component that is conditionally rendered to the GUI
 */
public abstract class ConditionalGUIComponent extends GUIComponent {
    /**
     * A conditional handler that determines whether the component should be rendered.
     * @param player The player who is viewing the GUI
     * @param gui The GUI that is being displayed.
     * @return Whether the component should be rendered.
     */
    public abstract boolean shouldRender(SaplingPlayer player, SaplingGUI gui);
}
