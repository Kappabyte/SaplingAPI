package net.kappabyte.sapling.gui;

import net.kappabyte.sapling.core.SaplingPlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;

public class GUIComponentText extends GUIComponent {

    String icon;
    String title;
    String[] description;

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
