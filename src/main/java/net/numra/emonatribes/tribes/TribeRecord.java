package net.numra.emonatribes.tribes;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.numra.emonatribes.tribes.rituals.TribalRitualType;

import java.util.EnumMap;
import java.util.Map;

import static java.util.Map.entry;
import static net.numra.emonatribes.tribes.rituals.TribalRitualType.*;

public enum TribeRecord {
    None,
    Voloria;

    public EnumMap<TribalRitualType, ItemStack> getRituals() {
        return switch (this) {
            case None -> null;
            case Voloria -> new EnumMap<>(Map.ofEntries(
                    entry(Creation, new ItemStack(Items.GOLD_INGOT, 1)),
                    entry(Membership, new ItemStack(Items.GOLD_INGOT, 1)),
                    entry(Founding, new ItemStack(Items.BARRIER, 1)), //TODO
                    entry(Curious, new ItemStack(Items.GOLD_INGOT, 1)),
                    entry(Student, new ItemStack(Items.BARRIER, 1)), //TODO
                    entry(Expert, new ItemStack(Items.BARRIER, 1)), // TODO
                    entry(Master, new ItemStack(Items.NETHERITE_SCRAP, 1)),
                    entry(Ascended, new ItemStack(Items.NETHERITE_INGOT, 1)),
                    entry(Demigod, new ItemStack(Items.BARRIER, 1)), // TODO
                    entry(Godhood, new ItemStack(Items.BARRIER, 1)) // TODO
            ));
        };
    }
}
