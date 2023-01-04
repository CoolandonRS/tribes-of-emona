package net.numra.emonatribes.tribes.hooks.sound.listen;

import lombok.Value;
import net.minecraft.entity.player.PlayerEntity;

@Value
public class IntListen {
    PlayerEntity player;
    int value;
}
