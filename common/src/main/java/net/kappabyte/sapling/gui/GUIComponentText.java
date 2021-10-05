package net.kappabyte.sapling.gui;

import net.kappabyte.sapling.core.SaplingPlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;

/**
 * A basic GUI component that displays text on an item.
 */
public final class GUIComponentText extends GUIComponent {

    String icon;
    String title;
    String[] description;

    /**
     * Create the text component.
     * @param icon The icon to use for the item.
     * @param title THe title of the item.
     * @param description The description of the item.
     */
    public GUIComponentText(String icon, String title, String... description) {
        this.icon = icon;
        this.title = title;
        this.description = description;
    }

    @Override
    public RenderableComponentData Render(SaplingPlayer player, SaplingGUI gui) {
        RenderableComponentData data = new RenderableComponentData();

        data.icon(icon);
        data.title(gui.getLookAndFeel().styleComponentTitle(title));
        data.description(gui.getLookAndFeel().styleComponentDescription(description));

        return data;
    }
}
