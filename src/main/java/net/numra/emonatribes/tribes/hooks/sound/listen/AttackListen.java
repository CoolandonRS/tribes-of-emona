package net.numra.emonatribes.tribes.hooks.sound.listen;

import lombok.Value;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

@Value
public class AttackListen {
    Entity target;
    float amount;
    PlayerEntity self;
}
