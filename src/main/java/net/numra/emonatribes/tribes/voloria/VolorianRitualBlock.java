package net.numra.emonatribes.tribes.voloria;

import net.minecraft.text.Text;
import net.numra.emonatribes.tribes.TribalRitualBlock;
import net.numra.emonatribes.tribes.TribalRitualType;

import java.util.EnumMap;


public class VolorianRitualBlock extends TribalRitualBlock {


    public VolorianRitualBlock(Settings settings) {
        super(settings);
        this.ritualCosts = new EnumMap<>(TribalRitualType.class); //TODO
        this.containerDisplayName = Text.of("The Heart of the Opening");
    }
}
