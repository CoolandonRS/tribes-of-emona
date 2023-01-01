package net.numra.emonatribes.tribes;

import net.numra.emonatribes.ModConstants;
import net.numra.emonatribes.data.SerializeUtil;

import java.io.*;
import java.util.EnumMap;
import java.util.Map;

import static java.util.Map.entry;
import static net.numra.emonatribes.tribes.TribeRecord.*;

public class GlobalTribeAuthority implements Serializable {
    private final EnumMap<TribeRecord, Data> tribes;

    /**
     * @return A {@link Object#clone()} of {@link GlobalTribeAuthority}'s data so it can't be accidently modified.
     */
    public EnumMap<TribeRecord, Data> get() {
        return tribes.clone();
    }

    public boolean isCreated(TribeRecord tr) {
        return tribes.get(tr).created;
    }

    public void setCreated(TribeRecord tr, boolean val) {
        tribes.get(tr).created = val;
    }

    public boolean hasGod(TribeRecord tr) {
        return tribes.get(tr).hasGod;
    }

    public void setHasGod(TribeRecord tr, boolean val) {
        if (val && !hasHadGod(tr)) tribes.get(tr).hasHadGod = true;
        tribes.get(tr).hasGod = val;
    }
    public boolean hasHadGod(TribeRecord tr) {
        return tribes.get(tr).hasHadGod;
    }

    public GlobalTribeAuthority() {
        this.tribes = new EnumMap<>(Map.ofEntries(
                entry(None, new Data()),
                entry(Voloria, new Data())
        ));
    }

    @SuppressWarnings("ProtectedMemberInFinalClass")
    public final static class Data implements Serializable {

        protected boolean created;
        protected boolean hasGod;
        protected boolean hasHadGod;
        public Data() {
            created = false;
            hasGod = false;
            hasHadGod = false;
        }

    }
    public static GlobalTribeAuthority deserializeGlobalTribeAuthority() {
        File file = new File(ModConstants.saveDir, "gta" + ModConstants.saveFileExt);
        String err = "Failed to load GlobalTribeAuthority, replacing";
        return SerializeUtil.deserialize(GlobalTribeAuthority.class, file, err, GlobalTribeAuthority::new);
    }

    public void serializeGlobalTribeAuthority() {
        File file = new File(ModConstants.saveDir, "gta" + ModConstants.saveFileExt);
        String err = "Failed to save GlobalTribeAuthority";
        SerializeUtil.serialize(this, file, err);
    }
}
