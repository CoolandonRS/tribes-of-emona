package net.numra.emonatribes.tribes;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;

public abstract class TribalRitualBlock extends Block implements BlockEntityProvider {
    protected EnumMap<TribalRitualType, ItemStack> ritualCosts;
    protected Text containerDisplayName;

    @SuppressWarnings("deprecation")
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        BlockEntity be = world.getBlockEntity(pos);
        if (!(be instanceof RitualBlockEntity rbe)) return ActionResult.PASS;
        return rbe.open(state, world, pos, player);
    }

    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new RitualBlockEntity(pos, state);
    }
    protected TribalRitualBlock(Settings settings) {
        super(settings);
    }

    @Nullable
    @Override
    public NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos) {
        if (!(world.getBlockEntity(pos) instanceof RitualBlockEntity rbe)) return null;
        return new RitualScreenHandlerFactory(containerDisplayName, rbe, world, pos);
    }
}
