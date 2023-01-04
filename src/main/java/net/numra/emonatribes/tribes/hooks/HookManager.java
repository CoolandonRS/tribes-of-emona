package net.numra.emonatribes.tribes.hooks;

import net.numra.emonatribes.misc.IncompatibleDataException;
import net.numra.emonatribes.tribes.hooks.sound.listen.*;
import net.numra.emonatribes.tribes.hooks.sound.speak.FloatSpeak;
import net.numra.emonatribes.tribes.hooks.sound.speak.GenericSpeak;
import net.numra.emonatribes.tribes.hooks.sound.speak.VoidSpeak;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Stream;

import static java.util.Map.entry;
public class HookManager {
    private final EnumMap<HookType, List<GenericListener<?, ?>>> hookMap;
    private final EnumMap<HookType, HookClassPair> classMap;

    public <T extends GenericListener<?, ?>> void addHook(HookType type, T hook) throws IncompatibleDataException {
        if (!hook.getDataClass().isAssignableFrom(classMap.get(type).getListen())) throw new IncompatibleDataException();
        hookMap.get(type).add(hook);
    }

    /**
     * @return A list of all speak data, or an empty list if the hook doesn't speak
     */
    @Nullable
    public <R extends GenericSpeak> List<R> trigger(HookType type, Object data, Class<R> expectedReturnType) throws IncompatibleDataException {
        if (!data.getClass().isAssignableFrom(classMap.get(type).getListen())) throw new IncompatibleDataException();
        Class<?> speakClass = classMap.get(type).getSpeak();
        if (speakClass != VoidSpeak.class && !speakClass.isAssignableFrom(expectedReturnType)) throw new IncompatibleDataException();
        Stream<?> list = hookMap.get(type).stream().map(gl -> gl.execute(data));
        return speakClass == VoidSpeak.class ? List.of() : list.map(expectedReturnType::cast).toList();
    }

    public HookManager() {
        hookMap = new EnumMap<>(HookType.class);
        Arrays.stream(HookType.values()).forEach(k -> hookMap.put(k, new ArrayList<>()));
        this.classMap = new EnumMap<>(Map.ofEntries(
                entry(HookType.DropItem, new HookClassPair(DropItemListen.class, VoidSpeak.class)),
                entry(HookType.Death, new HookClassPair(DeathListen.class, VoidSpeak.class)),
                entry(HookType.Damaged, new HookClassPair(DamagedListen.class, FloatSpeak.class)),
                entry(HookType.EntityInteract, new HookClassPair(EntityInteractListen.class, VoidSpeak.class)),
                entry(HookType.Attack, new HookClassPair(AttackListen.class, FloatSpeak.class)),
                entry(HookType.SweepingAttack, new HookClassPair(AttackListen.class, FloatSpeak.class))
        ));
        if (!Arrays.stream(HookType.values()).allMatch(classMap::containsKey)) throw new RuntimeException("classMap in HookManager incomplete");
    }
}
