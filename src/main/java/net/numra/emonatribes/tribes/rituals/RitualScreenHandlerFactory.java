package net.numra.emonatribes.tribes.rituals;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RitualScreenHandlerFactory implements NamedScreenHandlerFactory {
    private final Text displayName;
    private final World world;
    private final BlockPos pos;

    @Override
    public Text getDisplayName() {
        return this.displayName;
    }

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory pInv, PlayerEntity player) {
        return new RitualScreenDescription(syncId, pInv, ScreenHandlerContext.create(world, pos));
    }

    public RitualScreenHandlerFactory(Text displayName, World world, BlockPos pos) {
        this.displayName = displayName;
        this.world = world;
        this.pos = pos;
    }
}
