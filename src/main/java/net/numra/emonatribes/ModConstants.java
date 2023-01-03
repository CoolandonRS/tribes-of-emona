package net.numra.emonatribes;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import net.numra.emonatribes.tribes.GlobalTribeAuthority;
import net.numra.emonatribes.tribes.hooks.HookManager;
import net.numra.emonatribes.tribes.TribeDataAuthority;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public final class ModConstants {
    public static final String prettyName = "Tribes of Emona";
    // This logger is used to write text to the console and the log file.
    // It is considered best practice to use your mod id as the logger's name.
    // That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger logger = LoggerFactory.getLogger(prettyName);
    public static final String internalName = "emonatribes";
    public static final File saveDir;
    public static final File tribeDataDir;
    public static final GlobalTribeAuthority globalTribeAuthority;
    public static final TribeDataAuthority tribeDataAuthority = new TribeDataAuthority(); // Does NOT need to be serialized/deserialized
    public static final Identifier sacrificePacketID = new Identifier(ModConstants.internalName, "sacrifice");
    public static final String saveFileExt = ".tribedata";
    public static final HookManager hookManager = new HookManager();

    static {
        saveDir = new File(FabricLoader.getInstance().getGameDir().toFile(), "emonatribes");
        if (!saveDir.mkdir() && (!saveDir.exists() || !saveDir.isDirectory())) throw new RuntimeException("Failed to create emonatribes directory");
        tribeDataDir = new File(saveDir, "tribedata");
        if (!tribeDataDir.mkdir() && (!tribeDataDir.exists() || !tribeDataDir.isDirectory())) throw new RuntimeException("Failed to create tribedata directory");

        globalTribeAuthority = GlobalTribeAuthority.deserializeGlobalTribeAuthority();
    }
}
