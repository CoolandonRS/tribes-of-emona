package net.numra.emonatribes.tribes.hooks;

import lombok.Value;
import net.numra.emonatribes.tribes.hooks.sound.speak.GenericSpeak;
@Value
public class HookClassPair {
    Class<?> listen;
    Class<? extends GenericSpeak> speak;
}
