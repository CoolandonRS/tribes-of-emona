package net.numra.emonatribes.tribes.hooks.sound.listen;

import lombok.Value;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
@Value
public class DamagedListen {
    DamageSource source;
    float amount;
    PlayerEntity playerEntity;
}
