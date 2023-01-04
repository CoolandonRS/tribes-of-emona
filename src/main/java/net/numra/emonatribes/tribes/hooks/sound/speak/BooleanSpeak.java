package net.numra.emonatribes.tribes.hooks.sound.speak;

import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.Accessors;
import net.numra.emonatribes.tribes.hooks.sound.IncompatibleSpeakException;

import java.util.List;

@Value
@EqualsAndHashCode(callSuper = false)
public class BooleanSpeak extends GenericSpeak {
    boolean True; // So the Lombok getter looks good

    public BooleanSpeak(List<? extends GenericSpeak> list) {
        this(list, true);
    }

    /**
     * @param lean Will return a new BooleanSpeak where state = !lean when any state contradicts the lean.
     */
    public BooleanSpeak(List<? extends GenericSpeak> list, boolean lean) {
        for (GenericSpeak gs : list) {
            if (gs instanceof BooleanSpeak bs) {
                if (bs.isTrue() == !lean) {
                    this.True = !lean;
                    return;
                }
            } else throw new IncompatibleSpeakException();
        }
        this.True = lean;
    }
}
