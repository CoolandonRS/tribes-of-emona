package net.numra.emonatribes.tribes.voloria;

import net.minecraft.text.Text;
import net.numra.emonatribes.tribes.rituals.TribalRitualBlock;
import net.numra.emonatribes.tribes.TribeRecord;


public class VolorianRitualBlock extends TribalRitualBlock {


    public VolorianRitualBlock(Settings settings) {
        super(settings);
        this.ritualCosts = TribeRecord.Voloria.getRituals(); //TODO
        this.containerDisplayName = Text.of("The Heart of the Opening");
    }
}
