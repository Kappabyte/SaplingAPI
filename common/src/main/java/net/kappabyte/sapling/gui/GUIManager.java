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

/**
 * The GUI management system
 */
public abstract class GUIManager {

    /**
     * The default look and feel of the GUI.
     */
    public GUILookAndFeel defaultLookAndFeel;

    /**
     * She the GUI to a player
     * @param player The player to show the GUI to
     * @param gui The GUI to show to the player
     */
    public abstract void ShowToPlayer(SaplingPlayer player, SaplingGUI gui);

    /**
     * Re-render the GUI for a player, without reopening the window
     * @param player The player who has the GUI open
     * @param gui The GUI to refresh
     */
    public abstract void ReRender(SaplingPlayer player, SaplingGUI gui);

    /**
     * Handle a gui close
     * @param player The player that was viewing the gui
     * @param gui The gui that was closed.
     */
    public abstract void handleClose(SaplingPlayer player, SaplingGUI gui);

    /**
     * Handle a gui click
     * @param slot The slot that was clicked
     * @param playerUUID THe UUID of the player who clicked the component.
     * @param type The type of click that was received.
     * @param gui The gui that was clicked.
     */
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

    /**
     * The look and feel of a GUI.
     */
    public static class GUILookAndFeel {
        private TextColor titleTextColor = NamedTextColor.BLACK;
        private Map<TextDecoration, TextDecoration.State> titleDecorations = new HashMap<>();

        private TextColor componentTitleTextColor = NamedTextColor.GREEN;
        private Map<TextDecoration, TextDecoration.State> componentTitleDecorations = new HashMap<>() {{
            put(TextDecoration.ITALIC, TextDecoration.State.FALSE);
        }};

        private TextColor componentDescriptionTextColor = NamedTextColor.GRAY;
        private Map<TextDecoration, TextDecoration.State> componentDescriptionDecorations = new HashMap<>() {{
            put(TextDecoration.ITALIC, TextDecoration.State.FALSE);
        }};

        /**
         * Apply the style to the title of the GUI
         * @param title The text of the title.
         * @return The applied title style
         */
        public Component styleTitle(String title) {
            return Component.text(title).color(titleTextColor).decorations(titleDecorations);
        }

        /**
         * Apply the style to the title of the component
         * @param componentTitle The title of the component.
         * @return The applied component title style.
         */
        public Component styleComponentTitle(String componentTitle) {
            return Component.text(componentTitle).color(componentTitleTextColor).decorations(componentTitleDecorations);
        }

        /**
         * Apply the style to the description of the component.
         * @param descriptionText The description of the component.
         * @return The applied component description style.
         */
        public Component[] styleComponentDescription(String... descriptionText) {
            Component[] description = new Component[descriptionText.length];
            for(int i = 0; i < description.length; i++) {
                description[i] = Component.text(descriptionText[i]).color(componentDescriptionTextColor).decorations(componentDescriptionDecorations);
            }

            return description;
        }
    }
}
