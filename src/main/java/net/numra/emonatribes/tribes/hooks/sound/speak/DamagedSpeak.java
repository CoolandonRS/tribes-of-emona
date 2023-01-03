package net.numra.emonatribes.tribes.hooks.sound.speak;

import net.numra.emonatribes.tribes.hooks.sound.IncompatibleSpeakException;

import java.util.List;

public class DamagedSpeak extends GenericSpeak {
    public final float amountChange;

    public DamagedSpeak(float amountChange) {
        this.amountChange = amountChange;
    }
    public DamagedSpeak(List<? extends GenericSpeak> list) {
        float sum = 0.0F;
        for (GenericSpeak gs : list) {
            if (gs instanceof DamagedSpeak ds) {
                sum += ds.amountChange;
            } else throw new IncompatibleSpeakException();
        }
        this.amountChange = sum;
    }
}
