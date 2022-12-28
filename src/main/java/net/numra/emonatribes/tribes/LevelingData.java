package net.numra.emonatribes.tribes;

import java.io.Serializable;
import java.util.List;

/**
 * Used only in {@link Tribe#levelingData} to store data for tribes to level up abilities.
 * @param abilityIndex The index of the ability in {@link Tribe#abilities}
 * @param newLevel The level to set the ability to using {@link TribeAbility#setLevel}
 */
public record LevelingData(int abilityIndex, int newLevel) implements Serializable {
    /**
     * @param abilities The ability list found in {@link Tribe#abilities}
     * @return if setting the level was successful
     */
    public boolean trigger(List<TribeAbility> abilities) {
        return abilities.get(abilityIndex).setLevel(newLevel);
    }
}
