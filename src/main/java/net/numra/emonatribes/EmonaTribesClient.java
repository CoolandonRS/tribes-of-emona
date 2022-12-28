package net.numra.emonatribes;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.numra.emonatribes.tribes.RitualScreen;

public class EmonaTribesClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HandledScreens.register(EmonaTribesMain.ritualScreenHandlerYucky, RitualScreen::new);
    }
}
