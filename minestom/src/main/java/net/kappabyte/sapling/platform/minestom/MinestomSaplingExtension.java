package net.kappabyte.sapling.platform.minestom;

import net.kappabyte.sapling.SaplingAPI;
import net.minestom.server.extensions.Extension;

public class MinestomSaplingExtension extends Extension {

    @Override
    public void initialize() {
        System.out.println("Loading Minestom Sapling Support...");
        SaplingAPI.initialize(new MinestomSaplingAPI());
        System.out.println("Loaded Minestom Sapling Support!");
    }

    @Override
    public void terminate() {

    }
}
