package net.numra.emonatribes.mixin.hooks;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.numra.emonatribes.ModConstants;
import net.numra.emonatribes.misc.Discardable;
import net.numra.emonatribes.tribes.hooks.HookType;
import net.numra.emonatribes.tribes.hooks.sound.listen.DamagedListen;
import net.numra.emonatribes.tribes.hooks.sound.listen.DeathListen;
import net.numra.emonatribes.tribes.hooks.sound.listen.DropItemListen;
import net.numra.emonatribes.tribes.hooks.sound.speak.DamagedSpeak;
import net.numra.emonatribes.tribes.hooks.sound.speak.GenericSpeak;
import net.numra.emonatribes.tribes.hooks.sound.speak.VoidSpeak;
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
        return ModConstants.hookManager.trigger(type, data, expectedSpeak);
    }
    @Inject(at = @At("HEAD"), method = "dropItem(Lnet/minecraft/item/ItemStack;ZZ)Lnet/minecraft/entity/ItemEntity;")
    private void dropHook(ItemStack stack, boolean throwRandomly, boolean retainOwnership, CallbackInfoReturnable<ItemEntity> cir) {
        trigger(HookType.DropItem, new DropItemListen(stack, this.asPE()), VoidSpeak.class);
    }
    @Inject(at = @At("HEAD"), method = "onDeath")
    private void deathHook(DamageSource damageSource, CallbackInfo ci) {
        trigger(HookType.Death, new DeathListen(damageSource, this.asPE()), VoidSpeak.class);
    }

    @ModifyVariable(at = @At("HEAD"), method = "applyDamage", argsOnly = true)
    private float damageHook(float amount, DamageSource source) {
        PlayerEntity pe = this.asPE();
        if (pe.isInvulnerableTo(source)) return amount;
        if (amount == 0.0F) return amount;
        DamagedSpeak consensus = new DamagedSpeak(trigger(HookType.Damaged, new DamagedListen(source, amount, pe), DamagedSpeak.class));
        return Math.max(amount + consensus.getAmountChange(), 0.0F);
    }
}
