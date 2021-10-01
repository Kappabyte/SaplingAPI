package net.kappabyte.sapling;

import net.kappabyte.sapling.core.SaplingPlayer;
import net.kappabyte.sapling.gui.GUIManager;
import net.kappabyte.sapling.platform.Platform;

import java.util.UUID;
import java.util.function.Consumer;

public abstract class SaplingAPI {

    private static Consumer<SaplingAPI> callback;

    public static void setInitalizedCallback(Consumer<SaplingAPI> callback) {
        if(instance != null) callback.accept(instance);

        SaplingAPI.callback = callback;
    }

    public static void initialize(SaplingAPI instance) {
        if(SaplingAPI.instance != null) throw new IllegalStateException("Sapling API is already initialized!");
        System.out.println("Initialized Sapling API!");
        SaplingAPI.instance = instance;

        callback.accept(instance);
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
