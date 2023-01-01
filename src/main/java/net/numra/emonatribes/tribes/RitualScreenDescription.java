package net.numra.emonatribes.tribes;

import io.github.cottonmc.cotton.gui.SyncedGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.text.Text;
import net.numra.emonatribes.EmonaTribesMain;
import net.numra.emonatribes.ModConstants;

import java.util.Optional;

public class RitualScreenDescription extends SyncedGuiDescription {
    private final ScreenHandlerContext screenHandlerContext;
    private boolean initran = false;

    public RitualScreenDescription(int syncId, PlayerInventory pInv, ScreenHandlerContext context) {
        super(EmonaTribesMain.ritualScreenHandlerYucky, syncId, pInv, getBlockInventory(context, 1), getBlockPropertyDelegate(context));
        this.screenHandlerContext = context;
        init();
    }

    /**
     * @return an instance of {@code this}. May be safely ignored
     */
    public RitualScreenDescription init() {
        if (initran) return this;
        initran = true;
        WGridPanel root = new WGridPanel();
        setRootPanel(root);
        root.setSize(120,200);
        root.setInsets(Insets.ROOT_PANEL);

        WItemSlot sacrificeSlot = WItemSlot.of(blockInventory, 0);
        root.add(sacrificeSlot, 7, 2);

        WButton button = new WButton(Text.of("SACRIFICE!"));
        button.setSize(50, 0);
//        button.setIcon(new ItemIcon(new ItemStack(Items.BLAZE_POWDER)));
        button.setOnClick(() -> {
            if (!world.isClient) throw new RuntimeException("How is this possible");
            ClientPlayNetworking.send(ModConstants.sacrificePacketID, PacketByteBufs.empty());
        });

        root.add(button, 6, 4);

        root.add(createPlayerInventoryPanel(), 0, 5);

        root.validate(this);
        return this;
    }

    public void clickSacrificeRunnable() {
        System.out.println("WOOOOO");
        System.out.println(world.isClient);
        var pos = screenHandlerContext.get((w, bp) -> bp).orElse(null);
        if (pos == null) {
            System.out.println(Thread.currentThread().getId());
            System.err.println(screenHandlerContext.get((w, bp) -> new Object()) == Optional.empty());
            ModConstants.logger.error("POS is null! This is a bug!");
            return;
        }
        BlockEntity be = world.getBlockEntity(pos);
        if (!(be instanceof RitualBlockEntity rbe)) throw new IllegalArgumentException("Excuse me what? 1");
        BlockState bs = world.getBlockState(pos);
        if (!(bs.getBlock() instanceof TribalRitualBlock)) throw new IllegalArgumentException("Excuse me what? 2");
        ItemStack item = rbe.getStack(0);
        TribeData tribeData = ModConstants.tribeDataAuthority.get(playerInventory.player.getUuid());
        var castable = RitualUtil.getCastableRitualsWithItemStack(tribeData, item);
        if (castable.isEmpty()) return;
        TribeRecord tribeName = (TribeRecord) castable.keySet().toArray()[0];
        TribalRitualType ritualType = castable.get(tribeName).get(0);
        int count = tribeName.getRituals().get(ritualType).getCount();
        blockInventory.removeStack(0, count);
        switch (ritualType) {
            case Creation -> {
                ModConstants.globalTribeAuthority.setCreated(tribeName, true);
                tribeData.joinTribe(tribeName, true);
            }
            case Membership -> tribeData.joinTribe(tribeName);
            case Founding -> {
                // TODO: NOT YET IMPLEMENTED
            }
            case Curious, Student, Expert, Master, Ascended -> tribeData.getTribeValues().levelUp();
            case Demigod -> {
                // TODO: NOT YEET IMPLEMENTED..
            }
            case Godhood -> {
                ModConstants.globalTribeAuthority.setHasGod(tribeName, true);
                // TODO: NOT YET IMPLEMENTED.
            }
        }
    }

    @Override
    public boolean canUse(PlayerEntity entity) {
        return true;
    }
}
