package net.numra.emonatribes.tribes;

import net.minecraft.item.ItemStack;
import net.numra.emonatribes.misc.NotImplementedException;
import net.numra.emonatribes.tribes.hooks.HookType;
import net.numra.emonatribes.tribes.rituals.TribalRitualType;

import java.io.Serializable;
import java.util.*;

public abstract class Tribe implements Serializable {
    protected int maxLevel = 25;
    protected List<TribeAbility> abilities;
    protected Queue<LevelingData> levelingData;
    protected GodLevelData godData;
    protected GodLevelData demigodData;

    public Set<HookType> getHookSet() {
        Set<HookType> set = new HashSet<>();
        abilities.forEach(a -> set.addAll(a.getHookSet()));
        return set;
    }

    public void levelUp() {
        levelingData.remove().trigger(abilities);
    }

    public int getLevel() {
        return maxLevel - levelingData.size();
    }

    public static Map<TribalRitualType, ItemStack> getRituals() {
        throw new NotImplementedException();
    }

    public Tribe() {
        throw new NotImplementedException();
    }
}
