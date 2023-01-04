package net.numra.emonatribes.tribes.hooks;

import net.numra.emonatribes.misc.IncompatibleDataException;
import net.numra.emonatribes.tribes.hooks.sound.speak.GenericSpeak;

import java.util.function.Function;

public class GenericListener<D, R extends GenericSpeak> {
    protected Class<D> clazz;
    protected Function<D, R> executor;

    /**
     * @return The hook's speak. If the hook is registered as non-speaking to {@link HookManager} then return will be ignored.
     */
    public R execute(Object data) throws IncompatibleDataException {
        if (!data.getClass().isAssignableFrom(clazz)) throw new IncompatibleDataException();
        return executor.apply(clazz.cast(data));
    }

    public Class<D> getListenClass() {
         return this.clazz;
    }

    public GenericListener(Class<D> listenClass, Function<D, R> executor) {
        this.clazz = listenClass;
        this.executor = executor;
    }
}
