package net.numra.emonatribes;

import lombok.experimental.UtilityClass;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import net.numra.emonatribes.tribes.GlobalTribeAuthority;
import net.numra.emonatribes.tribes.TribeDataAuthority;
import net.numra.emonatribes.tribes.hooks.HookManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

@UtilityClass
public final class ModConstants {
    public static final String prettyName = "Tribes of Emona";
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
