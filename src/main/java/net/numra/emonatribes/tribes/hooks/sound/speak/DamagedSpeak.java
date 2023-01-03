package net.numra.emonatribes.tribes.hooks.sound.speak;

import lombok.EqualsAndHashCode;
import lombok.Value;
import net.numra.emonatribes.tribes.hooks.sound.IncompatibleSpeakException;

import java.util.List;

@Value
@EqualsAndHashCode(callSuper = false)
public class DamagedSpeak extends GenericSpeak {
    float amountChange;

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


