package net.numra.emonatribes.tribes;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.EnumMap;

public abstract class TribalRitualBlock extends Block implements BlockEntityProvider {
    protected EnumMap<TribalRitualType, ItemStack> ritualCosts;
    protected Text containerDisplayName;

    @SuppressWarnings("deprecation")
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        BlockEntity be = world.getBlockEntity(pos);
        if (!(be instanceof RitualBlockEntity rbe)) return ActionResult.PASS;
        return rbe.open(state, world, pos, player, hand, hit, ritualCosts, containerDisplayName);
    }

    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new RitualBlockEntity(pos, state);
    }
    protected TribalRitualBlock(Settings settings) {
        super(settings);
    }
}
