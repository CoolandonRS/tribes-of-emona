package net.numra.emonatribes.tribes;

import java.io.Serializable;
import java.util.List;

public abstract class TribeAbility implements Serializable {
    protected int level = 0;

    public boolean setLevel(int newLevel) {
        if (newLevel <= level) return false;
        level = newLevel;
        return true;
    }

    public int getLevel() {
        return level;
    }

    public abstract List<HookType> getHookList();

    // TODO: Add hooks
}
