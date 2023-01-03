package net.numra.emonatribes.tribes;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
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
    @Setter(value = AccessLevel.PRIVATE)
    private transient PlayerEntity player;
    @Getter
    private TribeRecord tribeName;
    @Getter
    private Tribe tribeValues;
    @Getter
    private boolean leader;

    public void joinTribe(TribeRecord tribe) {
        joinTribe(tribe, false);
    }

    public void joinTribe(TribeRecord tribe, boolean leader) {
        this.leader = leader;
        this.tribeName = tribe;
        this.tribeValues = switch (tribe) {
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

    public UUID getUUID() {
        return player.getUuid();
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
