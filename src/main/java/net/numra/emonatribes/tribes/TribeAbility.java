package net.numra.emonatribes.tribes;

import net.numra.emonatribes.misc.NotImplementedException;
import net.numra.emonatribes.tribes.hooks.GenericListener;
import net.numra.emonatribes.tribes.hooks.HookType;
import net.numra.emonatribes.tribes.hooks.sound.listen.*;
import net.numra.emonatribes.tribes.hooks.sound.speak.*;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Map.entry;

public abstract class TribeAbility implements Serializable {
    protected int level = 0;

    public boolean setLevel(int newLevel) {
        if (newLevel <= level) return false;
        level = newLevel;
        return true;
    }

    public int getLevel() {
        return level;
    }

    public abstract Set<HookType> getHookSet();

    public EnumMap<HookType, GenericListener<?, ? extends GenericSpeak>> getListeners() {
        Set<HookType> hookSet = getHookSet();
        return new EnumMap<>(toListenerMap().entrySet().stream().filter(e -> hookSet.contains(e.getKey())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
    }

    protected EnumMap<HookType, GenericListener<?, ? extends GenericSpeak>> toListenerMap() {
        return new EnumMap<>(Map.ofEntries(
                entry(HookType.Death, new GenericListener<>(DamageSourceListen.class, this::onDeath)),
                entry(HookType.Damaged, new GenericListener<>(DamagedListen.class, this::onDamaged)),
                entry(HookType.EntityInteract, new GenericListener<>(EntityInteractListen.class, this::onEntityInteract)),
                entry(HookType.Attack, new GenericListener<>(AttackListen.class, this::onAttack)),
                entry(HookType.SweepingAttack, new GenericListener<>(AttackListen.class, this::onSweepingAttack)),
                entry(HookType.Sleep, new GenericListener<>(BlockPosListen.class, this::onSleep)),
                entry(HookType.Jump, new GenericListener<>(PlayerListen.class, this::onJump)),
                entry(HookType.Fall, new GenericListener<>(FallListen.class, this::onFall)),
                entry(HookType.AddXp, new GenericListener<>(IntListen.class, this::onGainXp)),
                entry(HookType.Hunger, new GenericListener<>(FloatListen.class, this::onHunger)),
                entry(HookType.DropItem, new GenericListener<>(ItemStackListen.class, this::onDropItem))
        ));
    }

    public VoidSpeak onDeath(DamageSourceListen listen) {
        throw new NotImplementedException();
    }

    public FloatSpeak onDamaged(DamagedListen listen) {
        throw new NotImplementedException();
    }

    public VoidSpeak onEntityInteract(EntityInteractListen listen) {
        throw new NotImplementedException();
    }

    public FloatSpeak onAttack(AttackListen listen) {
        throw new NotImplementedException();
    }

    public FloatSpeak onSweepingAttack(AttackListen listen) {
        throw new NotImplementedException();
    }

    public SleepSpeak onSleep(BlockPosListen listen) {
        throw new NotImplementedException();
    }

    public VoidSpeak onJump(PlayerListen listen) {
        throw new NotImplementedException();
    }

    /**
     * @return MultiSpeak of [FloatSpeak, FloatSpeak]
     */
    public MultiSpeak onFall(FallListen listen) {
        throw new NotImplementedException();
    }

    public IntSpeak onGainXp(IntListen listen) {
        throw new NotImplementedException();
    }

    public FloatSpeak onHunger(FloatListen listen) {
        throw new NotImplementedException();
    }

    public VoidSpeak onDropItem(ItemStackListen listen) {
        throw new NotImplementedException();
    }
}
