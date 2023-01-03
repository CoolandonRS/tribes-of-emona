package net.numra.emonatribes.tribes.hooks.sound.listen;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public record DropItemListen(ItemStack item, PlayerEntity player) {
}
