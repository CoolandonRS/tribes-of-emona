package net.numra.emonatribes.tribes.hooks.sound.listen;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;

public record DamagedListen(DamageSource source, float amount, PlayerEntity playerEntity) {
}
