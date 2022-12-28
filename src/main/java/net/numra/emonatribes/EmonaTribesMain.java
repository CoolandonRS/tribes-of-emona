package net.numra.emonatribes;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
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
import net.numra.emonatribes.tribes.RitualBlockEntity;
import net.numra.emonatribes.tribes.RitualScreenDescription;
import net.numra.emonatribes.tribes.voloria.VolorianRitualBlock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("deprecation")
public class EmonaTribesMain implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger logger = LoggerFactory.getLogger(ModConstants.prettyName);

	@Override
	public void onInitialize() {
		logger.info("Loading...");
		initThings();
		logger.info("Loaded successfully!");
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
}
