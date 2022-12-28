package net.numra.emonatribes.tribes;

import net.minecraft.block.BlockState;
import net.minecraft.block.InventoryProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static net.numra.emonatribes.EmonaTribesMain.ritualBlockYucky;

public class RitualBlockEntity extends BlockEntity implements SidedInventory, InventoryProvider {
    private boolean inUse = false;
    private ItemStack item = ItemStack.EMPTY;

    public RitualBlockEntity(BlockPos pos, BlockState state) {
        super(ritualBlockYucky, pos, state);
    }

    public ActionResult open(BlockState state, World world, BlockPos pos, PlayerEntity player) {
        if (inUse) return ActionResult.FAIL;
        if (world.isClient) return ActionResult.SUCCESS;
        player.openHandledScreen(state.createScreenHandlerFactory( world, pos));
        return ActionResult.SUCCESS;
    }

    @Override
    public void onOpen(PlayerEntity player) {
        inUse = true;
    }

    @Override
    public void onClose(PlayerEntity player) {
        if (!isEmpty()) {
            player.giveItemStack(item);
            clear();
        }
        inUse = false;
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return item == ItemStack.EMPTY;
    }

    @Override
    public ItemStack getStack(int slot) {
        if (slot == 0) return item; else return null;
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        return Inventories.splitStack(List.of(item), slot, amount);
    }

    @Override
    public ItemStack removeStack(int slot) {
        if (slot == 0) item = ItemStack.EMPTY; else return null;
        return ItemStack.EMPTY;
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        if (slot == 0) item = stack;
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        boolean inRange = !(player.squaredDistanceTo((double)this.pos.getX() + 0.5, (double)this.pos.getY() + 0.5, (double)this.pos.getZ() + 0.5) > 64.0); // Taken from EnderChestBlockEntity
        return !inUse && inRange;
    }

    @Override
    public void clear() {
        item = ItemStack.EMPTY;
    }

    @Override
    public SidedInventory getInventory(BlockState state, WorldAccess world, BlockPos pos) {
        return this;
    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        return new int[0];
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        return false;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return false;
    }


}
