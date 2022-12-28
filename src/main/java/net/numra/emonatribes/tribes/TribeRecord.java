package net.numra.emonatribes.tribes;

import net.minecraft.item.ItemStack;

import java.util.EnumMap;

public enum TribeRecord {
    Voloria, None;

    public EnumMap<TribalRitualType, ItemStack> getRituals() {
        return switch (this) {
            case Voloria -> new EnumMap<>(TribalRitualType.class);
            case None -> null;
        };
    }
}
