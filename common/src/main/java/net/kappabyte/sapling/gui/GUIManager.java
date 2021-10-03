package net.kappabyte.sapling.gui;

import net.kappabyte.sapling.core.SaplingPlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class GUIManager {

    public GUILookAndFeel defaultLookAndFeel;

    public abstract void ShowToPlayer(SaplingPlayer player, SaplingGUI gui);

    public abstract void ReRender(SaplingPlayer player, SaplingGUI gui);

    public abstract void handleClose(SaplingPlayer player, SaplingGUI gui);

    public void handleInventoryClick(int slot, UUID playerUUID, GUIComponent.ClickType type, SaplingGUI gui) {
        if(!gui.getComponents().containsKey(slot)) return;

        SaplingPlayer player = SaplingPlayer.getPlayerFromUniqueID(playerUUID);

        GUIComponent.ClickResponse response = gui.getComponents().get(slot).OnClick(player, gui, type);
        if(response == GUIComponent.ClickResponse.CLOSE) {
            player.closeInventory();
        } else if(response == GUIComponent.ClickResponse.RENDER) {
            ReRender(player, gui);
        }
    }

    public static class GUILookAndFeel {
        public TextColor titleTextColor = NamedTextColor.BLACK;
        public Map<TextDecoration, TextDecoration.State> titleDecorations = new HashMap<>();

        public TextColor componentTitleTextColor = NamedTextColor.GREEN;
        public Map<TextDecoration, TextDecoration.State> componentTitleDecorations = new HashMap<>() {{
            put(TextDecoration.ITALIC, TextDecoration.State.FALSE);
        }};

        public TextColor componentDescriptionTextColor = NamedTextColor.GRAY;
        public Map<TextDecoration, TextDecoration.State> componentDescriptionDecorations = new HashMap<>() {{
            put(TextDecoration.ITALIC, TextDecoration.State.FALSE);
        }};

        public Component styleTitle(String title) {
            return Component.text(title).color(titleTextColor).decorations(titleDecorations);
        }

        public Component styleComponentTitle(String componentTitle) {
            return Component.text(componentTitle).color(componentTitleTextColor).decorations(componentTitleDecorations);
        }

        public Component[] styleComponentDescription(String... descriptionText) {
            Component[] description = new Component[descriptionText.length];
            for(int i = 0; i < description.length; i++) {
                description[i] = Component.text(descriptionText[i]).color(componentDescriptionTextColor).decorations(componentDescriptionDecorations);
            }

            return description;
        }
    }
}
