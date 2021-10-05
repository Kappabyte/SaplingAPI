package net.kappabyte.sapling.gui;

import net.kappabyte.sapling.core.SaplingPlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;

import java.util.function.Consumer;

public class BasicGUIComponents {
    public GUIComponent GUIComponentBack(Consumer<SaplingPlayer> backEvent) {
        return new GUIComponentButton("arrow", Component.text("Back").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC, false).decoration(TextDecoration.BOLD, true), new Component[] {Component.text("Click to go back.").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)}, backEvent);
    }
}
