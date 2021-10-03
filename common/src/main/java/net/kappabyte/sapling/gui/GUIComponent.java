package net.kappabyte.sapling.gui;

import net.kappabyte.sapling.core.SaplingPlayer;
import net.kyori.adventure.text.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public abstract class GUIComponent {

    public GUIComponent() {}

    public abstract RenderableComponentData Render(SaplingPlayer player, SaplingGUI gui);

    public ClickResponse OnClick(SaplingPlayer player, SaplingGUI gui, ClickType type) {
        return ClickResponse.NOTHING;
    }

    public static class RenderableComponentData {
        private Component title = Component.empty();
        private String icon = "minecraft:barrier";
        private int count = 1;
        private CustomComponentData customData = new CustomComponentData();
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

        public int count() { return this.count; }

        public void count(int count) { this.count = count; }

        public void description(List<Component> description) {
            this.description = description;
        }

        public void description(Component... description) {
            description(Arrays.asList(description));
        }

        public List<Component> description() {
            return this.description;
        }

        public CustomComponentData data() {
            return this.customData;
        }

        public void data(CustomComponentData data) {
            this.customData = data;
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

    public static class CustomComponentData {
        private List<Enchantment> enchantments = new ArrayList<>();
        private int customModelData;

        private UUID skullOwner = null;
        private int hideFlags = 0;

        public void enchantment(Enchantment enchantment) {
            enchantments.add(enchantment);
        }

        public List<Enchantment> enchantments() {
            return enchantments;
        }

        public void enchantments(Enchantment... enchantments) {
            this.enchantments = Arrays.asList(enchantments);
        }

        public void customModelData(int customModelData) {
            this.customModelData = customModelData;
        }

        public int customModelData() {
            return customModelData;
        }

        public void skullOwner(UUID skullOwner) {
            this.skullOwner = skullOwner;
        }

        public UUID skullOwner() {
            return this.skullOwner;
        }

        public void flag(HideFlags flag, boolean hidden) {
            if(hidden) {
                this.hideFlags |= flag.getBit();
            } else {
                this.hideFlags &= ~(flag.getBit());
            }
        }

        public int flags() {
            return this.hideFlags;
        }

        public List<HideFlags> hiddenFlags() {
            List<HideFlags> flags = new ArrayList<>();
            for(HideFlags flag : HideFlags.values()) {
                if(flag(flag)) flags.add(flag);
            }

            return flags;
        }

        public boolean flag(HideFlags flag) {
            return ((this.hideFlags >> flag.getBit()) & 1) == 1;
        }

    }

    public static record Enchantment(String enchantment, int level) {}
    public static record AttributeModifier(String modifier, int amount, String slot, int operation) {}

    public enum HideFlags {
        ENCHANTMENTS(1 << 0),
        ATTRIBUTE_MODIFIERS(1 << 1),
        UNBREAKABLE(1 << 2),
        CAN_DESTROY(1 << 3),
        CAN_PLACE_ON(1 << 4),
        OTHER(1 << 5),
        DYED(1 << 6);
        private int bit;

        HideFlags(int bit) {
            this.bit = bit;
        }

        public int getBit() {
            return bit;
        }
    }

    public enum ClickType {
        LEFT_CLICK,
        RIGHT_CLICK,
        MIDDLE_CLICK,

        SHIFT_CLICK,

        LEFT_DRAGGING,
        RIGHT_DRAGGING,

        DOUBLE_CLICK,
        DROP
    }
}
