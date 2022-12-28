package net.numra.emonatribes.tribes.voloria;

import net.minecraft.text.Text;
import net.numra.emonatribes.tribes.TribalRitualBlock;
import net.numra.emonatribes.tribes.TribalRitualType;
import net.numra.emonatribes.tribes.TribeRecord;

import java.util.EnumMap;


public class VolorianRitualBlock extends TribalRitualBlock {


    public VolorianRitualBlock(Settings settings) {
        super(settings);
        this.ritualCosts = TribeRecord.Voloria.getRituals(); //TODO
        this.containerDisplayName = Text.of("The Heart of the Opening");
    }
}
