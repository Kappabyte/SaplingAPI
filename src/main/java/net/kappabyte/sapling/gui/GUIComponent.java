package net.kappabyte.sapling.gui;

import net.kappabyte.sapling.core.SaplingPlayer;
import net.kyori.adventure.text.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class GUIComponent {

    public GUIComponent() {}

    public boolean customUpdateRule() { return false; };

    public abstract RenderableComponentData Render(SaplingPlayer player);

    public ClickResponse OnClick(SaplingPlayer player, SaplingGUI gui) {
        return ClickResponse.NOTHING;
    }

    public static class RenderableComponentData {
        private Component title = Component.empty();
        private String icon = "minecraft:barrier";
        private List<Component> description = new ArrayList<>();

        public void title(Component title) {
            this.title = title;
        }

        public Component title() {
            return this.title;
        }

        public void icon(String icon) {
            this.icon = icon;
        }

        public String icon() {
            return this.icon;
        }

        public void description(List<Component> description) {
            this.description = description;
        }

        public void description(Component... description) {
            description(Arrays.asList(description));
        }

        public List<Component> description() {
            return this.description;
        }
    }

    public enum ClickResponse {
        /**
         * Do nothing after handling the click event
         */
        NOTHING,
        /**
         * Re-render the gui after handling the click event
         */
        RENDER,
        /**
         * Close the gui after handling the click event
         */
        CLOSE
    }
}
