package net.numra.emonatribes.mixin.hooks;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.numra.emonatribes.ModConstants;
import net.numra.emonatribes.misc.Discardable;
import net.numra.emonatribes.tribes.hooks.HookType;
import net.numra.emonatribes.tribes.hooks.sound.listen.AttackListen;
import net.numra.emonatribes.tribes.hooks.sound.listen.EntityInteractListen;
import net.numra.emonatribes.tribes.hooks.sound.listen.PlayerListen;
import net.numra.emonatribes.tribes.hooks.sound.speak.FloatSpeak;
import net.numra.emonatribes.tribes.hooks.sound.speak.GenericSpeak;
import net.numra.emonatribes.tribes.hooks.sound.speak.VoidSpeak;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
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
    private boolean fallHook(LivingEntity instance, float fallDistance, float damageMultiplier, DamageSource damageSource, Operation<Boolean> original) {

    }
}
