package net.numra.emonatribes.tribes;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.numra.emonatribes.EmonaTribesMain;

public class RitualScreenHandler extends ScreenHandler {
    private final Inventory inv;

    public RitualScreenHandler(int syncId, PlayerInventory pInv) {
        this(syncId, pInv, new SimpleInventory(1));
    }
    public RitualScreenHandler(int syncId, PlayerInventory pInv, Inventory inv) {
        super(EmonaTribesMain.ritualScreenHandlerYucky, syncId);
        checkSize(inv, 1);
        this.inv = inv;
        inv.onOpen(pInv.player);
        int m, l;
        // our container
        for (m = 0; m < 1; ++m) { // col
            for (l = 0; l < 1; ++l) { // row
                this.addSlot(new Slot(inv, l + m * 3, 62 + l * 18, 17 + m * 18));
            }
        }
        // player
        for (m = 0; m < 3; ++m) {
            for (l = 0; l < 9; ++l) {
                this.addSlot(new Slot(pInv, l + m * 9 + 9, 8 + l * 18, 84 + m * 18));
            }
        }
        for (m = 0; m < 9; ++m) {
            this.addSlot(new Slot(pInv, m, 8 + m * 18, 142));
        }
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slotIdx) {
        Slot slot = this.slots.get(slotIdx);
        if (slot.hasStack()) {
            ItemStack oStack = slot.getStack();
            if (slotIdx < this.inv.size()) {
                // Don't know what this if is for, took it from wiki and kept it.
                if (!this.insertItem(oStack, this.inv.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(oStack, 0, this.inv.size(), false)) {
                return ItemStack.EMPTY;
            }
            if (oStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
            return oStack;
        }
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

    @Override
    public void close(PlayerEntity player) {
        inv.onClose(player);
        super.close(player);
    }
}
