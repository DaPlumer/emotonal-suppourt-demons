package esd;

import esd.entity.ModEntities;
import esd.entity.custom.DemonEntity;
import esd.item.ModItems;
import esd.world.gen.ModEntitySpawns;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmotionalSuppourtDemons implements ModInitializer {
	public static final String MOD_ID = "esd";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Hello Fabric world!");
		ModEntities.registerEntities();
		ModItems.registerItems();
		FabricDefaultAttributeRegistry.register(ModEntities.DEMON, DemonEntity.createAttributes());
		ModEntitySpawns.addEntitySpawns();
	}
}