package net.numra.emonatribes.tribes.hooks.sound.speak;

import net.numra.emonatribes.misc.NotImplementedException;

import java.util.List;

public abstract class GenericSpeak {
    /**
     * <strong>MUST OVERRIDE</strong> <br/>
     * Init using a list of the same class, negotiating changes.
     */
    public GenericSpeak(List<? extends GenericSpeak> list) {
        throw new NotImplementedException();
    }

    /**
     * Exists so compiler doesn't yell at you, with the side effect of making it not yell at you for not overriding {@link GenericSpeak#GenericSpeak(List)}, which is very important. <br/>
     * If you forget, the game will crash due to a {@link NotImplementedException}. So don't do that.
     */
    public GenericSpeak() {

    }
}
