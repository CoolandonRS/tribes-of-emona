package net.numra.emonatribes.tribes.hooks.sound.listen;

import lombok.Value;
import net.minecraft.entity.LivingEntity;
@Value
public class FallListen {
    LivingEntity instance;
    float distance;
    float multiplier;
}
