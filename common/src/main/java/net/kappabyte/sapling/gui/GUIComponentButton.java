package net.kappabyte.sapling.gui;

import net.kappabyte.sapling.core.SaplingPlayer;
import net.kyori.adventure.text.Component;

import java.util.function.Consumer;

/**
 * A basic GUI component that runs a action when clicked.
 */
public final class GUIComponentButton extends GUIComponent {

    Component name;
    String icon;
    Component[] description;
    Consumer<SaplingPlayer> onClick;

    /**
     * Create a Basic Button Component
     * @param icon The icon of the button
     * @param name The name of the button
     * @param description The description of the button
     * @param onClick The action to be run when the button is clicked.
     */
    public GUIComponentButton(String icon, Component name, Component[] description, Consumer<SaplingPlayer> onClick) {
        this.icon = icon;
        this.name = name;
        this.description = description;
        this.onClick = onClick;
    }

    @Override
    public RenderableComponentData Render(SaplingPlayer player, SaplingGUI gui) {
        RenderableComponentData data = new RenderableComponentData();
        data.icon(icon);
        data.title(name);
        data.description(description);
        return data;
    }

    @Override
    public ClickResponse OnClick(SaplingPlayer player, SaplingGUI gui, ClickType type) {
        onClick.accept(player);
        return ClickResponse.NOTHING;
    }
}
