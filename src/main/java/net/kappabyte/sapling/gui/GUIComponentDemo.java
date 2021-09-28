package net.kappabyte.sapling.gui;

import net.kappabyte.sapling.core.SaplingPlayer;
import net.kyori.adventure.text.Component;

import java.util.Arrays;

public class GUIComponentDemo extends GUIComponent {
    @Override
    public RenderableComponentData Render(SaplingPlayer player) {
        RenderableComponentData data = new RenderableComponentData();
        data.title(Component.text("This is a test item"));
        data.icon("minecraft:player_head");
        data.description(
                Component.text("This is a demo component!"),
                Component.text("This is a demo component!")
        );

        return data;
    }
}
