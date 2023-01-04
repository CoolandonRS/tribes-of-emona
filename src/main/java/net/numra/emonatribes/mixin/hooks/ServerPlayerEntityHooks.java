package net.numra.emonatribes.mixin.hooks;

import com.mojang.datafixers.util.Either;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Unit;
import net.minecraft.util.math.BlockPos;
import net.numra.emonatribes.ModConstants;
import net.numra.emonatribes.misc.Discardable;
import net.numra.emonatribes.tribes.hooks.HookType;
import net.numra.emonatribes.tribes.hooks.sound.listen.*;
import net.numra.emonatribes.tribes.hooks.sound.speak.FloatSpeak;
import net.numra.emonatribes.tribes.hooks.sound.speak.GenericSpeak;
import net.numra.emonatribes.tribes.hooks.sound.speak.SleepSpeak;
import net.numra.emonatribes.tribes.hooks.sound.speak.VoidSpeak;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityHooks {
    private PlayerEntity asPE() {
        return (PlayerEntity) (Object) this;
    }
    @Discardable
    private <S extends GenericSpeak> List<S> trigger(HookType type, Object data, Class<S> expectedSpeak) {
        if (this.asPE().isSpectator()) return List.of();
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

    @ModifyVariable(at = @At("HEAD"), method = "damage", argsOnly = true)
    private float damageHook(float amount, DamageSource source) {
        PlayerEntity pe = this.asPE();
        if (pe.isInvulnerableTo(source)) return amount;
        if (amount == 0.0F) return amount;
        FloatSpeak consensus = new FloatSpeak(trigger(HookType.Damaged, new DamagedListen(source, amount, pe), FloatSpeak.class));
        return Math.max(amount + consensus.getChange(), 0.0F);
    }

    @Inject(at = @At("HEAD"), method = "trySleep", cancellable = true)
    private void sleepHook(BlockPos pos, CallbackInfoReturnable<Either<PlayerEntity.SleepFailureReason, Unit>> cir) {
        SleepSpeak consensus = new SleepSpeak(trigger(HookType.Sleep, new SleepListen(pos, this.asPE()), SleepSpeak.class));
        if (consensus.getFailureReason() == null) return;
        cir.setReturnValue(Either.left(consensus.getFailureReason()));
    }
}
