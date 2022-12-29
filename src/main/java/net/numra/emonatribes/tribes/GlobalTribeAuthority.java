package net.numra.emonatribes.tribes;

import java.io.Serializable;
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
        tribes.get(tr).hasGod = val;
    }

    public GlobalTribeAuthority() {
        this.tribes = new EnumMap<>(Map.ofEntries(
                entry(None, new Data()),
                entry(Voloria, new Data())
        ));
    }

    @SuppressWarnings("ProtectedMemberInFinalClass")
    public final static class Data {
        protected boolean created;
        protected boolean hasGod;

        public Data() {
            created = false;
            hasGod = false;
        }
    }
}
