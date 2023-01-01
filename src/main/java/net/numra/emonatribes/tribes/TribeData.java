package net.numra.emonatribes.tribes;

import net.minecraft.entity.player.PlayerEntity;
import net.numra.emonatribes.ModConstants;
import net.numra.emonatribes.data.SerializeUtil;
import net.numra.emonatribes.tribes.voloria.VolorianTribe;

import java.io.*;
import java.util.UUID;

/**
 * Per player data containing data about their tribe
 */
public class TribeData implements Serializable {
    private transient PlayerEntity player;
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

    public TribeData(PlayerEntity player) {
        this.player = player;
        joinTribe(TribeRecord.None);
    }

    public boolean isLeader() {
        return leader;
    }

    public UUID uuid() {
        return player.getUuid();
    }

    private void setPlayer(PlayerEntity player) {
        this.player = player;
    }

    public static TribeData deserialize(PlayerEntity player) {
        File file = new File(ModConstants.tribeDataDir, player.getUuidAsString() + ModConstants.saveFileExt);
        String err = "Unable to load tribedata for player " + player.getDisplayName().getString() + ", replacing";
        TribeData data = SerializeUtil.deserialize(TribeData.class, file, err, () -> new TribeData(player));
        data.setPlayer(player);
        return data;
    }

    public void serialize() {
        File file = new File(ModConstants.tribeDataDir, player.getUuidAsString() + ModConstants.saveFileExt);
        String err = "Unable to save tribedata for player " + player.getDisplayName().getString();
        SerializeUtil.serialize(this, file, err);
    }
}
