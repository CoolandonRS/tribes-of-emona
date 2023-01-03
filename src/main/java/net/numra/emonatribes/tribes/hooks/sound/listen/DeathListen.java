package net.numra.emonatribes.tribes.hooks.sound.listen;

import lombok.Value;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
@Value
public class DeathListen {
    DamageSource source;
    PlayerEntity playerEntity;
}
