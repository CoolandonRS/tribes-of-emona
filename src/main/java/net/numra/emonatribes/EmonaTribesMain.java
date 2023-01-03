package net.numra.emonatribes;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.numra.emonatribes.tribes.rituals.RitualBlockEntity;
import net.numra.emonatribes.tribes.rituals.RitualScreenDescription;
import net.numra.emonatribes.tribes.TribeData;
import net.numra.emonatribes.tribes.voloria.VolorianRitualBlock;

public class EmonaTribesMain implements ModInitializer {

	@Override
	public void onInitialize() {
		ModConstants.logger.info("Loading...");
		initThings();
		initPrimaryEventListeners();
		initPacketListeners();
		ModConstants.logger.info("Loaded successfully!");

	}

	public static BlockEntityType<RitualBlockEntity> ritualBlockYucky; // named yucky since its yucky code that mojang makes me use.

	public static ScreenHandlerType<RitualScreenDescription> ritualScreenHandlerYucky; //see above;

	private void initThings() {
		Block volorianRitualBlock = new VolorianRitualBlock(FabricBlockSettings.of(Material.STONE).strength(25F, 100F).requiresTool());
		Registry.register(Registries.BLOCK, new Identifier(ModConstants.internalName, "ritualblock_volorian"), volorianRitualBlock);
		Registry.register(Registries.ITEM, new Identifier(ModConstants.internalName, "ritualblock_volorian"), new BlockItem(volorianRitualBlock, new FabricItemSettings().fireproof().maxCount(1).rarity(Rarity.RARE)));

		ritualBlockYucky = Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(ModConstants.internalName, "ritualblock"), FabricBlockEntityTypeBuilder.create(RitualBlockEntity::new, volorianRitualBlock).build());
		ritualScreenHandlerYucky = Registry.register(Registries.SCREEN_HANDLER, new Identifier(ModConstants.internalName, "ritualgui"), new ScreenHandlerType<>((syncId, playerInventory) -> new RitualScreenDescription(syncId, playerInventory, ScreenHandlerContext.EMPTY)));
	}

	private void initPacketListeners() {
		ServerPlayNetworking.registerGlobalReceiver(ModConstants.sacrificePacketID, (server, player, handler, data, sender) -> {
			if (!(player.currentScreenHandler instanceof RitualScreenDescription ritualScreen)) {
				ModConstants.logger.error("Player isn't in RitualScreen after sending sacrifice packet");
				return;
			}
			server.execute(ritualScreen::clickSacrificeRunnable);
		});
	}
	private void initPrimaryEventListeners() {
		ServerLifecycleEvents.SERVER_STOPPING.register((server) -> {
			ModConstants.globalTribeAuthority.serializeGlobalTribeAuthority();
		});
		ServerPlayConnectionEvents.JOIN.register((handler, packetSender, server) -> {
			TribeData tribeData = TribeData.deserialize(handler.player);
			ModConstants.tribeDataAuthority.register(tribeData);
		});
		ServerPlayConnectionEvents.DISCONNECT.register(((handler, server) -> {
			TribeData data = ModConstants.tribeDataAuthority.get(handler.player.getUuid());
			data.serialize();
			ModConstants.tribeDataAuthority.unregister(data);
		}));
	}
}
