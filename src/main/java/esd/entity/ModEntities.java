package esd.entity;

import esd.EmotionalSuppourtDemons;
import esd.entity.custom.DemonEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

/**
 * This is currently just a wrapper for the Emotional Support demon
 */
public class ModEntities {
    public static final EntityType<DemonEntity> DEMON = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(EmotionalSuppourtDemons.MOD_ID, "emotional_suppourt_demon"),
            EntityType.Builder.create(DemonEntity::new, SpawnGroup.CREATURE)
                    .dimensions(1f,1f).makeFireImmune().build());

    public static void registerEntities() {
        EmotionalSuppourtDemons.LOGGER.info("Registering entities for " + EmotionalSuppourtDemons.MOD_ID);
    }

}
