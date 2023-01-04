package net.numra.emonatribes.tribes.hooks.sound.listen;

import lombok.Value;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
@Value
public class BlockPosListen {
    BlockPos pos;
    PlayerEntity player;
}
