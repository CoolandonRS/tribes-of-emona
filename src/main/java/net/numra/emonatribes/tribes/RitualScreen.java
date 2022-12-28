package net.numra.emonatribes.tribes;

import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

public class RitualScreen extends CottonInventoryScreen<RitualScreenDescription> {

    public RitualScreen(RitualScreenDescription description, PlayerEntity player, Text title) {
        super(description, player, title);
    }
}
