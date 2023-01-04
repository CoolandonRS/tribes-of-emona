package net.numra.emonatribes.tribes.hooks.sound.listen;

import lombok.Value;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.entity.Entity;

@Value
public class EntityInteractListen {
    Entity other;
    Hand hand;
    PlayerEntity self;
}
