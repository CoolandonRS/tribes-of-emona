package net.numra.emonatribes.tribes.hooks.sound.speak;

import lombok.EqualsAndHashCode;
import lombok.Value;
import net.numra.emonatribes.tribes.hooks.sound.IncompatibleSpeakException;

import java.util.List;

@Value
@EqualsAndHashCode(callSuper = false)
public class IntSpeak extends GenericSpeak {
    int change;

    public IntSpeak(List<? extends GenericSpeak> list) {
        int sum = 0;
        for (GenericSpeak gs : list) {
            if (gs instanceof IntSpeak is) {
                sum += is.change;
            } else throw new IncompatibleSpeakException();
        }
        this.change = sum;
    }
}


