package net.numra.emonatribes.tribes;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;

public class RitualScreenHandlerFactory implements NamedScreenHandlerFactory {
    private final Text displayName;
    private final Inventory inv;

    @Override
    public Text getDisplayName() {
        return this.displayName;
    }

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory pInv, PlayerEntity player) {
        return new RitualScreenHandler(syncId, pInv, inv);
    }

    public RitualScreenHandlerFactory(Text displayName, Inventory inv) {
        this.displayName = displayName;
        this.inv = inv;
    }
}
