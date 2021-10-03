package net.kappabyte.sapling.platform.fabric;

import net.fabricmc.api.DedicatedServerModInitializer;

public class FabricSaplingMod implements DedicatedServerModInitializer {
    @Override
    public void onInitializeServer() {
        FabricSaplingAPI.initialize(new FabricSaplingAPI());
    }
}
