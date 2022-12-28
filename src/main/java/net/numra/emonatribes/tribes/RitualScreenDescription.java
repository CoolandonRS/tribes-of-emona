package net.numra.emonatribes.tribes;

import io.github.cottonmc.cotton.gui.SyncedGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import io.github.cottonmc.cotton.gui.widget.icon.ItemIcon;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.numra.emonatribes.EmonaTribesMain;

public class RitualScreenDescription extends SyncedGuiDescription {
    private final BlockPos pos;

    public RitualScreenDescription(int syncId, PlayerInventory pInv, ScreenHandlerContext context) {
        super(EmonaTribesMain.ritualScreenHandlerYucky, syncId, pInv, getBlockInventory(context, 1), getBlockPropertyDelegate(context));
        this.pos = context.get((w, bp) -> bp, null);

        WGridPanel root = new WGridPanel();
        setRootPanel(root);
        root.setSize(300,200);
        root.setInsets(Insets.ROOT_PANEL);

        WItemSlot sacrificeSlot = WItemSlot.of(blockInventory, 0);
        root.add(sacrificeSlot, 4, 1);

        WButton button = new WButton(Text.of("SACRIFICE!"));
        button.setIcon(new ItemIcon(new ItemStack(Items.BLAZE_POWDER)));
        button.setOnClick(() -> {
            BlockEntity be = world.getBlockEntity(pos);
            if (!(be instanceof RitualBlockEntity rbe)) throw new IllegalArgumentException("Excuse me what?");
            BlockState bs = world.getBlockState(pos);
            if (!(bs.getBlock() instanceof TribalRitualBlock trb)) throw new IllegalArgumentException("Excuse me what?");
            ItemStack item = rbe.getStack(0);
            // FIXME!!!!!! Make a way to get a TribeData from the right player using pInv.player!!!!
            // FIXME Cont!!!!!! Very temporary until I make that system, but it goes to github anyway because :)
            TribeData tribeData = new TribeData();
            var castable = RitualUtil.getCastableRitualsWithItemStack(tribeData, item);
            if (castable.isEmpty()) return;
            TribeRecord tribeName = (TribeRecord) castable.keySet().toArray()[0];
            TribalRitualType ritualType = castable.get(tribeName).get(0);
            int count = tribeName.getRituals().get(ritualType).getCount();
            blockInventory.removeStack(0, count);
            switch (ritualType) {
                case Creation -> tribeData.joinTribe(tribeName, true);
                case Membership -> tribeData.joinTribe(tribeName);
                case Founding -> {
                    // TODO: NOT YET IMPLEMENTED
                }
                default -> tribeData.getTribeValues().levelUp();
            }
        });

        root.add(button, 4, 5);

        root.add(createPlayerInventoryPanel(), 0, 30);

        root.validate(this);
    }
}
