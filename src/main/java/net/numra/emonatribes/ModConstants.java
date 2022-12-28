package net.numra.emonatribes;

import net.fabricmc.loader.api.FabricLoader;

import java.io.File;

public final class ModConstants {
    public static final String prettyName = "Tribes of Emona";
    public static final String internalName = "emonatribes";
    public static final File tribeDataDir;

    static {
        tribeDataDir = new File(FabricLoader.getInstance().getGameDir().toFile(), "tribedata");
        if (!tribeDataDir.mkdir() && (!tribeDataDir.exists() || !tribeDataDir.isDirectory())) throw new RuntimeException("Failed to create tribedata directory");
    }
}
