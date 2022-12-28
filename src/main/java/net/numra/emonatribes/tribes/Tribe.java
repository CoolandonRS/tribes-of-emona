package net.numra.emonatribes.tribes;

import net.numra.emonatribes.misc.NotImplementedException;

import java.io.Serializable;
import java.util.List;
import java.util.Queue;

public abstract class Tribe implements Serializable {
    protected List<TribeAbility> abilities;
    protected Queue<LevelingData> levelingData;

    public void levelUp() {
        levelingData.remove().trigger(abilities);
    }

    public Tribe() {
        throw new NotImplementedException();
    }
}
