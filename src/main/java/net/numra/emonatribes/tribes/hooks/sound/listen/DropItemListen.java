package net.numra.emonatribes.tribes.hooks.sound.listen;

import lombok.Value;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

@Value
public class DropItemListen {
    ItemStack itemStack;
    PlayerEntity player;
}
