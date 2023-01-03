package net.numra.emonatribes.tribes;

import net.numra.emonatribes.tribes.hooks.HookType;

import java.io.Serializable;
import java.util.Set;

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

    public abstract Set<HookType> getHookSet();

    // TODO: Add hooks
}
