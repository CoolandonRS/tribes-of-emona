package net.numra.emonatribes.tribes.hooks;

import net.numra.emonatribes.misc.IncompatibleDataException;
import net.numra.emonatribes.tribes.hooks.sound.listen.DamagedListen;
import net.numra.emonatribes.tribes.hooks.sound.listen.DeathListen;
import net.numra.emonatribes.tribes.hooks.sound.listen.DropItemListen;
import net.numra.emonatribes.tribes.hooks.sound.speak.DamagedSpeak;
import net.numra.emonatribes.tribes.hooks.sound.speak.GenericSpeak;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Stream;

import static java.util.Map.entry;

public class HookManager {
    private final EnumMap<HookType, List<GenericListener<?, ?>>> hookMap;
    private final EnumMap<HookType, HookClassPair> classMap;

    public <T extends GenericListener<?, ?>> void addHook(HookType type, T hook) throws IncompatibleDataException {
        if (!hook.getDataClass().isAssignableFrom(classMap.get(type).listen())) throw new IncompatibleDataException();
        hookMap.get(type).add(hook);
    }

    /**
     * @return A list of all speak data, or an empty list if the hook doesn't speak
     */
    @Nullable
    public <R extends GenericSpeak> List<R> trigger(HookType type, Object data, Class<R> expectedReturnType) throws IncompatibleDataException {
        if (!data.getClass().isAssignableFrom(classMap.get(type).listen())) throw new IncompatibleDataException();
        Class<?> speakClass = classMap.get(type).speak();
        if (speakClass != null && !speakClass.isAssignableFrom(expectedReturnType)) throw new IncompatibleDataException();
        Stream<?> list = hookMap.get(type).stream().map(gl -> gl.execute(data));
        return speakClass == null ? List.of() : list.map(expectedReturnType::cast).toList();
    }

    public HookManager() {
        hookMap = new EnumMap<>(HookType.class);
        Arrays.stream(HookType.values()).forEach(k -> hookMap.put(k, new ArrayList<>()));
        this.classMap = new EnumMap<>(Map.ofEntries(
                entry(HookType.DropItem, new HookClassPair(DropItemListen.class, null)),
                entry(HookType.Death, new HookClassPair(DeathListen.class, null)),
                entry(HookType.Damaged, new HookClassPair(DamagedListen.class, DamagedSpeak.class))
        ));
        if (!Arrays.stream(HookType.values()).allMatch(classMap::containsKey)) throw new RuntimeException("classMap in HookManager incomplete");
    }
}
