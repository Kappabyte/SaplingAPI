package net.kappabyte.sapling;

import net.kappabyte.sapling.core.SaplingPlayer;
import net.kappabyte.sapling.gui.GUIManager;
import net.kappabyte.sapling.platform.Platform;

import java.util.UUID;

public abstract class SaplingAPI {

    public static void initalize(SaplingAPI instance) {
        SaplingAPI.instance = instance;


    }

    private static SaplingAPI instance;

    public static SaplingAPI getInstance() {
        return instance;
    }

    public abstract GUIManager getGUIManager();

    public abstract Platform getPlatform();
    public abstract SaplingPlayer getPlayerFromUniqueID(UUID uuid);
    public abstract SaplingPlayer getPlayerFromNativePlayer(Object nativePlayer);
}
