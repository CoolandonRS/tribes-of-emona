package net.numra.emonatribes.mixin.hooks;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.numra.emonatribes.ModConstants;
import net.numra.emonatribes.misc.Discardable;
import net.numra.emonatribes.tribes.hooks.HookType;
import net.numra.emonatribes.tribes.hooks.sound.IncompatibleSpeakException;
import net.numra.emonatribes.tribes.hooks.sound.listen.*;
import net.numra.emonatribes.tribes.hooks.sound.speak.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(PlayerEntity.class)
public class PlayerEntityHooks {
    private PlayerEntity asPE() {
        return (PlayerEntity) (Object) this;
    }
    @Discardable
    private <S extends GenericSpeak> List<S> trigger(HookType type, Object data, Class<S> expectedSpeak) {
        if (this.asPE().isSpectator()) return List.of();
        return ModConstants.hookManager.trigger(type, data, expectedSpeak);
    }
    @Inject(at = @At("HEAD"), method = "interact")
    private void entityInteractHook(Entity entity, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        trigger(HookType.EntityInteract, new EntityInteractListen(entity, hand, this.asPE()), VoidSpeak.class);
    }

    @WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"), method = "attack")
    private boolean attackHook(Entity target, DamageSource source, float amount, Operation<Boolean> original) {
        FloatSpeak consensus = new FloatSpeak(trigger(HookType.Attack, new AttackListen(target, amount, this.asPE()), FloatSpeak.class));
        return original.call(source, Math.max(amount + consensus.getChange(), 0.0F));
    }

    @WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"), method = "attack")
    private boolean sweepingAttackHook(LivingEntity target, DamageSource source, float amount, Operation<Boolean> original) {
        FloatSpeak consensus = new FloatSpeak(trigger(HookType.SweepingAttack, new AttackListen(target, amount, this.asPE()), FloatSpeak.class));
        return original.call(source, Math.max(amount + consensus.getChange(), 0.0F));
    }

    @Inject(at = @At("HEAD"), method = "jump")
    public void jumpHook(CallbackInfo ci) {
        trigger(HookType.Jump, new PlayerListen(this.asPE()), VoidSpeak.class);
    }

    @WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;handleFallDamage(FFLnet/minecraft/entity/damage/DamageSource;)Z"), method = "handleFallDamage")
    private boolean fallHook(LivingEntity instance, float distance, float multiplier, DamageSource source, Operation<Boolean> original) {
        MultiSpeak consensus = new MultiSpeak(trigger(HookType.Fall, new FallListen(instance, distance, multiplier), MultiSpeak.class));
        List<? extends GenericSpeak> speaks = consensus.getSpeaks();
        if (speaks.get(0) instanceof FloatSpeak dist && speaks.get(1) instanceof FloatSpeak mult) {
            return original.call(Math.max(distance + dist.getChange(), 0.0F), Math.max(multiplier + mult.getChange(), 0.0F), source);
        } else throw new IncompatibleSpeakException();
    }

    @ModifyVariable(at = @At("HEAD"), method = "addExperience", argsOnly = true)
    private int addXpHook(int gain) {
        IntSpeak consensus = new IntSpeak(trigger(HookType.AddXp, new IntListen(this.asPE(), gain), IntSpeak.class));
        return Math.max(gain + consensus.getChange(), 0);
    }

    @WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/HungerManager;addExhaustion(F)V"), method = "addExhaustion")
    private void hungerHook(HungerManager instance, float exhaustion, Operation<Void> original) {
        FloatSpeak consensus = new FloatSpeak(trigger(HookType.Hunger, new FloatListen(this.asPE(), exhaustion), FloatSpeak.class));
        original.call(Math.max(exhaustion + consensus.getChange(), 0.0F));
    }
}
