package net.numra.emonatribes;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.numra.emonatribes.tribes.RitualScreen;
import net.numra.emonatribes.tribes.RitualScreenDescription;

public class EmonaTribesClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HandledScreens.<RitualScreenDescription, RitualScreen>register(EmonaTribesMain.ritualScreenHandlerYucky, (gui, inv, title) -> new RitualScreen(gui, inv.player, title));
    }
}
