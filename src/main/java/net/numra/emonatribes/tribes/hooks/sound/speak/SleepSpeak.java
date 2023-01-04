package net.numra.emonatribes.tribes.hooks.sound.speak;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Value;
import net.minecraft.entity.player.PlayerEntity;
import net.numra.emonatribes.tribes.hooks.sound.IncompatibleSpeakException;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@Value
@EqualsAndHashCode(callSuper = false)
public class SleepSpeak extends GenericSpeak {
    @Nullable
    PlayerEntity.SleepFailureReason failureReason;

    public SleepSpeak(List<? extends GenericSpeak> list) {
        for (GenericSpeak gs : list) {
            if (gs instanceof SleepSpeak ss) {
                if (ss.getFailureReason() != null) {
                    this.failureReason = ss.getFailureReason();
                    return;
                }
            } else throw new IncompatibleSpeakException();
        }
        failureReason = null;
    }
}
