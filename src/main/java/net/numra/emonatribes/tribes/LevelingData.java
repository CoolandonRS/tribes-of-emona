package net.numra.emonatribes.tribes;

import lombok.Value;

import java.io.Serializable;
import java.util.List;

/**
 * Used only in {@link Tribe#levelingData} to store data for tribes to level up abilities.
 */
@Value
public class LevelingData implements Serializable {
    /**
     * The index of the ability in {@link Tribe#abilities}
     */
    int abilityIndex;
    /**
     * The level to set the ability to using {@link TribeAbility#setLevel}
     */
    int newLevel;
    /**
     * @param abilities The ability list found in {@link Tribe#abilities}
     * @return if setting the level was successful
     */
    public boolean trigger(List<TribeAbility> abilities) {
        return abilities.get(abilityIndex).setLevel(newLevel);
    }
}
