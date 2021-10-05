package net.kappabyte.sapling.gui;

import net.kappabyte.sapling.core.SaplingPlayer;
import net.kyori.adventure.text.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * A GUI component that gets rendered in a GUI
 */
public abstract class GUIComponent {

    public GUIComponent() {}

    /**
     * Render Callback. This determines how the component should be displayed in the GUI
     * @param player The player that is viewing the GUI
     * @param gui The GUI the component belongs to.
     * @return The data that should be rendered to the component.
     */
    public abstract RenderableComponentData Render(SaplingPlayer player, SaplingGUI gui);

    /**
     * Click event callback. This gets called whenever the component is clicked by a player.
     * @param player The player that clicked the component.
     * @param gui The GUI the component belongs to.
     * @param type The type of click that was received.
     * @return the action that should be run after the click has been handled.
     */
    public ClickResponse OnClick(SaplingPlayer player, SaplingGUI gui, ClickType type) {
        return ClickResponse.NOTHING;
    }

    /**
     * Represents the data that a component renders to the screen.
     */
    public static class RenderableComponentData {
        private Component title = Component.empty();
        private String icon = "minecraft:barrier";
        private int count = 1;
        private CustomComponentData customData = new CustomComponentData();
        private List<Component> description = new ArrayList<>();

        /**
         * Set the title of the component
         * @param title The title of the component
         */
        public void title(Component title) {
            this.title = title;
        }

        /**
         * Get the title of the component.
         * @return The title of the component.
         */
        public Component title() {
            return this.title;
        }

        /**
         * Set the icon that should be displayed.
         * <p>Example: minecraft:barrier</p>
         * @param icon The icon that should be displayed. This should be passed in as a namespace item
         */
        public void icon(String icon) {
            this.icon = icon;
        }

        /**
         * Get the icon that is displayed in the GUI.
         * @return The icon that should be displayed.
         */
        public String icon() {
            return this.icon;
        }

        /**
         * Get the count of the item in the GUI.
         * @return The count of the item in the GUI.
         */
        public int count() { return this.count; }

        /**
         * Set the count of the item in the GUI.
         * @param count The count of the item in the GUI.
         */
        public void count(int count) { this.count = count; }

        /**
         * Set the description (lore) of the component in the GUI.
         * @param description The description of the item.
         */
        public void description(List<Component> description) {
            this.description = description;
        }

        /**
         * Set the description (lore) of the component in the GUI.
         * @param description The description of the item.
         */
        public void description(Component... description) {
            description(Arrays.asList(description));
        }

        /**
         * Get the description (lore) of the component in the GUI.
         * @return The description of the item.
         */
        public List<Component> description() {
            return this.description;
        }

        /**
         * Get the custom NBT data of the item.
         * @return The custom NBT data of the item.
         */
        public CustomComponentData data() {
            return this.customData;
        }

        /**
         * Set the custom NBT data of the item.
         * @param data The custom NBT data of the item.
         */
        public void data(CustomComponentData data) {
            this.customData = data;
        }
    }

    /**
     * The action that should be run after the Click event is handled.
     */
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

    /**
     * Custom NBT data of the component.
     */
    public static class CustomComponentData {
        private List<Enchantment> enchantments = new ArrayList<>();
        private int customModelData;

        private UUID skullOwner = null;
        private int hideFlags = 0;

        /**
         * Add an enchantment to the component.
         * @param enchantment The enchantment to add.
         */
        public void enchantment(Enchantment enchantment) {
            enchantments.add(enchantment);
        }

        /**
         * Get the enchantments on the component.
         * @return The enchantments on the component.
         */
        public List<Enchantment> enchantments() {
            return enchantments;
        }

        /**
         * Set the enchantments on the component.
         * @param enchantments The enchantments on the component.
         */
        public void enchantments(Enchantment... enchantments) {
            this.enchantments = Arrays.asList(enchantments);
        }

        /**
         * Set the component's custom model data.
         * @param customModelData The custom model data of the component.
         */
        public void customModelData(int customModelData) {
            this.customModelData = customModelData;
        }

        /**
         * Get the custom model data of the component.
         * @return The custom model data of the component.
         */
        public int customModelData() {
            return customModelData;
        }

        /**
         * Set the skull owner of the component
         * <p>Only works when the icon is set to <i>minecraft:player_head</i></p>
         * @param skullOwner The skull owner of the component.
         */
        public void skullOwner(UUID skullOwner) {
            this.skullOwner = skullOwner;
        }

        /**
         * Get the skull owner of the component.
         * @return The skull owner of the component.
         */
        public UUID skullOwner() {
            return this.skullOwner;
        }

        /**
         * Hide or show a flag on the component.
         * @param flag The flag to change the state of.
         * @param hidden Whether the flag is hidden.
         */
        public void flag(HideFlags flag, boolean hidden) {
            if(hidden) {
                this.hideFlags |= flag.getBit();
            } else {
                this.hideFlags &= ~(flag.getBit());
            }
        }

        /**
         * Get the flags on the component as a bit field.
         * @return A bit field representing the flags of the component.
         */
        public int flags() {
            return this.hideFlags;
        }

        /**
         * Get the hidden flags on the component.
         * @return THe hidden flags on the component.
         */
        public List<HideFlags> hiddenFlags() {
            List<HideFlags> flags = new ArrayList<>();
            for(HideFlags flag : HideFlags.values()) {
                if(flag(flag)) flags.add(flag);
            }

            return flags;
        }

        /**
         * Get the state of a flag on the component
         * @param flag The flag to check
         * @return Whether the flag is hidden.
         */
        public boolean flag(HideFlags flag) {
            return ((this.hideFlags >> flag.getBit()) & 1) == 1;
        }

    }

    /**
     * An enchantment on a GUI component
     */
    public static record Enchantment(String enchantment, int level) {}

    /**
     * The flags that can be hidden on the item.
     */
    public enum HideFlags {
        /**
         * Whether the enchantments should be hidden.
         */
        ENCHANTMENTS(1 << 0),
        /**
         * Whether attribute modifiers should be hidden
         */
        ATTRIBUTE_MODIFIERS(1 << 1),
        /**
         * Whether the unbreakable status of the component should be hidden.
         */
        UNBREAKABLE(1 << 2),
        /**
         * Whether the CAN DESTROY list is visible on the item.
         */
        CAN_DESTROY(1 << 3),
        /**
         * Whether the CAN PLACE ON list is visible on the item.
         */
        CAN_PLACE_ON(1 << 4),
        /**
         * Whether other attributes are hidden on the item.
         */
        OTHER(1 << 5),
        /**
         * Whether the color of dyed items is visible.
         */
        DYED(1 << 6);

        private int bit;

        HideFlags(int bit) {
            this.bit = bit;
        }

        /**
         * Get the bit of the flag in the bitfield.
         * @return
         */
        public int getBit() {
            return bit;
        }
    }

    /**
     * The type of click that was performed in the click event.
     */
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
