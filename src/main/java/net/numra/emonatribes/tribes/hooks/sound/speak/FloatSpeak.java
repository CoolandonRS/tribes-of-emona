package net.numra.emonatribes.tribes.hooks.sound.speak;

import lombok.EqualsAndHashCode;
import lombok.Value;
import net.numra.emonatribes.tribes.hooks.sound.IncompatibleSpeakException;

import java.util.List;

@Value
@EqualsAndHashCode(callSuper = false)
public class FloatSpeak extends GenericSpeak {
    float change;

    public FloatSpeak(List<? extends GenericSpeak> list) {
        float sum = 0.0F;
        for (GenericSpeak gs : list) {
            if (gs instanceof FloatSpeak fs) {
                sum += fs.change;
            } else throw new IncompatibleSpeakException();
        }
        this.change = sum;
    }
}


