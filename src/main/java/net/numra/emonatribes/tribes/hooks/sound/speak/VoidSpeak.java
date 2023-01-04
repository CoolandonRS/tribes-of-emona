package net.numra.emonatribes.tribes.hooks.sound.speak;

import net.numra.emonatribes.tribes.hooks.sound.IncompatibleSpeakException;

import java.util.List;

public final class VoidSpeak extends GenericSpeak {
    public VoidSpeak(List<? extends GenericSpeak> list) {
        if (!list.stream().allMatch(d -> d instanceof VoidSpeak)) throw new IncompatibleSpeakException();
    }
}
