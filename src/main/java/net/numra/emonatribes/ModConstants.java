package net.numra.emonatribes;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import net.numra.emonatribes.tribes.GlobalTribeAuthority;

import java.io.File;

public final class ModConstants {
    public static final String prettyName = "Tribes of Emona";
    public static final String internalName = "emonatribes";
    public static final File tribeDataDir;
    public static final GlobalTribeAuthority globalTribeAuthority;
    public static final Identifier sacrificePacketID = new Identifier(ModConstants.internalName, "sacrifice");

    static {
        tribeDataDir = new File(FabricLoader.getInstance().getGameDir().toFile(), "tribedata");
        if (!tribeDataDir.mkdir() && (!tribeDataDir.exists() || !tribeDataDir.isDirectory())) throw new RuntimeException("Failed to create tribedata directory");
        // TODO deserialize TribeCreationAuthority instead of hardcoding a new one each time
        globalTribeAuthority = new GlobalTribeAuthority();
    }
}
