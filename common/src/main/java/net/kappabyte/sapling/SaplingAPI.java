package net.kappabyte.sapling;

import net.kappabyte.sapling.core.SaplingPlayer;
import net.kappabyte.sapling.gui.GUIManager;
import net.kappabyte.sapling.platform.Platform;

import java.util.UUID;
import java.util.function.Consumer;

/**
 * The base class for Sapling API
 */
public abstract class SaplingAPI {

    private static SaplingAPI instance;
    private static Consumer<SaplingAPI> callback;

    /**
     * Register a callback to run when the API is initialized. If the api
     * is already initialized, the callback will be run immediately.
     * @param callback THe callback to run
     */
    public static void setInitalizedCallback(Consumer<SaplingAPI> callback) {
        if(instance != null) callback.accept(instance);

        SaplingAPI.callback = callback;
    }

    /**
     * Initialize the API. Requires a platform specific implementation of the API.
     * @param instance The instance of the API
     * @throws IllegalStateException if the API has already been initialized.
     */
    public static void initialize(SaplingAPI instance) {
        if(SaplingAPI.instance != null) throw new IllegalStateException("Sapling API is already initialized!");
        System.out.println("Initialized Sapling API!");
        SaplingAPI.instance = instance;

        callback.accept(instance);
    }

    /**
     * Get the instance of the bot.
     * @return the instance of the bot.
     */
    public static SaplingAPI getInstance() {
        return instance;
    }

    /**
     * Gets the GUI manager.
     * @return the GUI manager.
     */
    public abstract GUIManager getGUIManager();

    /**
     * Get the platform that the API is running on.
     * @return the API platform.
     */
    public abstract Platform getPlatform();

    /**
     * Get a sapling player by the player's UUID
     * @param uuid the UUID of the player
     * @return A Sapling API player.
     */
    public abstract SaplingPlayer getPlayerFromUniqueID(UUID uuid);

    /**
     * Get a sapling player by a native platform player.
     * @param nativePlayer the native player
     * @return A Sapling API player.
     */
    public abstract SaplingPlayer getPlayerFromNativePlayer(Object nativePlayer);
}
