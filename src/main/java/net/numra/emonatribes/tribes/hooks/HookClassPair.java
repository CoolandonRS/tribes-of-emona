package net.numra.emonatribes.tribes.hooks;

import net.numra.emonatribes.tribes.hooks.sound.speak.GenericSpeak;

public record HookClassPair(Class<?> listen, Class<? extends GenericSpeak> speak) {
}
