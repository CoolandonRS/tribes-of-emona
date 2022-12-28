package net.numra.emonatribes.tribes;

import net.numra.emonatribes.tribes.voloria.VolorianTribe;

import java.io.Serializable;

/**
 * Per player data containing data about their tribe
 */
public class TribeData implements Serializable {
    private TribeRecord tribeName;
    private Tribe tribeVal;
    private boolean leader;

    public TribeRecord getTribeName() {
        return tribeName;
    }
    public Tribe getTribeValues() {
        return tribeVal;
    }

    public void joinTribe(TribeRecord tribe) {
        joinTribe(tribe, false);
    }
    public void joinTribe(TribeRecord tribe, boolean leader) {
        this.leader = leader;
        this.tribeName = tribe;
        this.tribeVal = switch (tribe) {
            case Voloria -> new VolorianTribe();
            case None -> null;
        };
    }

    public void leaveTribe() {
        joinTribe(TribeRecord.None);
    }

    public TribeData() {
        joinTribe(TribeRecord.None);
    }
}
